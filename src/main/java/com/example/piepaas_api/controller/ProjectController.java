package com.example.piepaas_api.controller;

import com.example.piepaas_api.entity.Project;
import com.example.piepaas_api.service.ProjectService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
@RestController
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping("/all")
    ResponseEntity<List<Project>> findAll(){
        return new ResponseEntity<>(projectService.findAllProjects(), HttpStatus.OK);
    }
    @PostMapping("/project/save")
    ResponseEntity<Project> newProject(@RequestBody Project project){
        return new ResponseEntity<>(projectService.saveProject(project), HttpStatus.OK);
    }
    @GetMapping("project/{id}")
    ResponseEntity<Project> findById(@PathVariable Long id){
        return new ResponseEntity<>(projectService.findProjectById(id), HttpStatus.OK);
    }
    @PutMapping("project/{id}")
    ResponseEntity<Project> updateProject(@RequestBody Project newProject,@PathVariable Long id){
        return new ResponseEntity<>(projectService.updateProject(newProject,id), HttpStatus.OK);
    }
    @DeleteMapping("project/{id}")
    ResponseEntity deleteById(@PathVariable Long id){
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("project/byclient/{id}")
    ResponseEntity<Project> getProjectByClientId(@PathVariable Long id){
        return ResponseEntity.ok(projectService.getProjectByClientId(id));
    }


}


