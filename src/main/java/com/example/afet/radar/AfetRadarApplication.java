package com.example.afet.radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AfetRadarApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfetRadarApplication.class, args);
	}

}
