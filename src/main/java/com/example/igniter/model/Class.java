package com.example.igniter.model;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="class")
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.TRANSACTIONAL, region = "short-cache")
public class Class {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private int difficulty;

    @ManyToMany
    @JoinTable(
            name = "class_choices",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "short-cache")
    private List<Student> students;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }
}
