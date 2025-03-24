package adc.task.configurations;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@Configuration
@OpenAPIDefinition(
		info=@Info(
			title="Task API",
			version="1.0",
			description="API for task management"
		)
)
public class OpenApiConfig {

}
