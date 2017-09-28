package cn.edu.szu.bigdata.service.impl;

import cn.edu.szu.bigdata.entity.SegmentInfoEntity;
import cn.edu.szu.bigdata.mapper.SegmentInfoMapper;
import cn.edu.szu.bigdata.service.SegmentInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

/**
 * Created by longhao on 2017/9/4.
 * Email: longhao1@email.szu.edu.cn
 */
@Service("segmentInfoService")
public class SegmentInfoServiceImpl extends BaseService<SegmentInfoEntity> implements SegmentInfoService {

    private final SegmentInfoMapper segmentInfoMapper;

    public SegmentInfoServiceImpl(SegmentInfoMapper segmentInfoMapper){
        this.segmentInfoMapper = segmentInfoMapper;
    }


    @Override
    public List<SegmentInfoEntity> selectByPage(SegmentInfoEntity segmentInfoEntity, int start, int length) {
        int page = start/length+1;
        Example example = new Example(SegmentInfoEntity.class);
        //分页查询
        PageHelper.startPage(page, length);
        List<SegmentInfoEntity> segmentInfoEntityList = selectByExample(example);
        return (List<SegmentInfoEntity>) new PageInfo(segmentInfoEntityList);
    }

    public List<SegmentInfoEntity> getAllSegmentInfo(){
        return segmentInfoMapper.selectAllSegmentInfoEntity();
    }

    @Override
    public SegmentInfoEntity getSegmentInfoById(int id) {
        return segmentInfoMapper.selectSegmentInfoEntityById(id);
    }

}
