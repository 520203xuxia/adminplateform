package cn.edu.szu.bigdata.entity;

import lombok.*;
import org.apache.solr.client.solrj.beans.Field;

/**
 * Created by longhao on 2017/8/23.
 * Email: longhao1@email.szu.edu.cn
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReportEntity {
    /**
     * 项目的名称，也是环评报告的名称
     */
    @Field("project_name")
    private String projectName;

    /**
     * 项目所在的省级行政单位
     */
    @Field("project_province")
    private String projectProvince;


    /**
     * 项目所在的城市，到县市区
     */
    @Field("project_city")
    private String projectCity;

    /**
     * 项目所属的领域，如：社会区域
     */
    @Field("type_name")
    private String projectDomain;


}
