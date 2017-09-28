package cn.edu.szu.bigdata.service;

import cn.edu.szu.bigdata.entity.SegmentInfoEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by longhao on 2017/8/30.
 * Email: longhao1@email.szu.edu.cn
 */
public interface SegmentInfoService extends IService<SegmentInfoEntity>{

    List<SegmentInfoEntity> selectByPage(SegmentInfoEntity segmentInfoEntity, int start, int length);

    List<SegmentInfoEntity> getAllSegmentInfo();

    SegmentInfoEntity getSegmentInfoById(int id);

}
