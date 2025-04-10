package com.example.todolist.XmlUtils;

import com.example.todolist.Entity.Roles;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class RoleAdapter extends XmlAdapter<String, Roles> {
    @Override
    public Roles unmarshal(String v) throws Exception {
        Roles role = new Roles();
        role.setName(v);
        return role;
    }

    @Override
    public String marshal(Roles v) throws Exception {
        return v.getName();
    }
}
