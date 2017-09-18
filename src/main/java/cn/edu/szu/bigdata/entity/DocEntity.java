package cn.edu.szu.bigdata.entity;

import com.mongodb.DBObject;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * Created by longhao on 2017/8/23.
 * Email: longhao1@email.szu.edu.cn
 */
public class DocEntity {
    //id属性是给mongodb用的，用@Id注解修饰
    @Id
    private String uid;

    /**
     * 文档名称
     */
    private String fileName;

    /**
     * 文档大小
     */
    private long length;

    /**
     * 文档类型
     */
    private String contentType;

    /**
     * 文档提交日期
     */
    private Date UploadDate;

    /**
     * metadata
     */
    private DBObject metadata;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Date getUploadDate() {
        return UploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        UploadDate = uploadDate;
    }

    public DBObject getMetadata() {
        return metadata;
    }

    public void setMetadata(DBObject metadata) {
        this.metadata = metadata;
    }
}
