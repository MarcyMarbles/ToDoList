package com.example.todolist.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Category extends MappedSuperClass{
    private String name;

    @ManyToOne
    private Users userId;
}
