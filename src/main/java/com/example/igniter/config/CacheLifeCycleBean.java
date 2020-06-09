package com.example.igniter.config;

import com.example.igniter.model.Class;
import com.example.igniter.model.Student;
import com.example.igniter.repository.StudentDao;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class CacheLifeCycleBean implements LifecycleBean, ApplicationListener<ApplicationReadyEvent>
{
    Logger logger = LoggerFactory.getLogger(CacheLifeCycleBean.class);

    @IgniteInstanceResource
    Ignite ignite;

    @Autowired
    Ignite springIgnite;

    @Autowired
    StudentDao studentDao;

    @Override
    public void onLifecycleEvent(LifecycleEventType lifecycleEventType) throws IgniteException
    {
        if(lifecycleEventType == LifecycleEventType.BEFORE_NODE_START)
        {
            logger.info("Ignite instance {} exists.", ignite.name());
        }
        else if (lifecycleEventType == LifecycleEventType.AFTER_NODE_START)
        {
            logger.info("Loading cache data.");
        }
        else if (lifecycleEventType == LifecycleEventType.BEFORE_NODE_STOP)
        {
            logger.info("Terminating Ignite instance {}", ignite.name());
            ignite.cache("short-cache").clear();
        }
        else if (lifecycleEventType == LifecycleEventType.AFTER_NODE_STOP)
        {
            logger.info("Node stopped.");
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent)
    {
        loadCache();
    }

    public void loadCache()
    {
        logger.info("Pre-loading cache with data, current cache size {}.", springIgnite.cache("short-cache").size());

        List<Student> students = studentDao.findAll();
        long startTime = System.currentTimeMillis();
        for(Student student : students)
        {
            springIgnite.cache("short-cache").put(student.getId(), student);
            for(Class classChoice : student.getClassList())
            {
                springIgnite.cache("short-cache").put(classChoice.getId(), classChoice);
            }
        }
        long endTime = System.currentTimeMillis();
        logger.info("Time taken to load cache {}ms, current cache size {}.", (endTime - startTime), springIgnite.cache("short-cache").size());
    }
}
