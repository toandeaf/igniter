package com.example.igniter.repository;

import com.example.igniter.model.TestObject;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDao extends MongoRepository<TestObject, String> {
}
