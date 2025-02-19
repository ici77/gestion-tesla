package com.tesla.gestion_tesla;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "API de Gestión Tesla",
                version = "1.0",
                description = "Esta API permite la gestión de clientes, coches, ventas y revisiones en el sistema Tesla."
        )
)

@SpringBootApplication
@ComponentScan(basePackages = "com.tesla")  // 🔹 Asegura que se detectan los controladores
@EnableJpaRepositories(basePackages = "com.tesla.repository")  // 🔹 Asegura que detecta repositorios
@EntityScan(basePackages = "com.tesla.model")  // 🔹 Asegura que escanea las entidades
public class GestionTeslaApplication {
    public static void main(String[] args) {
        SpringApplication.run(GestionTeslaApplication.class, args);
    }

    // 🔹 Configuración global de CORS
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir todas las rutas
                        .allowedOrigins("http://127.0.0.1:5500") // Permitir solicitudes desde el frontend
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowCredentials(true);
            }
        };
    }
}
