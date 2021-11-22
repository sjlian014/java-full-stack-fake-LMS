package com.github.sjlian014.jlms.service;

import java.util.ArrayList;
import java.util.List;

import com.github.sjlian014.jlms.model.EmailAddress;
import com.github.sjlian014.jlms.model.EmailAddress.EmailAddressType;
import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.model.Student.Major;
import com.github.sjlian014.jlms.util.Util;

import org.springframework.stereotype.Service;

@Service
public class StudentService {

	public List<Student> getAll() {
		return List.of(new Student(1L, "stu", "dent", "1", Util.getDate(1000, 4, 22)),
					   new Student(2L, "john", "", "", Util.getDate(2021, 11, 17)),
					   new Student(3L, "john", "", "", Util.getDate(2021, 11, 17)));
	}

	public Student getSample() {
		Student sampleStudent = new Student(1L, "random", "student", "1", Util.getDate(2000, 1, 10));
		ArrayList<EmailAddress> emailAddresses = new ArrayList<>();
		emailAddresses.add(new EmailAddress("email@uni.edu", EmailAddressType.UNIVERSITY));
        emailAddresses.add(new EmailAddress("email@m.com", EmailAddressType.PERSONAL));
		sampleStudent.setEmailAddresses(emailAddresses);
		sampleStudent.setMajor(Major.MAJOR1);
		return sampleStudent;
	}

}
