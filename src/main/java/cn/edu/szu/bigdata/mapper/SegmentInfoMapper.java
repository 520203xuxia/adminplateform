package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.entity.SegmentInfoEntity;
import cn.edu.szu.bigdata.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by longhao on 2017/8/30.
 * Email: longhao1@email.szu.edu.cn
 */
public interface SegmentInfoMapper extends MyMapper<SegmentInfoEntity> {

    List<SegmentInfoEntity> selectAllSegmentInfoEntity();
    SegmentInfoEntity selectSegmentInfoEntityById(@Param("id") int id);

}
