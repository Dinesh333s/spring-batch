package net.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "net.springbatch.repo")
@SpringBootApplication(scanBasePackages = "net.springbatch")
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
