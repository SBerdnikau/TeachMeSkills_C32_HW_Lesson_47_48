package com.tms;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
		title = "Spring Boot JPA Market",
		version = "1.0",
		description = "This is a simple market application on Spring Boot with JPA API.  This API allows users to...",
		contact = @Contact(
				name = "Sergey Berdnikov",
				email = "berdnikausiarhei@test.com"
		)
))

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
