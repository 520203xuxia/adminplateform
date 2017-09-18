package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.model.Resources;
import cn.edu.szu.bigdata.util.MyMapper;

import java.util.List;
import java.util.Map;

public interface ResourcesMapper extends MyMapper<Resources> {
    public List<Resources> queryAll();

    public List<Resources> loadUserResources(Map<String,Object> map);

    public List<Resources> queryResourcesListWithSelected(Integer rid);
}