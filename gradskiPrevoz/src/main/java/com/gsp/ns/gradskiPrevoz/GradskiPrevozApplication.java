package com.gsp.ns.gradskiPrevoz;

import com.gsp.ns.gradskiPrevoz.fileStorage.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableConfigurationProperties(FileProperties.class)
public class GradskiPrevozApplication {

	public static void main(String[] args) {
		SpringApplication.run(GradskiPrevozApplication.class, args);
	}
}
