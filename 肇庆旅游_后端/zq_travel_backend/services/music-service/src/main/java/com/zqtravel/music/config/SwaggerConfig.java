package com.zqtravel.music.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI配置
 * 
 * @author 肇庆旅游开发团队
 * @since 2026-05-06
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8003");
        server.setDescription("音乐服务API服务器");

        Contact contact = new Contact();
        contact.setName("肇庆旅游开发团队");
        contact.setEmail("dev@zqtravel.com");
        contact.setUrl("https://zqtravel.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("肇庆旅游小程序 - 音乐服务API文档")
                .version("1.0.0")
                .contact(contact)
                .description("音乐服务API接口文档，包含音乐分类、音乐列表、播放列表、收藏等功能")
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}