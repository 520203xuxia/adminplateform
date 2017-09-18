package cn.edu.szu.bigdata.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by longhao on 2017/8/23.
 * Email: longhao1@email.szu.edu.cn
 * 由于wopi的接口不遵守驼峰命名规则，所以需要用@JsonProperty指定别名
 * 同时在getter上@JsonIgnore,防止出现2个名字
 */
public class FileInfoEntity {
    /**
     * 文件名
     * */
    @JsonProperty("BaseFileName")
    private String baseFileName;

    /**
     * 文件所有者的唯一编号
     * */
    @JsonProperty("OwnerId")
    private String ownerId;

    /**
     * 文件大小，以bytes为单位
     */
    @JsonProperty("Size")
    private long size;

    /**
     * 文件的256位的SHA-2编码散列内容
     */
    @JsonProperty("SHA256")
    private String sha256;

    /**
     * 文件版本号，如果文件被编辑，版本号也要跟着改变
     */
    @JsonProperty("Version")
    private long version;

    /**
     *允许外部服务的连接
     */
    @JsonProperty("AllowExternalMarketplace")
    private boolean allowExternalMarketplace;

    /**
     * 更改文件的权限
     */
    @JsonProperty("UserCanWrite")
    private boolean userCanWrite;

    /**
     * 是否支持更新
     */
    @JsonProperty("SupportsUpdate")
    private boolean supportsUpdate;

    /**
     * 是否支持锁定
     */
    @JsonProperty("SupportsLocks")
    private boolean supportsLocks;


    @JsonIgnore
    public String getBaseFileName() {
        return baseFileName;
    }

    @JsonIgnore
    public void setBaseFileName(String baseFileName) {
        this.baseFileName = baseFileName;
    }

    @JsonIgnore
    public String getOwnerId() {
        return ownerId;
    }

    @JsonIgnore
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    @JsonIgnore
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @JsonIgnore
    public String getSha256() {
        return sha256;
    }

    public void setSha256(String sha256) {
        this.sha256 = sha256;
    }

    @JsonIgnore
    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @JsonIgnore
    public boolean isAllowExternalMarketplace() {
        return allowExternalMarketplace;
    }

    public void setAllowExternalMarketplace(boolean allowExternalMarketplace) {
        this.allowExternalMarketplace = allowExternalMarketplace;
    }

    @JsonIgnore
    public boolean isUserCanWrite() {
        return userCanWrite;
    }

    public void setUserCanWrite(boolean userCanWrite) {
        this.userCanWrite = userCanWrite;
    }

    @JsonIgnore
    public boolean isSupportsUpdate() {
        return supportsUpdate;
    }

    public void setSupportsUpdate(boolean supportsUpdate) {
        this.supportsUpdate = supportsUpdate;
    }

    @JsonIgnore
    public boolean isSupportsLocks() {
        return supportsLocks;
    }

    public void setSupportsLocks(boolean supportsLocks) {
        this.supportsLocks = supportsLocks;
    }

    @Override
    public String toString() {
        return "FileInfo{"+
                "BaseFileName"+baseFileName+'\''+
                ", ownerId='"+ ownerId +'\''+
                ", size="+size+
                ", sha256='"+sha256+'\''+
                ", version="+version+
                ", allowExternalMarketplace="+allowExternalMarketplace+
                ", userCanWrite="+userCanWrite+
                ", supportsUpdate="+supportsUpdate+
                ", supportsLocks="+supportsLocks+
                '}';
    }
}
