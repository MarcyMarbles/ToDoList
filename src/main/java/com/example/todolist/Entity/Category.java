package com.example.todolist.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category extends MappedSuperClass{
    private String name;

    @ManyToOne
    private Person userId;
}
