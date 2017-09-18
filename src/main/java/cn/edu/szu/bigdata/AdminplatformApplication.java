package cn.edu.szu.bigdata;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "cn.edu.szu.bigdata.mapper")
public class AdminplatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminplatformApplication.class, args);
	}
}
