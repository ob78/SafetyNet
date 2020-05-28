package com.safetynet;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.safetynet.util.IInitializeLists;

@SpringBootApplication
public class SafetyNetApplication {

	private static final Logger logger = LoggerFactory.getLogger(SafetyNetApplication.class);

	@Autowired
	private IInitializeLists initLists;

	public static void main(String[] args) {
		logger.info("INFO : Launch SafetyNet");

		SpringApplication.run(SafetyNetApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {

		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

			// initialisation : possibilité 2 (appelé au lancement du programme)
			//initLists.initializeLists();
		};
	}

}
