package com.github.sjlian014.jlms.controller;

import java.util.List;

import com.github.sjlian014.jlms.model.Course;
import com.github.sjlian014.jlms.model.CourseRequest;
import com.github.sjlian014.jlms.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "courses")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAll();
    }

    // @GetMapping(path = "/by_name")
    // public Course getCourseByName(@PathParam("name") String name) {
    //     System.out.println("[DEBUG] name=" + name);
    //     return courseService.getByName(name);
    // }

    @PostMapping
    public Course postCourse(@RequestBody Course course) {
        return courseService.add(course);
    }

    @DeleteMapping(path = "{courseID}")
    public void deleteCourse(@PathVariable("courseID") Long id) {
        courseService.delete(id);
    }

    @PutMapping(path = "{courseID}")
    public Course updateCourse(@PathVariable("courseID") Long id, @RequestBody CourseRequest courseRequest) {
        return courseService.update(id, courseRequest);
    }

}
