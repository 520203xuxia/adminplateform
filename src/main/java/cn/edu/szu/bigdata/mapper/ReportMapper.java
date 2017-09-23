package cn.edu.szu.bigdata.mapper;

import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by longhao on 2017/9/22.
 * Email: longhao1@email.szu.edu.cn
 */
public interface ReportMapper extends MyMapper<ReportEntity> {

    List<ReportEntity> getReportEntitysByUsername(@Param(value = "username") String username);

    ReportEntity selectReportEntityById(@Param(value = "id") String id);

    void deleteById(@Param(value = "id") String id);

}
