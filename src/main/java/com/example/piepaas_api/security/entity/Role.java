package com.example.piepaas_api.security.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Data
@Table(name = "role")
public class Role implements Serializable
{

    private static final long serialVersionUID = 6183305164933054119L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    private Collection<User> users;



    public Role()
    {
    }

    public Role(String name)
    {
        this.name = name;
    }

    public Role(String name, Collection<User> users)
    {
        this.name = name;
        this.users = users;
    }

    @Override
    public String toString()
    {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}