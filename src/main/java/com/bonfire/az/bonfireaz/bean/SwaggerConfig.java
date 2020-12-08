package com.bonfire.az.bonfireaz.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    Contact contact = new Contact(
            "Bonfire az",
            "http://www.bofire-az.com",
            "developers@bonfire-az.com"
    );

    List<VendorExtension> vendorExtensions = new ArrayList<>();

    ApiInfo apiInfo = new ApiInfo(
            "Bonfire-az documentation",
            "This pages documents Bonfire-az endpoints",
            "1.0",
            "http://www.bofire-az.com",
            contact,
            "Apache 2.0",
            "http://www.apache.org/licenses/LICENSE-2.0",
            vendorExtensions);

    @Bean
    public Docket swaggerConfiguration() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.bonfire.az.bonfireaz"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo);
    }
}
