package io.metadata.school;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import io.metadata.school.presentation.controller.InitialLoadController;

@SpringBootApplication
public class SchoolApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SchoolApplication.class, args);
		context.getBean(InitialLoadController.class).load();
	}

}
