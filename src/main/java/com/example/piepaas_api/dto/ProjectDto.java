package com.example.piepaas_api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProjectDto implements Serializable{

    private String name;
    private String subdomain;
    private String namespace;
    private String dbName;
    private String dbUsername;
    private String dbPassword;
    private Long idClient;
}
