package com.github.sjlian014.jlms;


import com.github.sjlian014.jlms.controller.MajorController;
import com.github.sjlian014.jlms.controller.StudentController;
import com.github.sjlian014.jlms.dao.MajorRepository;
import com.github.sjlian014.jlms.dao.MinorRepository;
import com.github.sjlian014.jlms.dao.StudentRepository;
import com.github.sjlian014.jlms.model.*;
import com.github.sjlian014.jlms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootApplication
public class JlmsApplication implements CommandLineRunner {

    @Autowired
    private MajorRepository majorRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private MinorRepository minorRepository;
    @Autowired
    private StudentService studentService;

    public static void main(String[] args) {
        SpringApplication.run(JlmsApplication.class, args);
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Resource resource = new ClassPathResource("majors.csv");

        HashMap<String, Major.Category> strToMajorCat = new HashMap<>() {
            {
                var input = Files.lines(Path.of(resource.getURI())).filter(line -> !line.equals("FOD1P,Major,Major_Category")).map((line) -> {
                    var cols = line.split(",");
                    return cols[2];
                }).distinct().toList();
                for(int i = 0; i < input.size(); i ++)
                    this.put(input.get(i), Major.Category.values()[i]);
            }
        };

        var majors = Files.lines(Path.of(resource.getURI())).filter(line->!line.equals("FOD1P,Major,Major_Category")).map((line) -> {
            var cols = line.split(",");
            return new Major(cols[1], strToMajorCat.get(cols[2]));
        }).toList();

        HashMap<String, Minor.Category> strToMinorCat = new HashMap<>() {
            {
                var input = Files.lines(Path.of(resource.getURI())).filter(line -> !line.equals("FOD1P,Major,Major_Category")).map((line) -> {
                    var cols = line.split(",");
                    return cols[2];
                }).distinct().toList();
                for(int i = 0; i < input.size(); i ++)
                    this.put(input.get(i), Minor.Category.values()[i]);
            }
        };
        var minors = Files.lines(Path.of(resource.getURI())).filter(line->!line.equals("FOD1P,Major,Major_Category")).map((line) -> {
            var cols = line.split(",");
            return new Minor(cols[1], strToMinorCat.get(cols[2]));
        }).toList();
        majorRepository.saveAll(majors);
        minorRepository.saveAll(minors);

        final Student student = new Student();
        student.setFirstName("Shijia");
        student.setLastName("Lian");
        student.setMajor(majors.get(1));
        student.setMinor(minors.get(2));
        studentService.add(student);

/*
        System.out.println(student);
        System.out.println(studentService.getAll());
*/

    }
}
