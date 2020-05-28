package com.example.igniter.config;


import com.example.igniter.model.TestObject;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;


@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager()
    {
        SpringCacheManager cacheManager = new SpringCacheManager();
        cacheManager.setConfiguration(igniteConfiguration());
        return cacheManager;
    }

    @Bean
    public IgniteConfiguration igniteConfiguration()
    {
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("cacheRunner");
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setClientMode(true);
        igniteConfiguration.setMetricsLogFrequency(0);
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));
        igniteConfiguration.setCacheConfiguration(cacheConfiguration());

        LifecycleBean lifecycleBean = new CacheLifeCycleBean();
        igniteConfiguration.setLifecycleBeans(lifecycleBean);
        return igniteConfiguration;
    }

    @Bean
    public CacheConfiguration cacheConfiguration()
    {
        CacheConfiguration<String, TestObject> cacheConfiguration = new CacheConfiguration();

        cacheConfiguration.setAtomicityMode(CacheAtomicityMode.ATOMIC);
        cacheConfiguration.setCacheMode(CacheMode.REPLICATED);
        cacheConfiguration.setName("testCache");
        cacheConfiguration.setWriteThrough(false);
        cacheConfiguration.setReadThrough(false);
        cacheConfiguration.setWriteBehindEnabled(false);
        cacheConfiguration.setBackups(1);
        cacheConfiguration.setStatisticsEnabled(true);

        return cacheConfiguration;
    }
}
