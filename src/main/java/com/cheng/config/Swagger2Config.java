package com.cheng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Config {


    //Swagger2
    // 核心配置Docket
//    @Bean
////    public Docket createDocket(){
////        return new Docket(DocumentationType.SWAGGER_2)//指定api类型为swagger2
////                .groupName("v1.0")
////                .apiInfo(apiInfo())//定义api文档信息
////                .select().apis(RequestHandlerSelectors.basePackage("com.controller.additional")) //指定扫描接口
////                .paths(PathSelectors.any()) //所有都需要生成
////                .build();
////    }

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("v1.0")
                .select()
                .build();
    }


    //配置swagger信息
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("codeC的swagger文档")
                .description("完整的人")
                .version("v1.0")
                .termsOfServiceUrl("")
                .contact(new Contact("codeC","/","1845472368@qq.com"))
                .build();
    }


}
