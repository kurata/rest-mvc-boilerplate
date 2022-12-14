package br.com.aqueteron.boilerplate.configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@Configuration
public class SpringFoxConfiguration {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Rest MVC Boilerplate API")
                        .description("It is an API to provide a reference to implement it whit java and spring.")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.OAS_30)
//                .apiInfo(
//                        new ApiInfoBuilder()
//                                .title("Builders client API")
//                                .description("Essa é uma API para o teste tecnico da Builders.")
//                                .version("v1")
//                                .build()
//                )
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
//                .paths(PathSelectors.any())
//                .build()
//                .pathMapping("/")
//                .directModelSubstitute(LocalDate.class, String.class)
//                .genericModelSubstitutes(ResponseEntity.class)
//                .useDefaultResponseMessages(false);
//    }

}
