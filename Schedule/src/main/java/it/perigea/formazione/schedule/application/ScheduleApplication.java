package it.perigea.formazione.schedule.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
@EnableFeignClients

public class ScheduleApplication {

	public static void main(String[] args) {

		SpringApplication.run(ScheduleApplication.class, args);

	}
	

}
