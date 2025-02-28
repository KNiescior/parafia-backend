package com.example.holyclick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class HolyclickApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolyclickApplication.class, args);
	}

}
