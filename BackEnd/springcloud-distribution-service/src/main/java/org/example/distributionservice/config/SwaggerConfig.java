package org.example.distributionservice.config;

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

/**
 * @description:
 * @author: LuBixing
 * @date: 2021/12/20 21:24
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    //创建接口文档
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //扫描org.example.controller包下文件
                .apis(RequestHandlerSelectors.basePackage("org.example.distributionservice.controller"))
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
                .title("派单微服务")
                .description("派单微服务")
                .version("1.0")
                .build();
    }
}
