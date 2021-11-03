package springbootbasicsetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootBasicSetupApp {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBasicSetupApp.class, args);
	}

	@RequestMapping("/custom_path")
	public String hello() {
		return "hello";
	}

	@RequestMapping("/another_custom_path")
	public String goodbye() {
		return "goodbye";
	}

}
