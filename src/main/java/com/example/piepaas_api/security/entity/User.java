package com.example.piepaas_api.security.entity;

import com.example.piepaas_api.entity.Client;
import com.example.piepaas_api.security.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;


@EqualsAndHashCode()
@Entity
@Data
@Table(name = "`user`")
public class User  implements Serializable
{

    private static final long serialVersionUID = -1742305710555210987L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "username",unique = true)
    protected String username;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "password", nullable = false)
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    @JsonManagedReference
    private Collection<Role> roles;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private Client client;

    public User()
    {
    }

    public User(String username, String password, Boolean active)
    {
        this.username = username;
        this.password = password;
        this.active = active;
    }

    @Override
    public String toString()
    {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", active=" + active +
                ", password='" + password + '\'' +
                '}';
    }
}
