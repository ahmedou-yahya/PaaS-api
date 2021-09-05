package com.example.piepaas_api.mapper;

import com.example.piepaas_api.dto.ProjectDto;
import com.example.piepaas_api.entity.Project;
import com.example.piepaas_api.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class ProjectMapper {

    private final ClientRepository clientRepository;
    public Project dtoToProject(ProjectDto projectDto) {
        return Objects.isNull(projectDto) ? null :
                Project
                        .builder()
                        .name(projectDto.getName())
                        .subdomain(projectDto.getSubdomain())
                        .namespace(projectDto.getNamespace())
                        .dbName(projectDto.getDbName())
                        .dbUsername(projectDto.getDbUsername())
                        .dbPassword(projectDto.getDbPassword())
                        .client(projectDto.getIdClient()==null?null: clientRepository.findById(projectDto.getIdClient()).orElse(null))
                        .build();
    }

    public ProjectDto ProjectToDto(Project project) {
        return Objects.isNull(project) ? null :
                ProjectDto.builder()
                        .name(project.getName())
                        .subdomain(project.getSubdomain())
                        .namespace(project.getNamespace())
                        .dbName(project.getDbName())
                        .dbUsername(project.getDbUsername())
                        .dbPassword(project.getDbPassword())
                        .idClient(project.getClient().getId())
                        .build();
    }
}
