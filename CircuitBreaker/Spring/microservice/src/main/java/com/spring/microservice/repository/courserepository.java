
package com.spring.microservice.repository;

import com.spring.microservice.model.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface courserepository {
    String updateCourse(String courseId,String courseName, String courseDescription);

}
