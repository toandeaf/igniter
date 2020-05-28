package com.example.igniter.config;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CacheLifeCycleBean implements LifecycleBean
{
    Logger logger = LoggerFactory.getLogger(CacheLifeCycleBean.class);

    @IgniteInstanceResource
    Ignite ignite;

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
            logger.info("Loading cache data.");
        }
        else if (lifecycleEventType == LifecycleEventType.BEFORE_NODE_STOP)
        {
            logger.info("Terminating Ignite instance {}", ignite.name());
            ignite.destroyCache("testCache");

            ignite.close();
        }
        else if (lifecycleEventType == LifecycleEventType.AFTER_NODE_STOP)
        {
            logger.info("Node stopped.");
        }
    }
}
