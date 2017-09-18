package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.entity.SegmentEntity;
import cn.edu.szu.bigdata.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by longhao on 2017/8/30.
 * Email: longhao1@email.szu.edu.cn
 */
public interface DocumentMapper extends MyMapper<SegmentEntity> {

    List<SegmentEntity> getSegmentEntitys();

    SegmentEntity getSegmentEntityByName(@Param(value = "name") String name);
}
