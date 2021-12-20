package com.example.userservice.config;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //创建接口文档
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描com.au.sa包下文件
                .apis(RequestHandlerSelectors.basePackage("com.example.userservice.controller"))
                //扫描@Api注解的类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //扫描@ApiOperation注解的方法
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    //接口文档的描述
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("用户管理")
                .description("用户管理模块")
                .version("1.0")
                .build();
    }


}