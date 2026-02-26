package com.hotel.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
//@EnableFeignClients
public class HabitacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionApplication.class, args);
	}

}
