package cn.edu.szu.bigdata.entity;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;

import java.io.Serializable;

/**
 * Created by longhao on 2017/8/23.
 * Email: longhao1@email.szu.edu.cn
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportEntity implements Serializable {

    private String id;
    /**
     * 项目的名称，也是环评报告的名称
     */
    @Field("project_name")
    private String project_name;

    /**
     * 项目所在的省级行政单位
     */
    @Field("project_province")
    private String project_province;


    /**
     * 项目所在的城市，到县市区
     */
    @Field("project_city")
    private String project_city;

    /**
     * 项目所属的领域，如：社会区域
     */
    @Field("type_name")
    private String type_name;

    private String report_owner;

    public ReportEntity(String project_name, String project_province, String project_city, String type_name, String report_owner){
        this.setProject_name(project_name);
        this.setProject_province(project_province);
        this.setProject_city(project_city);
        this.setType_name(type_name);
        this.setReport_owner(report_owner);
    }

    public ReportEntity(String id, String project_name, String report_owner){
        this.setId(id);
        this.setProject_name(project_name);
        this.setReport_owner(report_owner);
    }


}
