package cn.edu.szu.bigdata.service.impl;

import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.mapper.ReportMapper;
import cn.edu.szu.bigdata.service.ReportService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by longhao on 2017/9/22.
 * Email: longhao1@email.szu.edu.cn
 */
@Service("reportService")
public class ReportServiceImpl extends BaseService<ReportEntity> implements ReportService {

    private final ReportMapper reportMapper;

    public ReportServiceImpl(ReportMapper reportMapper){
        this.reportMapper=reportMapper;
    }

    @Override
    public List<ReportEntity> getAllReports(String username) {
        return reportMapper.getReportEntitysByUsername(username);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=false,rollbackFor={Exception.class})
    public void deleteReportById(String id) {
        Logger.getLogger(getClass()).info("delete:"+id);
        reportMapper.deleteById(id);
    }

    @Override
    public ReportEntity selectReportById(String id) {
        return reportMapper.selectReportEntityById(id);
    }


}
