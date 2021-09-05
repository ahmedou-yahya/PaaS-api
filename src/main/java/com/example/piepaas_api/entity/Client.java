package com.example.piepaas_api.entity;


import com.example.piepaas_api.security.entity.User;
import lombok.Data;
import org.springframework.context.annotation.Role;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "client")
    private Set<Project> projects;

}
