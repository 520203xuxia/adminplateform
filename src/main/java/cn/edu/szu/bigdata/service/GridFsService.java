package cn.edu.szu.bigdata.service;

import cn.edu.szu.bigdata.config.MongoConfig;
import cn.edu.szu.bigdata.entity.DocEntity;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import java.io.*;
import java.util.List;

import static cn.edu.szu.bigdata.util.CommonUtils.getHash256;
import static cn.edu.szu.bigdata.util.CommonUtils.getMd5;


/**
 * Created by longhao on 2017/9/5.
 * Email: longhao1@email.szu.edu.cn
 */

public class GridFsService {

    private static Mongo mongo= MongoConfig.getMongo();

    private static GridFS gridFS=new GridFS(mongo.getDB("test"));

    private static GridFS pdfGridFS=new GridFS(mongo.getDB("test"),"pdf");

    private static String templateFilePath="src/main/resources/template.docx";

    /**
     * 获取文档相关信息
     * @param fileName
     * @return
     */
    public static DocEntity getDocEntityFromGridFs(String fileName){
        DocEntity docEntity=new DocEntity();

        GridFSDBFile gridFSDBFile=gridFS.findOne(fileName);
        docEntity.setFileName(gridFSDBFile.getFilename());
        docEntity.setLength(gridFSDBFile.getLength());
        docEntity.setMetadata(gridFSDBFile.getMetaData());
        docEntity.setContentType(gridFSDBFile.getContentType());
        docEntity.setUploadDate(gridFSDBFile.getUploadDate());

        return docEntity;
    }

    /**
     * 检查文件是否存在
     * @param fileName
     * @return
     */
    public static boolean checkFileisExist(String fileName){

        List<GridFSDBFile> gridFSDBFiles=gridFS.find(fileName);
        if(gridFSDBFiles.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * 获取文档内容
     * @param fileName
     * @return
     */
    public static byte[] getDocContentsFromGridFs(String fileName){
        byte[] buffer=null;
        GridFSDBFile gridFSDBFile=gridFS.findOne(fileName);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            gridFSDBFile.writeTo(out);
            buffer=out.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                out.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return buffer;
    }


    /**
     * 删除文件
     * @param fileName
     */
    public static void deleteFileFromGridFs(String fileName){
        if(checkFileisExist(fileName)){
            gridFS.remove(fileName);
        }
        else{
            System.out.println("文件不存在");
        }
    }

    /**
     * 更新文档
     * @param fileName
     * @param content
     */
    public static void updateFileToGridFs(String fileName, String contentType,DBObject metadata,byte[] content){
        //先删除数据库中的文件
        deleteFileFromGridFs(fileName);
        //再执行上传
        store(fileName,contentType,metadata,content);
        System.out.println("---savefile--------");

    }


    /**
     * 具体执行存储的代码
     * @param fileName
     * @param contentType
     * @param metadata
     * @param content
     */
    private static void store(String fileName, String contentType,DBObject metadata,byte[] content){
        GridFSInputFile gridFSInputFile=gridFS.createFile(content);
        gridFSInputFile.setContentType(contentType);
        gridFSInputFile.setFilename(fileName);
        gridFSInputFile.setMetaData(metadata);
        gridFSInputFile.save();
    }

    /**
     * 上传文档至GridFs
     * @param path
     * @param filename
     */
    public static boolean uploadFileToGridFs(String path,String filename,String contentType){

        boolean status=true;
        DBObject metaData = new BasicDBObject();
        String filenameMd5=getMd5(filename)+"."+contentType;
        if(checkFileisExist(filenameMd5)){
            System.out.println("数据库中存在同名文件，请删除后再上传");
            status=false;
        }
        else {

            InputStream fis = null;
            try {
                fis = new BufferedInputStream(new FileInputStream(path + filename));
                byte[] buffer = new byte[fis.available()];
                String sha256 = getHash256(buffer);
                metaData.put("sha256", sha256);
                metaData.put("filename",filename);

                store(filenameMd5,contentType,metaData,buffer);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                status = false;
            } catch (IOException e) {
                e.printStackTrace();
                status = false;
            } finally {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        status = false;
                    }
                }
            }
        return status;
    }

    /**
     * 在gridfs中创建一个空的文档
     * @param Project_Name  项目的名称 不包含 ".doc"
     * @param contentType
     */
    public static String createNewEmptyDocFileToGridFs(String Project_Name,String contentType){

        String templateFile=templateFilePath;
        String filenameMd5=getMd5(Project_Name)+"."+contentType;
        if(checkFileisExist(filenameMd5)){
            System.out.println("系统存在同名文件");
        }
        else{
            File file=new File(templateFile);
            DBObject metaData = new BasicDBObject();
            InputStream fis=null;
            try {
                fis = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer=new byte[fis.available()];
                fis.read(buffer);
                String sha256=getHash256(buffer);
                metaData.put("sha256",sha256);
                metaData.put("filename",Project_Name+"."+contentType);

                store(filenameMd5, contentType, metaData,buffer);
            }catch(FileNotFoundException e){
                e.getStackTrace();
            }catch(IOException e){
                e.printStackTrace();
            }finally {
                try{
                    if(fis !=null){
                        fis.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return filenameMd5;
    }


    /**
     * 读取pdf表中的文档
     * @param filename
     * @return
     * @throws IOException
     */
    public static byte[] getPdfContent(String filename) throws IOException{
        byte[] buffer=null;
        GridFSDBFile gridFSDBFile=pdfGridFS.findOne(filename);
        ByteArrayOutputStream out = null;
        try {
            out = new ByteArrayOutputStream();
            gridFSDBFile.writeTo(out);
            buffer=out.toByteArray();
        }catch(IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                out.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return buffer;
    }


    /**
     * 检查文件是否存在于pdf表
     * @param fileName
     * @return
     */
    public static boolean checkFileisExistInPdf(String fileName){

        List<GridFSDBFile> gridFSDBFiles=pdfGridFS.find(fileName);
        if(gridFSDBFiles.isEmpty()){
            return false;
        }
        else{
            return true;
        }
    }



    public static byte[] readFileByBytes(File file) {
        InputStream in = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            in = new FileInputStream(file);
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = in.read(buf)) != -1) {
                out.write(buf, 0, length);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return out.toByteArray();
    }

    public static void main(String[] args) throws IOException{
//        createNewEmptyDocFileToGridFs("test","docx");
        String dir="E:\\pdfdoc";
        File file=new File(dir);
        File flist[] = file.listFiles();

        for (File f:flist
             ) {
            byte[] content=readFileByBytes(f);
            String sha256=getHash256(content);
            String filenameMd5=getMd5(f.getName())+".pdf";
            DBObject metaData = new BasicDBObject();
            metaData.put("sha256",sha256);
            metaData.put("filename",f.getName());
            GridFSInputFile gridFSInputFile=pdfGridFS.createFile(f);
            gridFSInputFile.setFilename(filenameMd5);
            gridFSInputFile.setContentType("pdf");
            gridFSInputFile.setMetaData(metaData);
            gridFSInputFile.save();
        }

    }
}
