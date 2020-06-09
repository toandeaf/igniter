package com.example.igniter.model;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "student")
@Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "short-cache")
public class Student {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String school;

    @ManyToMany
    @JoinTable(
            name = "class_choices",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "class_id"))
    @Cache(usage = CacheConcurrencyStrategy.TRANSACTIONAL, region = "short-cache")
    private List<Class> classList;

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

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<Class> getClassList() {
        return classList;
    }

    public void setClassList(List<Class> classList) {
        this.classList = classList;
    }
}
