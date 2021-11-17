package top.top6699.mall.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.top6699.mall.filter.AaCorsFilter;

/**
 * @author LongHaiJiang
 * @date 2021/10/22 17:28
 * @description Admin过滤器的配置
 **/
@Configuration
public class AaCorsFilterConfig {

    @Bean
    public AaCorsFilter aaCorsFilter() {
        return new AaCorsFilter();
    }

    @Bean(name = "aaCorsFilterConf")
    public FilterRegistrationBean adminFilterConfig() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(aaCorsFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("aaCorsFilterConf");
        return filterRegistrationBean;
    }
}
