package cn.edu.szu.bigdata.service;

import cn.edu.szu.bigdata.entity.ReportEntity;

import java.util.List;

/**
 * Created by longhao on 2017/9/22.
 * Email: longhao1@email.szu.edu.cn
 */
public interface ReportService extends IService<ReportEntity> {
    List<ReportEntity> getAllReports(String username);

    public void deleteReportById(String id);

    public ReportEntity selectReportById(String id);
}
