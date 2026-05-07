package com.zqtravel.shop.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server devServer = new Server();
        devServer.setUrl("http://localhost:8005");
        devServer.setDescription("开发环境");

        Server prodServer = new Server();
        prodServer.setUrl("https://shop.zqtravel.com");
        prodServer.setDescription("生产环境");

        Contact contact = new Contact();
        contact.setEmail("contact@zqtravel.com");
        contact.setName("肇庆旅游技术团队");
        contact.setUrl("https://www.zqtravel.com");

        License mitLicense = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("肇庆旅游商店服务 API")
                .version("1.0.0")
                .contact(contact)
                .description("商店服务API文档，提供商品管理、购物车、订单等功能")
                .license(mitLicense);

        return new OpenAPI()
                .info(info)
                .servers(List.of(devServer, prodServer));
    }
}