package com.controle.mspedidos.config;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Controle de Pedidos e Produtos")
                        .version("1.0.0")
                        .description("API para gerenciamento de pedidos e produtos")
                        .contact(new Contact()
                                .name("Vitor")
                                .email("vitor.caliman99@gmail.com")
                                .url("https://github.com/VitorPCaliman")
                        )
                );
    }
}