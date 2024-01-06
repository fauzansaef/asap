package project.asap;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@OpenAPIDefinition(info = @Info(title = "ASAP API Docs", version = "1.0", description = "Documentation ASAP APIs v1.0"))
public class AsapApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsapApplication.class, args);
	}

}
