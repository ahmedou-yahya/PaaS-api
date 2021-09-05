package com.example.piepaas_api.controller;

import com.example.piepaas_api.dto.ProjectDto;
import com.example.piepaas_api.entity.Project;
import com.example.piepaas_api.service.GerritApiService;
import com.example.piepaas_api.service.GerritService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Set;

@RestController
@Data
@AllArgsConstructor
@NoArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class GerritController {
    @Autowired
    private GerritService gerritService;
    @Autowired
    private GerritApiService gerritApiService;

    @PostMapping(value = "/newproject",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> createNewProject(@RequestBody ProjectDto projectDto) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
        return ResponseEntity.ok(gerritService.createNewProject(projectDto));
    }
//    @GetMapping("/v")
//    public String getVersion(){
//        return gerritService.getVersion().getBody().toString();
//    }
//    @PutMapping("/newrepo/{nom}")
//    public String createRepo(@PathVariable String nom) throws InvalidRemoteException, TransportException, GitAPIException, IOException {
//
//        return gerritService.createRepo(nom).getBody().toString();
//    }
//
//    @PostMapping("/dbdetails")
//    public ResponseEntity<DbDetails> getDbDetails(@RequestBody DbDetails dbDetails) throws IOException {
//        return ResponseEntity.ok(gerritService.getDbDetails(dbDetails));
//    }
//
//    @PostMapping("/subdomain")
//    public ResponseEntity<String> getSubdomain(@RequestBody String subdomain) throws IOException {
//        return ResponseEntity.ok(gerritService.getSubdomain(subdomain));
//    }
//
//    @GetMapping("/clonerepo")
//    public void cloneRepo(String reponame) throws GitAPIException, IOException {
//        gerritService.cloneRepo(reponame);
//    }
//
//    @GetMapping("/pushtorepo")
//    public void pushToRepo() throws GitAPIException, IOException {
//        gerritService.pushToRepo();
//    }
        @GetMapping("/repos")
    public Set<String> getAllRepos()  {

       return gerritApiService.findAllRepos();
    }
    @GetMapping("repos/{name}")
   public String findRepoByName(@PathVariable String name){
        return gerritApiService.getReposByName(name).getBody().substring(5);
    }
    @DeleteMapping("repos/delete/{name}")
    public ResponseEntity deleteRepoByName(@PathVariable String name){
        gerritApiService.deleteRepoByName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
