package com.example.igniter.controller;

import com.example.igniter.model.TestObject;
import com.example.igniter.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("/returnAll")
    @ResponseBody
    public ResponseEntity returnAll()
    {
        List<TestObject> objects = testService.returnAll();
        return new ResponseEntity(objects, null, HttpStatus.ACCEPTED);
    }

    @RequestMapping("/testCall/{id}")
    @ResponseBody
    public ResponseEntity testCall(@PathVariable("id") String id)
    {
        TestObject test = testService.getObjectById(id);
        return new ResponseEntity(test, null, HttpStatus.ACCEPTED);
    }
}
