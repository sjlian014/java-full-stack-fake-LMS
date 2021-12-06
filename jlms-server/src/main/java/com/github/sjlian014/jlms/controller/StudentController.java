package com.github.sjlian014.jlms.controller;

import java.util.List;

import com.github.sjlian014.jlms.model.Student;
import com.github.sjlian014.jlms.service.StudentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/students")
public class StudentController {

    private final StudentService studentService;

    @Autowired
	public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
	public List<Student> getStudents() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
		return studentService.getAll();
    }

    @PostMapping
    public Student postStudent(@RequestBody Student student) {
        return studentService.add(student);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long id) {
        studentService.delete(id);
    }

    // endpoint for communication for two in-house programs
    // => minimize work for the two programs to talk to each other

    // @PutMapping(path = "{studentId}")
    // public Student updateStudent(@PathVariable("studentId") Long id, @RequestBody StudentRequest) {

    // }

}
