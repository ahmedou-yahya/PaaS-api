package com.example.piepaas_api.service;

import com.example.piepaas_api.entity.Project;
import com.example.piepaas_api.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@AllArgsConstructor
@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> findAllProjects(){
        return projectRepository.findAll();
    }
    public Project findProjectById(Long id){

        return projectRepository.findById(id).orElseGet(null);
    }

    public Project saveProject(Project project){

        return projectRepository.save(project);
    }
    public Project updateProject(Project newProject, Long id){
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(newProject.getName());
                    project.setClient(newProject.getClient());
                    return projectRepository.save(project);
                })
                .orElseGet(() -> {
                    newProject.setId(id);
                    return projectRepository.save(newProject);
                });
    }
    public void deleteProjectById(Long id){
      projectRepository.deleteById(id);
    }

    public Project getProjectByClientId(Long clientId){
        List<Project> projects = findAllProjects();
        for(Project project: projects){
            if(project.getClient().getId()==clientId){
                return project;
            }
        }
        return null;
    }
}


