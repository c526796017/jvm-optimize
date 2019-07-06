/**
 * Copyright (C), 2018-2019, 康之家有限公司
 * FileName: Swagger
 * Author:   Administrator
 * Date:     2019/7/6 0006 16:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 杨浩宇           修改时间           版本号              描述
 */
package com.mama;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger {

    @Bean
    public Docket createRestApi() {
        List<Parameter> pars = new ArrayList<>();
        Parameter tokenP = (new ParameterBuilder()).name("token").description("user token").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenP);    //根据每个方法名也知道当前方法在设置什么参数
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mama.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试文档")
                .description("测试虚拟机优化的文档")
                .version("1.0")
                .build();
    }
}
