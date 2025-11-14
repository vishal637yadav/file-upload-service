package com.code.file.upload;

import com.code.file.upload.service.UtilityService;
import com.code.file.upload.utility.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FileUploadServiceApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(FileUploadServiceApplication.class, args);

		System.out.println("---------------");
		System.out.println("Random Alpha Numeric Data :1::"+context.getBean(RandomAlphaNumericStringGenerator.class));
		System.out.println("Random Alpha Numeric Data :2::"+context.getBean(RandomAlphaNumericStringGenerator.class).ofSize(20));
		System.out.println("Random Alpha Numeric Data :3::"+context.getBean(RandomAlphaNumericStringGenerator.class).getData());
		System.out.println("Random Alpha Numeric Data :1::"+context.getBean(RandomUpperCaseAlphabetStringGenerator.class));
		RandomUpperCaseAlphabetStringGenerator upperCaseString = context.getBean(RandomUpperCaseAlphabetStringGenerator.class);
		upperCaseString.ofSize(8);
		System.out.println("RandomUpperCaseAlphabetStringGenerator ::"+upperCaseString);
		System.out.println("Random upperCaseString.getData() :4::"+upperCaseString.getData());

		RandomLowerCaseAlphabetStringGenerator lowerCaseString = context.getBean(RandomLowerCaseAlphabetStringGenerator.class);
		lowerCaseString.ofSize(18);
		System.out.println("RandomLowerCaseAlphabetStringGenerator ::"+lowerCaseString);
		System.out.println("Random lowerCaseString.getData() :4::"+lowerCaseString.getData());


		RandomNumericStringGenerator numericString = context.getBean(RandomNumericStringGenerator.class);
		numericString.ofSize(21);
		System.out.println("RandomLowerCaseAlphabetStringGenerator ::"+numericString);
		System.out.println("Random lowerCaseString.getData() :4::"+numericString.getData());

		System.out.println("--------------");


	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx,
											   RandomUpperCaseAlphabetStringGenerator randomUpperCaseAlphabetStringGenerator,

											   UtilityService utilityService) {
		return args -> {

			System.out.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			List<Integer> intList = List.of(65,66,70,97,98,101,123,76);
			String charString = utilityService.integerListToCharacterStringConversion(intList);
			System.out.println("charString ::"+charString);
		};
	}

}
