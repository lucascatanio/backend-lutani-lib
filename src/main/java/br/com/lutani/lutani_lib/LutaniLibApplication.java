package br.com.lutani.lutani_lib;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LutaniLibApplication {

	public static void main(String[] args) {
		SpringApplication.run(LutaniLibApplication.class, args);
	}

}
