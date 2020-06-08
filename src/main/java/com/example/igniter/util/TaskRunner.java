package com.example.igniter.util;

import org.apache.ignite.DataRegionMetrics;
import org.apache.ignite.Ignite;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

public class TaskRunner implements IgniteRunnable
{
    @IgniteInstanceResource
    Ignite ignite;

    public void run()
    {
        System.out.println(">> Executing the compute task");
        for (String cacheName : ignite.cacheNames())
        {
            System.out.println("Cache name : " + cacheName);
            System.out.println("Cache size is " + ignite.cache(cacheName).size());
        }

        if (ignite.dataRegionMetrics() != null && ignite.dataRegionMetrics().size() > 0)
        {
            for (DataRegionMetrics metrics : ignite.dataRegionMetrics())
            {
                System.out.println(metrics.getName());
                System.out.println(metrics.getTotalUsedPages());
            }
        }
    }
}