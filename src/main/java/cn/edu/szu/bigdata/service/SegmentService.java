package cn.edu.szu.bigdata.service;

import cn.edu.szu.bigdata.entity.SegmentEntity;

import java.util.List;

/**
 * Created by longhao on 2017/9/27.
 * Email: longhao1@email.szu.edu.cn
 */
public interface SegmentService extends IService<SegmentEntity>{

    List<SegmentEntity> getSegmentEntityListBYSegmentName(String segment_name);
    SegmentEntity getSegmentEntityListBySegmentNameAndProjectName(String segment_name,String project_name);
}
