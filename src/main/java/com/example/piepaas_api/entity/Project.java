package com.example.piepaas_api.entity;


import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String subdomain;
    private String namespace;
    private String dbName;
    private String dbUsername;
    private String dbPassword;

    @ManyToOne()
    private Client client;


}
