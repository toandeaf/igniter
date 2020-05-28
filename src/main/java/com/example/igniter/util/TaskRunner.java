package com.example.igniter.util;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.lang.IgniteRunnable;
import org.apache.ignite.resources.IgniteInstanceResource;

public class TaskRunner implements IgniteRunnable
{
    @IgniteInstanceResource
    Ignite ignite;

    public void run() {
        System.out.println(">> Executing the compute task");

        System.out.println(
                "   Node ID: " + ignite.cluster().localNode().id() + "\n" +
                        "   OS: " + System.getProperty("os.name") +
                        "   JRE: " + System.getProperty("java.runtime.name"));

        IgniteCache<Integer, String> cache = ignite.cache("main");

        System.out.println(">> " + cache.get(1) + " " + cache.get(2));
    }
}