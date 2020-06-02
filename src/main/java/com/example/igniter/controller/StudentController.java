package com.example.igniter.controller;

import com.example.igniter.model.Student;
import com.example.igniter.repository.StudentDao;
import com.example.igniter.service.StudentService;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    StudentService studentService;

    @RequestMapping("/returnAll")
    @ResponseBody
    public List<Student> returnAllStudents(){
        return studentService.returnAllStudents();
    }

    @RequestMapping("/getById/{id}")
    @ResponseBody
    public Student getStudentById(@PathVariable("id") String id)
    {
        return studentService.getStudentById(id);
    }


}
