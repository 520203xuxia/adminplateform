package cn.edu.szu.bigdata.service;

import cn.edu.szu.bigdata.entity.SegmentEntity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by longhao on 2017/8/30.
 * Email: longhao1@email.szu.edu.cn
 */
public interface DocumentService extends IService<SegmentEntity>{

    /**
     * 将排序后的列表栏目,组装成LinkedHashMap
     * 数据库返回的数据格式如下:1 xx;1.1 xx;1.2 xx;2 xx;2.1 xx;2.2 xx;3 xx;3.1 xx;
     * 组装成:     key:1 xxx;value:{1.1 xx;1.2 xx};
     *            key:2 xx;value:{2.1 xx;2.2 xx};
     *            key:3 xx;value:{3.1 xx;3.2 xx};
     * @return
     */
    HashMap<SegmentEntity,List<SegmentEntity>> getAllSegments();

    List<SegmentEntity> getAllSegmentName();

}
