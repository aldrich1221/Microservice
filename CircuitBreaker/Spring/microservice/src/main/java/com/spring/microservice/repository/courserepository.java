package com.spring.microservice.repository;

import com.spring.microservice.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findByCourseName(String courseName);
    List<Course> findByDescriptionContaining(String description);
}