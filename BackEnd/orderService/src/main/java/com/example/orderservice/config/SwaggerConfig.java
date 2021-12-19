package com.example.orderservice.config;



import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.*;

@Configuration  //告诉Spring容器，这个类是一个配置类
@EnableSwagger2  //启用Swagger2
//@EnableSwaggerBootstrapUI
//@EnableWebMvc  //必须用这个，不然会有空指针报错问题
public class SwaggerConfig /*implements WebMvcConfigurer*/ {


    @Bean
    public Docket createUserApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("orderService")
                .apiInfo(apiUserInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.orderservice.controller"))// com包下所有API都交给Swagger2管理
                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
    }

    /**
     * 此处主要是API文档页面显示信息
     */
    private ApiInfo apiUserInfo() {
        return new ApiInfoBuilder()
                .title("orderAPI") // 标题
                .description("订单") // 描述
                .termsOfServiceUrl("https://www.tongji.edu.cn") // 服务网址，一般写公司地址
                .version("1.0") // 版本
                .build();
    }

//    @Override   //重载拦截器
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("swagger-ui.html")
//                .addResourceLocations("classpath:/META-INF/resources/");
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//    }



}
