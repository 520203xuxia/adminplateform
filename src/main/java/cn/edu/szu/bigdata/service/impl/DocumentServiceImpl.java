package cn.edu.szu.bigdata.service.impl;

import cn.edu.szu.bigdata.entity.SegmentEntity;
import cn.edu.szu.bigdata.mapper.DocumentMapper;
import cn.edu.szu.bigdata.service.DocumentService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by longhao on 2017/9/4.
 * Email: longhao1@email.szu.edu.cn
 */
@Service("documentService")
public class DocumentServiceImpl extends BaseService<SegmentEntity> implements DocumentService {

    private final DocumentMapper documentMapper;

    public DocumentServiceImpl(DocumentMapper documentMapper){
        this.documentMapper = documentMapper;
    }


    /**
     * 将排序后的列表栏目,组装成LinkedHashMap
     * 数据库返回的数据格式如下:1 xx;1.1 xx;1.2 xx;2 xx;2.1 xx;2.2 xx;3 xx;3.1 xx;
     * 组装成:     key:1 xxx;value:{1.1 xx;1.2 xx};
     *            key:2 xx;value:{2.1 xx;2.2 xx};
     *            key:3 xx;value:{3.1 xx;3.2 xx};
     * @return
     */
    public HashMap<SegmentEntity,List<SegmentEntity>> getAllSegments(){

        HashMap<SegmentEntity,List<SegmentEntity>> map=new LinkedHashMap<>();
        List<SegmentEntity> segmentEntityList= documentMapper.getSegmentEntitys();
        List<SegmentEntity> list=null;
        SegmentEntity key=null;
        for (SegmentEntity segmentEntity:segmentEntityList
                ) {
            String strId=Float.toString(segmentEntity.getId());
            BigDecimal b = new BigDecimal(strId);
            //若为一级标题
            if(new BigDecimal(b.intValue()).compareTo(b)==0){
                map.put(key,list);
                key=segmentEntity;
                list=new LinkedList<>();
                //否则为二级标题
            }else{
                list.add(segmentEntity);
            }
        }
        map.put(key,null);
        map.remove(null);
        return map;
    }


    public List<SegmentEntity> getAllSegmentName(){
        return documentMapper.getSegmentEntitys();
    }



}
