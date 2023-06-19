package com.assignment.abn.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(info = @Info(title = "ABN Recipe API", version = "1.0", description = "This API can be used to get Recipe information"))
@Configuration
public class SwaggerConfig {

}
