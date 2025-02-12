package com.example.todolist.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Tasks extends MappedSuperClass{
    private String name;

    @ManyToOne
    private Category category;
    @ManyToOne
    private Priority priority;
    @ManyToOne
    private Users user;

    @ManyToMany(mappedBy = "tasks")
    List<Users> assignees = new ArrayList<>();

}
