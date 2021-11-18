package com.github.sjlian014.jlms.controller;

import java.util.ArrayList;
import java.util.List;

import com.github.sjlian014.jlms.model.EmailAddress;
import com.github.sjlian014.jlms.model.EmailAddress.EmailAddressType;
import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.model.Student.Major;
import com.github.sjlian014.jlms.util.Util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "students")
public class StudentController {

	@GetMapping
	public List<Student> getStudents() {
		return List.of(new Student(1, "stu", "dent", "1", Util.getDate(1000, 4, 22)),
					   new Student(2, "john", "", "", Util.getDate(2021, 11, 17)),
					   new Student(3, "john", "", "", Util.getDate(2021, 11, 17)));
	}

	@GetMapping("/sample")
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
