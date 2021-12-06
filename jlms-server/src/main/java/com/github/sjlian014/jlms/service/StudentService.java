package com.github.sjlian014.jlms.service;

import java.util.ArrayList;
import java.util.List;

import com.github.sjlian014.jlms.dao.StudentRepository;
import com.github.sjlian014.jlms.model.EmailAddress;
import com.github.sjlian014.jlms.model.EmailAddress.EmailAddressType;
import com.github.sjlian014.jlms.model.Student;
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

}
