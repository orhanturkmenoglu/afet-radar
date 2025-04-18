package com.example.afet.radar.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(info()
                        .contact(contact())
                );
    }

    @Bean
    public Contact contact() {
        return new Contact()
                .name("Orhan TÜRKMENOĞLU")
                .email("orhantrkmn749@gmail.com")
                .url("https://www.linkedin.com/in/orhanturkmenoglu/");
    }

    @Bean
    public Info info() {
        return new Info()
                .title("Afet Radar - Gerçek Zamanlı Deprem Uyarı API'si")
                .description("Afet Radar API, kullanıcıları gerçek zamanlı deprem uyarıları ile bilgilendiren bir hizmet sunar. WebSocket tabanlı acil durum bildirimleri sağlar.")
                .version("1.0.0");
    }
}
