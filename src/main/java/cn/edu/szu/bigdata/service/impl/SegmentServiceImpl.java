package cn.edu.szu.bigdata.service.impl;

import cn.edu.szu.bigdata.entity.SegmentEntity;
import cn.edu.szu.bigdata.mapper.SegmentMapper;
import cn.edu.szu.bigdata.service.SegmentService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by longhao on 2017/9/27.
 * Email: longhao1@email.szu.edu.cn
 */
@Service("segmentService")
public class SegmentServiceImpl extends BaseService<SegmentEntity> implements SegmentService {

    private SegmentMapper segmentMapper;

    public SegmentServiceImpl(SegmentMapper segmentMapper){
        this.segmentMapper=segmentMapper;
    }

    @Override
    public List<SegmentEntity> getSegmentEntityListBYSegmentName(String segment_name) {
        return segmentMapper.selectSegmentEntityBySegmentName(segment_name);
    }

    @Override
    public SegmentEntity getSegmentEntityListBySegmentNameAndProjectName(String segment_name, String project_name) {
        return segmentMapper.selectSegmentEntityBySegmentNameAndProjectName(segment_name,project_name);
    }
}
