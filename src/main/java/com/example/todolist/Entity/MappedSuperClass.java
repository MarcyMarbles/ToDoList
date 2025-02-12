package com.example.todolist.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class MappedSuperClass {
    @Id
    @GeneratedValue
    private int id;

    private OffsetDateTime created_ts;
    private OffsetDateTime updated_ts;
    private OffsetDateTime deleted_ts;
}
