package net.cnway.springboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "spring.dataSource")
    @Bean
    public DataSource druid(){

        return  new DruidDataSource();
    }
    //配置druid的监控
    //配置一个后台管理的Servlet
    @Bean
    public ServletRegistrationBean statViewServlet(){
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        Map<String, String> map = new HashMap<>();
        map.put("loginUsername","admin");
        map.put("loginPassword","123456");
        map.put("allow","");
        map.put("","");
        bean.setInitParameters(map);
        return bean;
    }
    //配置一个监控的filter
    @Bean
    public FilterRegistrationBean webStatFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String,String> map= new HashMap<>();
        map.put("exclusions","*.js,*.css,/druid/");

        bean.setInitParameters(map);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return  bean;
    }
}
