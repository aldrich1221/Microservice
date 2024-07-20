package com.spring.microservice.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("course")
public class Course {
    @Id
    private String courseId;

    private String courseName;

    private String courseDescription;

    private String courseType;

    public String getId() {
        return courseId;
    }

    public void setId(String id) {
        this.courseId = id;
    }

    public String getName() {
        return courseName;
    }

    public void setName(String name) {
        this.courseName = name;
    }

    public String getDescription() {
        return courseDescription;
    }

    public void setDescription(String name) {
        this.courseDescription = name;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

}
