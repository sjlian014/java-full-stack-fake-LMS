package com.github.sjlian014.jlms.service;

import java.util.List;

import com.github.sjlian014.jlms.dao.CourseRepository;
import com.github.sjlian014.jlms.model.Course;
import com.github.sjlian014.jlms.model.CourseRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // public Course getByName(String name) {
    //     return courseRepository.findByName(name).map(course-> {
    //             return course;
    //         }).orElseGet(()-> {throw new IllegalStateException("specified course not found");});
    // }

    public Course add(Course course) {
        return courseRepository.save(course);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    @Transactional
    public Course update(Long id, CourseRequest courseRequest) {
        return courseRepository.findById(id).map(course-> {
                course.setName(courseRequest.getName());
                return course;
            }).orElseThrow(()-> new IllegalStateException("specified course not found"));
    }

}
