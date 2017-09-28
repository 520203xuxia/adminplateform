package cn.edu.szu.bigdata;

import cn.edu.szu.bigdata.entity.ReportEntity;
import cn.edu.szu.bigdata.service.ReportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminplatformApplicationTests {

	@Autowired
	ReportService reportService;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testMysql(){
		ReportEntity reportEntity=new ReportEntity();
		reportEntity.setId("test");
		reportEntity.setProject_name("中文测试");
		reportEntity.setProject_province("湖北省");
		reportEntity.setProject_city("襄阳市");
		reportEntity.setReport_owner("龙浩");
		reportService.save(reportEntity);
		reportEntity=reportService.selectReportById("test");
		 System.out.println(reportEntity.toString());
	}

}
