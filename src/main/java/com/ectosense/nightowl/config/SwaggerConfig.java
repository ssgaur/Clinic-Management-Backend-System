package com.ectosense.nightowl.config;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static springfox.documentation.builders.RequestHandlerSelectors.withMethodAnnotation;

@Component
@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig
{
    private static final String API_NAME                = "API documentation.";
    private static final String API_DESCRIPTION         = "Most of the APIs documentation.";
    private static final String LICENSE_NAME            = "LAUDA Technologies Pvt. Ltd.";
    private static final String LICENSE_URL             = "https://www.google.com";
    private static final String TERMS_OF_SERVICE_URL    = "htttps://www.google.com";
    private static final String API_VERSION             = "1.0.0";

    private Contact contact()
    {
        return new Contact(
                "API documemtation.",
                "https://www.google.com/",
                "shailendra.singh.knp@gmail.com"
        );
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfo(
                API_NAME,
                API_DESCRIPTION,
                API_VERSION,
                TERMS_OF_SERVICE_URL,
                contact(),
                LICENSE_NAME,
                LICENSE_URL,
                Collections.emptyList()
        );
    }

    private ApiKey apiKey() {
        return new ApiKey("mykey", "api_key", "header");
    }

    @Bean
    @Profile("local")
    public Docket docket()
    {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        docket.select()
                .paths(PathSelectors.any())
                .apis(withMethodAnnotation(ApiOperation.class))
                .build();
        docket.pathMapping("/");
        docket.apiInfo(apiInfo());
        docket.securitySchemes(Lists.newArrayList(apiKey()));
        docket.protocols(new HashSet<>(Arrays.asList("http")));
        return docket;
    }
}
