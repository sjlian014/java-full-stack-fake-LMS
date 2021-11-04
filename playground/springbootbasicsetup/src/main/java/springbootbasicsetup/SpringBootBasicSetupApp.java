package springbootbasicsetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springbootbasicsetup.beans.ShijiaBean;
import springbootbasicsetup.beans.ShijiaBeanWithPrefix;
import springbootbasicsetup.beans.SomeoneElse;

@RestController
@SpringBootApplication
public class SpringBootBasicSetupApp {

	@Autowired
	ShijiaBean shijiaBean;

	@Autowired
	ShijiaBeanWithPrefix sjbwp;

	@Autowired
	SomeoneElse somebody;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootBasicSetupApp.class, args);
	}

	@RequestMapping("/beans/shijia")
	public String shijiaBean() {
		return String.format("name:%s major:%s role:%s", shijiaBean.getName(), shijiaBean.getMajor(),
				shijiaBean.getRole());
	}

	@RequestMapping("/beans/shijia_but_with_prefix")
	public String shijiaBeanWithPrefix() {
		return String.format("name:%s major:%s role:%s", sjbwp.getName(), sjbwp.getMajor(), sjbwp.getRole());
	}

	@RequestMapping("/beans/someone_else")
	public String somebodyElse() {
		return String.format("name:%s role:%s", somebody.getName(), somebody.getRole());
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
