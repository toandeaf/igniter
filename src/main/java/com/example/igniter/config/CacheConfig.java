package com.example.igniter.config;


import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCheckedException;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.*;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.apache.ignite.transactions.spring.SpringTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.configuration.FactoryBuilder;
import java.util.Collections;


@Configuration
public class CacheConfig {

    @Bean
    public Ignite ignite()
    {
        return Ignition.start(igniteConfiguration());
    }

    @Bean
    public IgniteConfiguration igniteConfiguration()
    {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("HibernateL2CacheGrid");
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setClientMode(true);
        igniteConfiguration.setMetricsLogFrequency(0);

        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));

        LifecycleBean lifecycleBean = new CacheLifeCycleBean();
        igniteConfiguration.setLifecycleBeans(lifecycleBean);

        TransactionConfiguration transConfig = new TransactionConfiguration();
        transConfig.setTxManagerFactory(FactoryBuilder.factoryOf(SpringTransactionManager.class));
        igniteConfiguration.setTransactionConfiguration(transConfig);

        CacheConfiguration cacheConfigClass = new CacheConfiguration();
        cacheConfigClass.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheConfigClass.setCacheMode(CacheMode.REPLICATED);

        // TODO decide whether write through or write behind is suitable.
        // Cannot enable write-through (writer or store is not provided) for cache: class
        // cacheConfigClass.setWriteThrough(true);
        cacheConfigClass.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_ASYNC);
        cacheConfigClass.setName("short-cache");
        cacheConfigClass.setDataRegionName("short-cache-region");

        igniteConfiguration.setCacheConfiguration(cacheConfigClass);

        return igniteConfiguration;
    }
}
