package com.example.igniter.util;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

import java.util.List;

public class TaskRunner implements IgniteRunnable {

    @IgniteInstanceResource
    Ignite ignite;

    public void run() {
        System.out.println(">> Executing the compute task");
        for(String cacheName: ignite.cacheNames()){
            System.out.println("Cache name : " + cacheName);
            System.out.println("Cache size is " + ignite.cache(cacheName).size());
        }

    }
}