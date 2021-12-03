package com.github.sjlian014.jlms.dao;

import java.util.Optional;

import com.github.sjlian014.jlms.model.Course;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    public Optional<Course> findByName(String name);

}
