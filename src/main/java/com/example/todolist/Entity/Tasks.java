package com.example.todolist.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Tasks extends MappedSuperClass{
    private String name;

    @ManyToOne
    private Category category;
    @ManyToOne
    private Priority priority;
    @ManyToOne
    private Person user;

    @ManyToMany(mappedBy = "tasks")
    List<Person> assignees = new ArrayList<>();

}
