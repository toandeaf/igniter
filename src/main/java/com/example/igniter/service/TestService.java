package com.example.igniter.service;

import com.example.igniter.model.TestObject;
import com.example.igniter.repository.TestDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TestService {

    @Autowired
    TestDao testDao;

    Logger logger = LoggerFactory.getLogger(TestService.class);


    public void addTestData()
    {
        TestObject test = new TestObject();
        test.setName("Catherine");
        test.setDescription("She's a wee woman");
        testDao.insert(test);
    }

    @Cacheable(value = "testCache", key="#id")
    public TestObject getObjectById(String id)
    {
        try {
            Thread.sleep(5000);
            logger.info("Getting from DB");
        } catch (Exception e){
            logger.error("Issue getting from DB.");
        }

        return testDao.findById(id).get();
    }

    @Cacheable(value = "testCache", key="#id")
    public List<TestObject> returnAll()
    {
        return testDao.findAll();
    }

    @EventListener(ApplicationReadyEvent.class)
    @CachePut(value = "testCache")
    public List<TestObject> initialiseData()
    {
        logger.info("Initialising data.");
        return returnAll();
    }
}
