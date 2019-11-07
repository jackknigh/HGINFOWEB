package com.config;

import com.dto.enums.SessionItem;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CuiL on 2018-11-01.
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestSysApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.api"))
                .paths(PathSelectors.ant("/sysapi/**"))
                .build().globalOperationParameters(getComParameters())
                .groupName("SysApi").pathMapping("/");
    }
    @Bean
    public Docket createRestZjReportApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.api"))
                .paths(PathSelectors.ant("/zjReport/**"))
                .build().globalOperationParameters(getComParameters())
                .groupName("ZjReport").pathMapping("/");
    }
    @Bean
    public Docket createRestZjSourceDataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.api"))
                .paths(PathSelectors.ant("/ZjSourceData/**"))
                .build().globalOperationParameters(getComParameters())
                .groupName("ZjSourceData").pathMapping("/");
    }
//    @Bean
//    public Docket createRestSystemApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                //为当前包路径
//                .apis(RequestHandlerSelectors.basePackage("com.api"))
//                .paths(PathSelectors.ant("/System/**"))
//                .build().globalOperationParameters(getComParameters())
//                .groupName("System").pathMapping("/");
//    }
    @Bean
    public Docket createRestZjStataApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                //为当前包路径
                .apis(RequestHandlerSelectors.basePackage("com.api"))
                .paths(PathSelectors.ant("/ZjStata/**"))
                .build().globalOperationParameters(getComParameters())
                .groupName("ZjStata").pathMapping("/");
    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                //创建人
                .contact(new Contact("shipinginfo", "http://www.shipinginfo.com/", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

    private List<Parameter> getComParameters(){
        ParameterBuilder ticketPar = new ParameterBuilder();
        ParameterBuilder ticketPar2 = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        ticketPar.name(SessionItem.tokenId.name()).description("tokenId")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以
        ticketPar2.name(SessionItem.noncetimestamp.name()).description("md5(时间戳)")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以

        pars.add(ticketPar.build());    //根据每个方法名也知道当前方法在设置什么参数
        pars.add(ticketPar2.build());    //根据每个方法名也知道当前方法在设置什么参数
        return pars;
    }


}
