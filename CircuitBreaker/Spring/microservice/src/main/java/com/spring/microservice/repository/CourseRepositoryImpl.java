package com.spring.microservice.repository;

import com.mongodb.client.result.UpdateResult;
import com.spring.microservice.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class CourseRepositoryImpl implements courserepository{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public String updateCourse(String courseId, String courseName, String description) {
        Query query = new Query(Criteria.where("courseId").is(courseId));

        Update update = new Update();
        update.set("courseName", courseName);
        update.set("courseDescription", description);

        UpdateResult result = mongoTemplate.updateFirst(query, update, Course.class);

        if(result == null)
            System.out.println("No documents updated");
        else
            System.out.println(result.getModifiedCount() + " document(s) updated..");

        return "success";
    }



}
