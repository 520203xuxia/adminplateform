package cn.edu.szu.bigdata.controller;

import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.model.User;
import cn.edu.szu.bigdata.service.ReportService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static cn.edu.szu.bigdata.service.GridFsService.*;
import static cn.edu.szu.bigdata.util.CommonUtils.getHash256;
import static cn.edu.szu.bigdata.util.CommonUtils.getMd5;
import static cn.edu.szu.bigdata.util.constant.defalutContentType;
import static cn.edu.szu.bigdata.util.constant.getOffice_online_addr;

/**
 * Created by longhao on 2017/9/4.
 * Email: longhao1@email.szu.edu.cn
 */
@Controller
public class DocumentController {


    @Autowired
    ReportService reportService;

    /**
     * 处理post请求，即提交了provicne,city,project_name
     * 通过三个变量获得最接近的5篇文档。
     */
    @PostMapping("/eidtpage")
    public String getEditPage(Model model, HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        String project_province=request.getParameter("project_province");
        String project_city=request.getParameter("project_city");
        String type_name=request.getParameter("type_name");
        String project_name=request.getParameter("project_name");
        org.apache.log4j.Logger.getLogger(getClass()).info("项目名称:"+project_name+"省份:"+project_province+"城市:"+project_city+"领域:"+type_name);
        ReportEntity reportEntity=new ReportEntity(project_name,project_province,project_city,type_name,user.getUsername());
        String filenameMd5 = createNewEmptyDocFileToGridFs(project_name, defalutContentType,user.getUsername());
        reportEntity.setId(filenameMd5);
        if(reportService.selectReportById(filenameMd5)!=null){
            reportService.deleteReportById(filenameMd5);
        }
        reportService.save(reportEntity);
        model.addAttribute("filenameMd5",filenameMd5);
        model.addAttribute("office_online_addr",getOffice_online_addr());
        model.addAttribute("reportEntity",reportEntity);
        model.addAttribute("user",user);
        return "edit";
    }

    @GetMapping("/page/{id}")
    public String getEditPageById(Model model,@PathVariable("id") String id,HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        ReportEntity reportEntity=reportService.selectReportById(id);
        if(reportEntity!=null&&checkFileisExist(id)){
            model.addAttribute("filenameMd5",id);
            model.addAttribute("office_online_addr",getOffice_online_addr());
            model.addAttribute("reportEntity",reportEntity);
            model.addAttribute("user",user);
        }
        return "edit";
    }


    /**
     * 上传功能,将本地docx文件上传至服务器，并打开
     */
    @PostMapping(value = "/upload",produces = "application/json")
    public ResponseEntity upload(MultipartFile file,HttpServletRequest request) throws IOException {
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        String filename=file.getOriginalFilename();
        System.out.println(filename);
        /**
         * 添加文件名格式校验模块
         */
        String[] temp=filename.split("\\.");
        if(temp.length<2){
            System.out.println("文件名没有可识别的格式");
            return new ResponseEntity("文件名没有可识别的格式", HttpStatus.OK);
        }
        else if(!temp[1].equals("docx")){
            System.out.println("文件不是docx格式,请转换后上传");
            return new ResponseEntity("文件不是docx格式,请转换后上传", HttpStatus.OK);
        }
        else{
            DBObject metaData = new BasicDBObject();
            String sha256=getHash256(file.getBytes());
            String filenameMd5=getMd5(filename+user.getUsername())+"."+defalutContentType;
            reportService.save(new ReportEntity(filenameMd5,filename,user.getUsername()));
            metaData.put("sha256",sha256);
            metaData.put("filename",filename);
            metaData.put("username",user.getUsername());
            updateFileToGridFs(filenameMd5,defalutContentType,metaData,file.getBytes());
            return new ResponseEntity("上传成功",HttpStatus.OK);
        }
    }


    /**
     * 下载当前编辑的文档
     */
    @GetMapping("/download/{filename}")
    public String downloadCurrentDoc(@PathVariable("filename") String filename){
        String url="/wopi/files/"+filename+"/contents";
        return "forward:"+url;
    }


    /**
     *
     * @param projectName
     * @param request  获取请求中的用户名
     * @return
     */
    @GetMapping("/create/{projectName}")
    public String createEmptyDoc(@PathVariable("projectName") String projectName, HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        //处理空文件名
        if(projectName==null|| projectName.length()==0){
            return "文件名为空";
        }
        String message;
        try {//正常则返回创建的文件名
            message=createNewEmptyDocFileToGridFs(projectName, defalutContentType,user.getUsername());
        }catch(Exception e){//处理文件名重复
            message=e.getMessage();
        }
        return message;
    }



    /**
     * 显示文档内容页面
     */
    @GetMapping("show/{docname}/{name}")
    public String showPdf(Model model,@PathVariable("docname") String docname,@PathVariable("name") String name,HttpServletRequest request){
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        model.addAttribute("user",user);
        model.addAttribute("name",name);
        System.out.println(getOffice_online_addr()+docname);
        model.addAttribute("docurl",getOffice_online_addr()+docname);
        return "pdfpage";
    }

    /**
     * 获取pdf文档内容
     * @param name
     */
    @RequestMapping(value="/loadTechFile/{name}", produces="application/pdf")
    @ResponseBody
    public Object getPdf(@PathVariable("name") String name){
        String filenameMd5=getMd5(name)+".pdf";
        System.out.println(filenameMd5);
        if(!checkFileisExistInPdf(filenameMd5)){
            System.out.println("文件不存在");
            return null;
        }
        try{
            byte[] buffer=getPdfContent(filenameMd5);
            System.out.println("获取pdf文件内容成功");
            return ArrayUtils.toPrimitive(buffer);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
