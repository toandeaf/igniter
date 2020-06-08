package com.example.igniter.service;

import com.example.igniter.model.Student;
import com.example.igniter.repository.StudentDao;
import com.example.igniter.util.TaskRunner;
import org.apache.ignite.Ignite;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    StudentDao studentDao;

    @Autowired
    Ignite ignite;

    public Student getStudentById(String id)
    {
        ignite.compute(ignite.cluster().forServers()).broadcast(new TaskRunner());
        logger.info("We aint cacheing!");
        return studentDao.findById(id).get();
    }

    public List<Student> returnAllStudents()
    {
        ignite.compute(ignite.cluster().forServers()).broadcast(new TaskRunner());
        logger.info("Loading all.");
        return studentDao.findAll();
    }
}
