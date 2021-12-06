package com.github.sjlian014.jlms;


import com.github.sjlian014.jlms.controller.MajorController;
import com.github.sjlian014.jlms.controller.StudentController;
import com.github.sjlian014.jlms.dao.MajorRepository;
import com.github.sjlian014.jlms.dao.StudentRepository;
import com.github.sjlian014.jlms.model.EmailAddress;
import com.github.sjlian014.jlms.model.Major;
import com.github.sjlian014.jlms.model.PhoneNumber;
import com.github.sjlian014.jlms.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class JlmsApplication implements CommandLineRunner {

	@Autowired
	private MajorRepository majorRepository;
	@Autowired
	private StudentRepository studentRepository;

	public static void main(String[] args) {
		SpringApplication.run(JlmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		majorRepository.save(new Major("major 1", "the only right major!"));
		Student stu = new Student();

		stu.setFirstName("Shijia");
		stu.setLastName("Lian");
		stu.setEmailAddresses(List.of(new EmailAddress("shijialian4@gmail.com", EmailAddress.EmailAddressType.PERSONAL)));
		stu.setPhoneNumbers(List.of(new PhoneNumber(1111111, PhoneNumber.PhoneNumberType.MOBILE)));
		stu.setMajor(majorRepository.getById(1L));

		studentRepository.save(stu);
	}
}
