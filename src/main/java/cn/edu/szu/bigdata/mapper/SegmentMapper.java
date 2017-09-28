package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.entity.SegmentEntity;
import cn.edu.szu.bigdata.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by longhao on 2017/9/27.
 * Email: longhao1@email.szu.edu.cn
 */
public interface SegmentMapper extends MyMapper<SegmentEntity>{

    List<SegmentEntity> selectSegmentEntityBySegmentName(@Param("segment_name") String segment_name);

    SegmentEntity selectSegmentEntityBySegmentNameAndProjectName(@Param("segment_name") String segment_name,@Param("project_name") String project_name);
}
