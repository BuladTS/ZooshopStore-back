package ru.sfu.zooshopback.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "ZooShopApi",
                description = "ZooShopApi system", version = "1.0.0",
                contact = @Contact(
                        name = "Bulad",
                        email = "tbulad222@gmail.com"
                )
        )
)
public class OpenApiConfig {

}
