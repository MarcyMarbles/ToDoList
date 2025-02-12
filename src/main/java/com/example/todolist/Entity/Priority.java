package com.example.todolist.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Priority extends MappedSuperClass {
    private String name;

    @ManyToOne
    private Person userId;

    private String color = "#00000";
}
