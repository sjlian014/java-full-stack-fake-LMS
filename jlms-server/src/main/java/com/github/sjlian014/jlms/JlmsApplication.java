package com.github.sjlian014.jlms;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class JlmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JlmsApplication.class, args);
	}

	@GetMapping("/")
	public String helloWorld() {
		return """
			{
				"project name"="jlms"
				"project desc"="a full stack web app implemented in pure java with spring boot + derby as backand, and javafx as frontend"
				"status"="running"
			}
			"""; // fake json for a fake project
	}


}
