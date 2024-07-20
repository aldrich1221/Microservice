package com.spring.microservice.Services;

import com.spring.microservice.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomCourseService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Course> findCoursesByCustomCriteria(String courseName, String description) {
        Query query = new Query();
        query.addCriteria(Criteria.where("courseName").is(courseName).and("description").regex(description, "i"));
        return mongoTemplate.find(query, Course.class);
    }
}