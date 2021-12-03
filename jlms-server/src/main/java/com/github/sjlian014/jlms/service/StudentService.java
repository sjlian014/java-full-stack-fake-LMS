package com.github.sjlian014.jlms.service;

import java.util.ArrayList;
import java.util.List;

import com.github.sjlian014.jlms.dao.StudentRepository;
import com.github.sjlian014.jlms.model.EmailAddress;
import com.github.sjlian014.jlms.model.EmailAddress.EmailAddressType;
import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.model.Student.Major;
import com.github.sjlian014.jlms.util.Util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

	private StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getAll() {
		return studentRepository.findAll();
	}

	public Student add(Student student) {
		return studentRepository.save(student);
	}

	public void delete(Long id) {
		studentRepository.deleteById(id);
	}

	public Student getSample() {
		Student sampleStudent = new Student("random", "student", "1", Util.getDate(2000, 1, 10));
		ArrayList<EmailAddress> emailAddresses = new ArrayList<>();
		emailAddresses.add(new EmailAddress("email@uni.edu", EmailAddressType.UNIVERSITY));
        emailAddresses.add(new EmailAddress("email@m.com", EmailAddressType.PERSONAL));
		sampleStudent.setEmailAddresses(emailAddresses);
		sampleStudent.setMajor(Major.MAJOR1);
		return sampleStudent;
	}

}
