package com.example.config;

import com.example.util.JsonUtil;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

@Configuration
public class SpringMvcConfig extends WebMvcConfigurationSupport {

    /**
     * 静态资源放行
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + new ApplicationHome(this.getClass()).getSource().getParent() + "/upload/");
    }


    /**
     * 解决后端传输JSON到前端过程中将过大的LONG刑转换为Number类型精度丢失问题
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JsonUtil());
        converters.add(0, messageConverter);
    }
}

