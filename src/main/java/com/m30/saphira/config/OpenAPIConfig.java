package com.m30.saphira.config;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.utils.SpringDocUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ProblemDetail;

@Configuration
public class OpenAPIConfig {

    // main OpenAPI configuration bean
    @Bean
    public OpenAPI customOpenAPI( ) {
        return new OpenAPI()
                .info(new Info()
                        .title("Investment portfolio management platform with AI technology.")
                        .version("v1.0")
                        .description("API for investment and investor management.")
                        .contact(new Contact()
                                .name("Pedro Fernandes")
                                .email("pedrofernandesctt@gmail.com")
                                .url("https://www.linkedin.com/in/pedrofernandesh/"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html" ))
                );
    }

    // this static block instructs SpringDoc to use our custom schema
    // whenever it encounters the ProblemDetail class.
    static {
        SpringDocUtils.getConfig( ).replaceWithClass(
                ProblemDetail.class,
                ProblemDetailSchema.class
        );
    }

    // this interface defines how a ProblemDetail (standard error response)
    // should be documented in Swagger/OpenAPI.
    @Schema(
            name = "ProblemDetail",
            description = "An error representation following the RFC 7807 standard.",
            example = """
                  {
                    "type": "about:blank",
                    "title": "Not Found",
                    "status": 404,
                    "detail": "Investor with ID 123e4567-e89b-12d3-a456-426614174000 was not found.",
                    "instance": "/investor/find/id/123e4567-e89b-12d3-a456-426614174000"
                  }
                  """
    )

    interface ProblemDetailSchema {}

}



