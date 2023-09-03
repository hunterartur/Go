package com.simbirsoft.mockauthserver;

import com.simbirsoft.mockauthserver.property.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@EnableConfigurationProperties({JwtProperties.class})
@EnableR2dbcRepositories
@SpringBootApplication
public class MockAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MockAuthServerApplication.class, args);
	}

}
