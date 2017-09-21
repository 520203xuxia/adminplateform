package cn.edu.szu.bigdata.controller;

import cn.edu.szu.bigdata.entity.FragmentEntity;
import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.entity.SegmentEntity;
import cn.edu.szu.bigdata.model.User;
import cn.edu.szu.bigdata.service.DocumentService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
    DocumentService documentService;

    /**
     * 处理post请求，即提交了provicne,city,project_name
     * 通过三个变量获得最接近的5篇文档。
     */
    @PostMapping("/eidtpage")
    public String getEditPage(Model model, HttpServletRequest request){
        String cmbProvince=request.getParameter("cmbProvince");
        String cmbCity=request.getParameter("cmbCity");
        String domain=request.getParameter("domain");
        String project_name=request.getParameter("project_name");
        ReportEntity reportEntity=new ReportEntity(project_name,cmbProvince,cmbCity,domain);
//        HashMap<SegmentEntity,List<SegmentEntity>> segmentEntityListMap=documentService.getAllSegments();
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("userSession");
        //编写的新文档属性
        String filenameMd5 = createNewEmptyDocFileToGridFs(project_name, defalutContentType);
//        model.addAttribute("segmentEntityListMap",segmentEntityListMap);
        model.addAttribute("filenameMd5",filenameMd5);
        model.addAttribute("office_online_addr",getOffice_online_addr());
        model.addAttribute("reportEntity",reportEntity);
        model.addAttribute("user",user);
        return "edit";
    }

    /**
     * 上传功能,将本地docx文件上传至服务器，并打开
     */
    @PostMapping("/upload/{filename}")
    @ResponseBody
    public String upload(@PathVariable("filename") String filename,@RequestBody byte[] contents){
        String[] temp=filename.split(".");

        /**
         * 添加文件名格式校验模块
         */
        if(temp.length<2){
            System.out.println("文件名没有可识别的格式");
            return null;
        }
        else if(temp[1]!="docx"){
            System.out.println("文件不是docx格式,请转换后上传");
            return null;
        }
        else{
            DBObject metaData = new BasicDBObject();
            String sha256=getHash256(contents);
            String filenameMd5=getMd5(filename)+"."+defalutContentType;
            metaData.put("sha256",sha256);
            metaData.put("filename",filename);
            updateFileToGridFs(filenameMd5,defalutContentType,metaData,contents);
            return getOffice_online_addr()+"/wopi/files/"+filenameMd5;
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
     * @return
     */
    @GetMapping("/create/{projectName}")
    public String createEmptyDoc(@PathVariable("projectName") String projectName){
        //处理空文件名
        if(projectName==null|| projectName.length()==0){
            return "文件名为空";
        }
        String message;
        try {//正常则返回创建的文件名
            message=createNewEmptyDocFileToGridFs(projectName, defalutContentType);
        }catch(Exception e){//处理文件名重复
            message=e.getMessage();
        }
        return message;
    }


    /**
     * 处理post请求，点击提纲中的标题，提交了，provicne,city,project_name,title_id
     * 返回最相似的5个段落
     */
    @GetMapping("/segments/{id}")
    @ResponseBody
    public List<FragmentEntity> getSegmentList(@PathVariable("id") String id){
        List<FragmentEntity> fragmentEntityList=new ArrayList<>();
        FragmentEntity fragmentEntity=new FragmentEntity();
        fragmentEntity.setProjectName("测试.pdf");
        fragmentEntity.setSegmentName("test"+id);
        fragmentEntity.setSegmentContent("test"+id);

        FragmentEntity fragmentEntity1=new FragmentEntity();
        fragmentEntity1.setProjectName("测试1.pdf");
        fragmentEntity1.setSegmentName("test1"+id);
        fragmentEntity1.setSegmentContent("test1"+id);

        fragmentEntityList.add(fragmentEntity);
        fragmentEntityList.add(fragmentEntity1);
        return fragmentEntityList;
    }


    /**
     * 显示文档内容页面
     */
    @GetMapping("show/{docname}/{name}")
    public String showPdf(Model model,@PathVariable("docname") String docname,@PathVariable("name") String name){
        model.addAttribute("name",name);
        System.out.println(getOffice_online_addr()+docname);
        model.addAttribute("docurl",getOffice_online_addr()+docname);
        return "pdfpage";
    }

    /**
     * 获取pdf文档内容
     * @param name
     * @param response
     */
    @GetMapping("/pdf/{name}")
    public void getPdf(@PathVariable("name") String name, HttpServletResponse response){
        String filenameMd5=getMd5(name)+".pdf";
        System.out.println(filenameMd5);
        if(!checkFileisExistInPdf(filenameMd5)){
            System.out.println("文件不存在");
            return;
        }
        OutputStream toClient=null;
        try{
            //清空response
            response.reset();
            byte[] buffer=getPdfContent(filenameMd5);

            //设置response的Header
            response.addHeader("Content-Disposition","attachment;filename=" + new String(name.getBytes("utf-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + buffer.length);
            toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            System.out.println("获取pdf文件内容成功");
        }catch(IOException e){
            e.printStackTrace();
        }finally {
            try {
                if(toClient!=null){
                    toClient.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }


    @GetMapping("/segments")
    @ResponseBody
    public List<SegmentEntity> getAllSegmentName(){
        return documentService.getAllSegmentName();
    }

    @GetMapping("/test")
    public String test(){
        return "edit";
    }
}
