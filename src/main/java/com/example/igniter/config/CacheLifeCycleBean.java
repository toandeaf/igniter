package com.example.igniter.config;

import com.example.igniter.model.Student;
import com.example.igniter.repository.StudentDao;
import com.example.igniter.util.TaskRunner;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.hibernate.HibernateCacheProxy;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.cache.integration.CacheLoader;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CacheLifeCycleBean implements LifecycleBean, ApplicationListener<ApplicationReadyEvent>
{
    Logger logger = LoggerFactory.getLogger(CacheLifeCycleBean.class);

    @IgniteInstanceResource
    Ignite ignite;

    @Autowired
    StudentDao studentDao;

    @CacheStoreSessionResource
    CacheStoreSession session;

//    @Autowired
//    Ignite springIgnite;


    @Override
    public void onLifecycleEvent(LifecycleEventType lifecycleEventType) throws IgniteException
    {
        if(lifecycleEventType == LifecycleEventType.BEFORE_NODE_START)
        {
            logger.info("Ignite instance {} exists.", ignite.name());
        }
        else if (lifecycleEventType == LifecycleEventType.AFTER_NODE_START)
        {
            // TODO implement cacheLoader functionality
            //ignite.compute(ignite.cluster().forServers()).broadcast(new TaskRunner());
            logger.info("Loading cache data.");
        }
        else if (lifecycleEventType == LifecycleEventType.BEFORE_NODE_STOP)
        {
            logger.info("Terminating Ignite instance {}", ignite.name());

            ignite.close();
        }
        else if (lifecycleEventType == LifecycleEventType.AFTER_NODE_STOP)
        {
            logger.info("Node stopped.");
        }
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        loadCache();
    }

    public void loadCache()
    {

    }
}
