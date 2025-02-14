package com.example.todolist.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends MappedSuperClass {
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String username;

    @ManyToMany(mappedBy = "users")
    List<Person> person = new ArrayList<>();

    @Transient
    private Person currentPerson;

    public Person getCurrentPerson() {
        if(person.isEmpty()) {
            return null;
        }
        return person.get(0);
    }
}