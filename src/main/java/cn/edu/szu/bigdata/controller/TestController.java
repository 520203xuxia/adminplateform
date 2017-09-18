package cn.edu.szu.bigdata.controller;

import cn.edu.szu.bigdata.entity.ReportEntity;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * Created by longhao on 2017/9/5.
 * Email: longhao1@email.szu.edu.cn
 */
@RestController
public class TestController {

    @Autowired
    private SolrClient solrClient;


    @GetMapping("/solr/{name}")
    public List<ReportEntity> getPdfList(@PathVariable("name") String name) throws IOException,SolrServerException {
        try {
                ModifiableSolrParams params = new ModifiableSolrParams();
                params.add("q", "project_name:" + name);
                QueryResponse query = solrClient.query(params);
                //查询结果
                SolrDocumentList results = query.getResults();
                //将查询结果直接转化为List，这里有个坑，对象每个属性必须要加 @Field("id") 属性，包为import org.apache.solr.client.solrj.beans.Field;
                //如果不加属性的话，会返回相等长度的的List，但是List里面每个对象的值均为空
                List<ReportEntity> beans = query.getBeans(ReportEntity.class);
                return beans;
            }catch (Exception e){
                e.printStackTrace();
        }
        return null;
    }

}
