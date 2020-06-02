package com.example.igniter.config;


import com.example.igniter.model.TestObject;
import org.apache.ignite.*;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.cache.CacheMode;
import org.apache.ignite.cache.CacheWriteSynchronizationMode;
import org.apache.ignite.cache.spring.SpringCacheManager;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.internal.IgnitionMXBeanAdapter;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cache.spi.RegionFactory;
import org.hibernate.cfg.Environment;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.*;


@Configuration
//@EnableCaching
public class CacheConfig {

//    @Bean
//    public SessionFactory sessionFactory(){
//        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
//
//        // Hibernate settings equivalent to hibernate.cfg.xml's properties
//        Map<String, String> settings = new HashMap<>();
//        settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
//        settings.put(Environment.URL, "jdbc:mysql://localhost:3306/cache_test?serverTimezone=UTC");
//        settings.put(Environment.USER, "toandeaf");
//        settings.put(Environment.PASS, "Boysmiles12");
//        settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
//        settings.put(Environment.USE_SECOND_LEVEL_CACHE, "true");
//        settings.put(Environment.CACHE_REGION_FACTORY, "org.apache.ignite.org.apache.ignite.cache.hibernate.HibernateRegionFactory");
//        settings.put(Environment.GENERATE_STATISTICS, "true");
//
//
//        // Apply settings
//        registryBuilder.applySettings(settings);
//
//
//        // Create registry
//        StandardServiceRegistry registry = registryBuilder.build();
//        // Create MetadataSources
//        MetadataSources sources = new MetadataSources(registry);
//        // Create Metadata
//        Metadata metadata = sources.getMetadataBuilder().build();
//
//        // Create SessionFactory
//        return metadata.getSessionFactoryBuilder().build();
//    }

    @Bean
    public Ignite ignite() throws IgniteCheckedException {
        return Ignition.start(igniteConfiguration());
    }

    @Bean
    public IgniteConfiguration igniteConfiguration()
    {
        TcpDiscoveryMulticastIpFinder ipFinder = new TcpDiscoveryMulticastIpFinder();
        ipFinder.setAddresses(Collections.singletonList("127.0.0.1:47500..47509"));
        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();
        igniteConfiguration.setIgniteInstanceName("HibernateL2CacheGrid");
        igniteConfiguration.setPeerClassLoadingEnabled(true);
        igniteConfiguration.setClientMode(true);
        igniteConfiguration.setMetricsLogFrequency(0);
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(ipFinder));

        CacheConfiguration cacheConfigStudent = new CacheConfiguration();

        cacheConfigStudent.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheConfigStudent.setCacheMode(CacheMode.REPLICATED);
        cacheConfigStudent.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        cacheConfigStudent.setName("student");

        CacheConfiguration cacheConfigClass = new CacheConfiguration();

        cacheConfigClass.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        cacheConfigClass.setCacheMode(CacheMode.REPLICATED);
        cacheConfigClass.setWriteSynchronizationMode(CacheWriteSynchronizationMode.FULL_SYNC);
        cacheConfigClass.setName("class");

        igniteConfiguration.setCacheConfiguration(cacheConfigStudent, cacheConfigClass);

        LifecycleBean lifecycleBean = new CacheLifeCycleBean();
        igniteConfiguration.setLifecycleBeans(lifecycleBean);
        return igniteConfiguration;
    }
}
