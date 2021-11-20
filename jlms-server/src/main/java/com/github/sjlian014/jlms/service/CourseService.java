package com.github.sjlian014.jlms.service;

import java.util.List;

import com.github.sjlian014.jlms.dao.CourseRepository;
import com.github.sjlian014.jlms.model.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAll() {
        return courseRepository.findAll();
    }

    public void createSampleCourse() {
        courseRepository.save(new Course("course 1"));
    }

}
