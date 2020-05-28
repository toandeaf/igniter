package com.example.igniter.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("test_data")
@Data
public class TestObject {

    @Id
    private String id;
    private String name;
    private String description;
}
