package cn.edu.szu.bigdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * Created by longhao on 2017/8/24.
 * Email: longhao1@email.szu.edu.cn
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    /**
     * 此处配置是为了避免@PathVariable dot(.) 之后被截断
     */
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer.setUseSuffixPatternMatch(false);
    }
}
