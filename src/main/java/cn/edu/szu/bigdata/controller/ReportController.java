package cn.edu.szu.bigdata.controller;

import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.service.GridFsService;
import cn.edu.szu.bigdata.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by longhao on 2017/9/22.
 * Email: longhao1@email.szu.edu.cn
 */
@RestController
public class ReportController {

    @Autowired
    ReportService reportService;

    /**
     * 获取用户所有报告
     * @param username
     * @return
     */
    @GetMapping("/report/{username}")
    public List<ReportEntity> getCurrentUserAllDocument(@PathVariable("username") String username){
        return reportService.getAllReports(username);
    }

    /**
     * 修改报告基本信息(不包括内容)
     */
    @PostMapping("/report/update")
    public void updateReport(HttpServletRequest request){
        ReportEntity reportEntity=(ReportEntity)request.getAttribute("report");
        if(reportService.selectReportById(reportEntity.getId())!=null){
            reportService.deleteReportById(reportEntity.getId());
        }
        reportService.updateAll(reportEntity);
    }

    /**
     * 删除报告(包括内容)
     */
    @GetMapping("/report/{id}/delete")
    public void deleteReport(@PathVariable("id") String id){
        reportService.deleteReportById(id);//删除基本信息
        GridFsService.deleteFileFromGridFs(id);//删除文件内容
    }

    /**
     * 通过id获取报告基本信息
     * @param id
     * @return
     */
    @GetMapping("/report/{id}")
    public ReportEntity getReportById(@PathVariable("id") String id){
        return reportService.selectReportById(id);
    }

}
