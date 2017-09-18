package cn.edu.szu.bigdata.controller;

import cn.edu.szu.bigdata.entity.DocEntity;
import cn.edu.szu.bigdata.entity.FileInfoEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static cn.edu.szu.bigdata.service.GridFsService.*;
import static cn.edu.szu.bigdata.util.CommonUtils.getHash256;
import static cn.edu.szu.bigdata.util.constant.currentUser;

/**
 * Created by longhao on 2017/8/23.
 * Email: longhao1@email.szu.edu.cn
 * 由于office online server 不支持中文文件名,同时在url中传递中文不太合适,这里选择对文件名求md5
 */
@Controller
@RequestMapping(value = "/wopi")
@ResponseBody
public class WopiHostController {


    @RequestMapping("/files/{name}")//这里的name是md5值
    public void getFileInfo(@PathVariable(name = "name" ) String name, HttpServletResponse response){
        FileInfoEntity fileInfoEntity=new FileInfoEntity();
        try{
            //获取文件名
            String fileName= URLEncoder.encode(name,"utf-8");
            System.out.println("文件名："+fileName);
            if(fileName!=null && fileName.length()>0 && checkFileisExist(fileName)){
                DocEntity docEntity = getDocEntityFromGridFs(fileName);
                fileInfoEntity.setBaseFileName(docEntity.getFileName());
                fileInfoEntity.setOwnerId(currentUser);
                fileInfoEntity.setSize(docEntity.getLength());
                fileInfoEntity.setVersion(docEntity.getUploadDate().getTime());
                fileInfoEntity.setSha256((String) docEntity.getMetadata().get("sha256"));
                fileInfoEntity.setAllowExternalMarketplace(true);
                fileInfoEntity.setUserCanWrite(true);
                fileInfoEntity.setSupportsLocks(true);
                fileInfoEntity.setSupportsUpdate(true);
            }
            ObjectMapper mapper=new ObjectMapper();
            String Json=mapper.writeValueAsString(fileInfoEntity);
            response.getWriter().write(Json);

        }catch(UnsupportedEncodingException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 保存更新文件
     * @param name
     * @param content
     */
    @PostMapping(value = "/files/{name}/contents")
    public void postFile(@PathVariable(name="name") String name, @RequestBody byte[] content){
        String sha256=getHash256(content);
        DocEntity docEntity=getDocEntityFromGridFs(name);
        String contentType=docEntity.getContentType();
        DBObject metaData = new BasicDBObject();
        metaData.put("sha256",sha256);
        updateFileToGridFs(name,contentType,metaData,content);
    }


    /**
     * 获取文件流
     * @param name
     * @param response
     */
    @GetMapping(value = "/files/{name}/contents")
    public void getFile(@PathVariable(name="name") String name, HttpServletResponse response){
        if(!checkFileisExist(name)){
            System.out.println("文件不存在");
            return;
        }

        OutputStream toClient=null;
        try{
            //清空response
            response.reset();
            byte[] buffer=getDocContentsFromGridFs(name);

            //设置response的Header
            response.addHeader("Content-Disposition","attachment;filename=" + new String(name.getBytes("utf-8"), "ISO-8859-1"));
            response.addHeader("Content-Length", "" + buffer.length);
            toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            System.out.println("获取文件内容成功");
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


}
