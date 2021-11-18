package com.github.sjlian014.jlms;

import java.util.ArrayList;
import java.util.List;

import com.github.sjlian014.jlms.model.EmailAddress;
import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.model.EmailAddress.EmailAddressType;
import com.github.sjlian014.jlms.model.Student.Major;
import com.github.sjlian014.jlms.util.Util;

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

	@GetMapping("/students")
	public List<Student> getStudents() {
		return List.of(new Student(1, "stu", "dent", "1", Util.getDate(1000, 4, 22)),
					   new Student(2, "john", "", "", Util.getDate(2021, 11, 17)),
					   new Student(3, "john", "", "", Util.getDate(2021, 11, 17)));
	}

	@GetMapping("/students/sample")
	public Student getSampleStudnet() {
		Student sampleStudent = new Student(1, "random", "student", "1", Util.getDate(2000, 1, 10));
		ArrayList<EmailAddress> emailAddresses = new ArrayList<>();
		emailAddresses.add(new EmailAddress("email@uni.edu", EmailAddressType.UNIVERSITY));
		emailAddresses.add(new EmailAddress("email@m.com", EmailAddressType.PERSONAL));
		sampleStudent.setEmailAddresses(emailAddresses);
		sampleStudent.setMajor(Major.MAJOR1);
		return sampleStudent;
	}

}
