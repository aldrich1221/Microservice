package com.spring.microservice.controllers;

import com.spring.microservice.Services.CustomCourseService;
import com.spring.microservice.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/custom-courses")
public class CustomCourseController {

    @Autowired
    private CustomCourseService customCourseService;

    @GetMapping
    public List<Course> getCoursesByCustomCriteria(@RequestParam String courseName, @RequestParam String description) {
        return customCourseService.findCoursesByCustomCriteria(courseName, description);
    }
}