package cn.popo.news.core.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: popo
 * @computer：Administrator
 * @create 2018-05-24 下午 3:31
 * @Description description
 */

@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(buildApiInf())
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.popo.news.core.controller.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo buildApiInf(){
        return new ApiInfoBuilder()
                .title("妙漫网创 RESTful APIs 文档")
                .description("如有疑问请加QQ：331139839")
                .termsOfServiceUrl("https://www.cdrysj.com")
                .contact(new Contact("popo", "https://www.cdrysj.com", "331139839@qq.com"))
                .build();

    }
}
