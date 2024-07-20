package com.spring.microservice.Services;

import com.spring.microservice.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.spring.microservice.repository.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(String id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course updateCourse(String id, Course courseDetails) {
        Course course = courseRepository.findById(id).orElse(null);
        if (course != null) {
            course.setCourseName(courseDetails.getCourseName());
            course.setDescription(courseDetails.getDescription());
            return courseRepository.save(course);
        }
        return null;
    }

    public void deleteCourse(String id) {
        courseRepository.deleteById(id);
    }

    public List<Course> getCoursesByName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }

    public List<Course> getCoursesByDescription(String description) {
        return courseRepository.findByDescriptionContaining(description);
    }
}