package com.example.igniter.config;


import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.configuration.TransactionConfiguration;
import org.apache.ignite.spi.discovery.DiscoverySpi;
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

    public IgniteConfiguration igniteConfiguration()
    {
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("HibernateL2CacheGrid");
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setClientMode(true);
        igniteConfiguration.setMetricsLogFrequency(0);

        igniteConfiguration.setDiscoverySpi(discoverySpi());

        igniteConfiguration.setLifecycleBeans(new CacheLifeCycleBean());

        igniteConfiguration.setTransactionConfiguration(transactionConfiguration());

        igniteConfiguration.setCacheConfiguration(shortCacheConfiguration());

        return igniteConfiguration;
    }

    private CacheConfiguration shortCacheConfiguration()
    {
        CacheConfiguration cacheConfigClass = new CacheConfiguration();
        cacheConfigClass.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheConfigClass.setCacheMode(CacheMode.REPLICATED);
        cacheConfigClass.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_ASYNC);
        cacheConfigClass.setName("short-cache");
        cacheConfigClass.setDataRegionName("short-cache-region");
        return cacheConfigClass;
    }

    private TransactionConfiguration transactionConfiguration()
    {
        TransactionConfiguration transConfig = new TransactionConfiguration();
        transConfig.setTxManagerFactory(FactoryBuilder.factoryOf(SpringTransactionManager.class));
        return transConfig;
    }

    private DiscoverySpi discoverySpi()
    {
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        return new TcpDiscoverySpi().setIpFinder(ipFinder);
    }
}
