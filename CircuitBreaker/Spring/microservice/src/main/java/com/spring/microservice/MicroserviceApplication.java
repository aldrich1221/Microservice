package com.spring.microservice;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.spring.microservice.model.Course;
import com.spring.microservice.repository.courserepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
//import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
@EnableMongoRepositories
@RestController
public class MicroserviceApplication {
    @Autowired
    courserepository courserepo;

public static void main(String[] args) {

    SpringApplication.run(MicroserviceApplication.class, args);
}


    private RestTemplate restTemplate;

    @GetMapping("/hello")
    @CircuitBreaker(name = "myService", fallbackMethod = "sayHelloFallback")
    public String sayHello(@RequestParam(value = "myName", defaultValue = "World") String name) {
        System.out.println("name"+name);
        String ans=courserepo.updateCourse("111","aaa",name);
        return String.format(ans);
    }
    public String sayHelloFallback(String name){
        return String.format("Sorry, %s . The services don't work now.", name);
    }

    //    @GetMapping("findCourseByStudent")
//    public ResponseEntity<String> findAllCourse(@PathVariable String id){
//        return this.
//
//    }
    @PostMapping(
            value = "/createcourse", consumes = "application/json", produces = "application/json")
    public void createCourse(@RequestBody Course courseDetails) {
        System.out.println(courseDetails.getId() + "_" + courseDetails.getName());
    }




}
