package cn.edu.szu.bigdata.controller;


import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.entity.SegmentEntity;
import cn.edu.szu.bigdata.entity.SegmentInfoEntity;
import cn.edu.szu.bigdata.service.SegmentInfoService;
import cn.edu.szu.bigdata.service.SegmentService;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by longhao on 2017/9/27.
 * Email: longhao1@email.szu.edu.cn
 */
@RestController
public class SegmentController {

    @Autowired
    SegmentService segmentService;

    @Autowired
    SegmentInfoService segmentInfoService;

    @Autowired
    private SolrClient solrClient;

    @GetMapping("/segments/{segment_name}")
    public List<SegmentEntity> getSimilarSegment(@PathVariable("segment_name") String segment_name){
        return segmentService.getSegmentEntityListBYSegmentName(segment_name);
    }


    /**
     * 获取相关的段落信息,目前数据不完善,不可用
     * @param segment_name
     * @param project_name
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @GetMapping("/segments/{segment_name}/reports/{project_name}")
    public List<SegmentEntity> getRelationSegment(@PathVariable("segment_name") String segment_name,@PathVariable("project_name") String project_name)
            throws IOException,SolrServerException {
        List<SegmentEntity> segmentEntityList=new ArrayList<>();
        try {
            ModifiableSolrParams params = new ModifiableSolrParams();
            params.add("q", "project_name:" + project_name);
            QueryResponse query = solrClient.query(params);
            //查询结果
            List<ReportEntity> beans = query.getBeans(ReportEntity.class);
            for (ReportEntity bean:beans
                 ) {
                String relation_project_name=bean.getProject_name();
                SegmentEntity segmentEntity=segmentService.getSegmentEntityListBySegmentNameAndProjectName(segment_name,relation_project_name);
                System.out.println(segmentEntity.toString());
                segmentEntityList.add(segmentEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return segmentEntityList;
    }

    @GetMapping("/segmentInfo")
    public List<SegmentInfoEntity> getAllSegmentInfo(){
        return segmentInfoService.getAllSegmentInfo();
    }

}
