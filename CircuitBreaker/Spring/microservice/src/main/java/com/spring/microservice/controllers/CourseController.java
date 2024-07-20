package com.spring.microservice.controllers;
import com.spring.microservice.Services.CourseService;
import com.spring.microservice.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseService.createCourse(course);
    }

    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/{id}")
    public Course updateCourse(@PathVariable String id, @RequestBody Course courseDetails) {
        return courseService.updateCourse(id, courseDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable String id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/name/{courseName}")
    public List<Course> getCoursesByName(@PathVariable String courseName) {
        return courseService.getCoursesByName(courseName);
    }

    @GetMapping("/description/{description}")
    public List<Course> getCoursesByDescription(@PathVariable String description) {
        return courseService.getCoursesByDescription(description);
    }
}