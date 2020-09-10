package br.gov.sp.iamspe.mensageria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableCircuitBreaker
@EnableScheduling
@SpringBootApplication
public class MensageriaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MensageriaApplication.class, args);
	}

}