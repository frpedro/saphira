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

    // configura informações da documentação swagger
    @Bean
    public OpenAPI customOpenAPI( ) {
        return new OpenAPI()
                .info(new Info()
                        .title("Software de gerenciamento de portfólio de investimentos com IA.")
                        .version("v1.0")
                        .description("API para gerenciamento de investimentos e investidores.")
                        .contact(new Contact()
                                .name("Pedro Fernandes")
                                .email("pedrofernandesctt@gmail.com")
                                .url(""))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html" ))
                );
    }

    // informa ao springdoc para usar o schema "ProblemDetail"
    // sempre que a classe ProblemDetail for encontrada.
    static {
        SpringDocUtils.getConfig( ).replaceWithClass(
                ProblemDetail.class,
                ProblemDetailSchema.class
        );
    }

    // define como o ProblemDetail (mensagem de erro) deve ser documentado no Swagger/OpenAPI
    @Schema(
            name = "ProblemDetail",
            description = "Representação de um erro RFC 7807",
            example = """
                  {
                    "type": "about:blank",
                    "title": "Not Found",
                    "status": 404,
                    "detail": "Investidor com o ID 123e4567-e89b-12d3-a456-426614174000 não foi encontrado.",
                    "instance": "/investidor/buscar/id/123e4567-e89b-12d3-a456-426614174000"
                  }
                  """
    )
    interface ProblemDetailSchema {}

}



