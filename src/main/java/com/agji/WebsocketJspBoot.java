package com.agji;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class WebsocketJspBoot {

	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder) {
		return applicationBuilder.sources(WebsocketJspBoot.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(WebsocketJspBoot.class, args);
	}
}
