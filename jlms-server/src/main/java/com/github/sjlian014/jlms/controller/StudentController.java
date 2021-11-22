package com.github.sjlian014.jlms.controller;

import java.util.List;

import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
	public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
	public List<Student> getStudents() {
		return studentService.getAll();
    }

	@GetMapping("/sample")
	public Student getSampleStudnet() {
		return studentService.getSample();
	}

}
