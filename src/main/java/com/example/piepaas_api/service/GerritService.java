package com.example.piepaas_api.service;

import com.example.piepaas_api.dto.ProjectDto;
import com.example.piepaas_api.entity.Project;
import com.example.piepaas_api.exceptions.FileUploadException;
import com.example.piepaas_api.mapper.ProjectMapper;
import com.example.piepaas_api.repository.ProjectRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GerritService {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;
    @Autowired
    ProjectMapper projectMapper;
    RestTemplate restTemplate = new RestTemplate();
    String url= "http://gerrit.piepaas.me";
    String username="admin";
    String password="myL/ia0tawfadRP43mJtRqTG7VS5aDsQ7X/griHlQQ";
    
    private final Path location = Paths.get("upload");

    @Autowired
    private ProjectRepository projectRepository;

    public Project createNewProject(ProjectDto projectDto) throws InvalidRemoteException, TransportException, GitAPIException, IOException{
        Project project = projectMapper.dtoToProject(projectDto);
        createRepo(project.getName());
        cloneRepo(project.getName());
        getDbDetails(project);
        getProjectDetails(project);
        pushToRepo(project.getName());
        return projectRepository.save(project);
    }

    public ResponseEntity<String> createRepo(String name)  {
        HttpHeaders headers= createHeaders(username,password);
        String body="{\"description\": \"This is a demo project.\",\"submit_type\": \"INHERIT\",\"owners\": []}";

        HttpEntity httpEntity=new HttpEntity<>(body,headers);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return restTemplate.exchange(url+"/a/projects/"+name,HttpMethod.PUT,new HttpEntity<>(body,headers),String.class);

    }

    public void cloneRepo(String reponame) throws GitAPIException, IOException {
        Path pathToFile = Paths.get(location.toString()+"/"+reponame);
        Files.createDirectories(pathToFile);
        Git.cloneRepository()
                .setURI("http://gerrit.piepaas.me/" + reponame)
                .setDirectory(new File(pathToFile.toString()))
                .call();
    }


        HttpHeaders createHeaders(String username, String password){
        return new HttpHeaders() {{
            String auth = username + ":" + password;
            byte[] encodedAuth = Base64.getEncoder().encode(
                    auth.getBytes(Charset.forName("US-ASCII")));
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }

    public Project getDbDetails(Project project) throws IOException {

        Path pathToFile = Paths.get(location.toString()+"/"+project.getName()+"/"+"dbDetails.txt");
        Files.createDirectories(pathToFile.getParent());
        Files.createFile(pathToFile);

        BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile.toString()));
        try {
            bw.write(project.getDbName());
            bw.newLine();
            bw.write(project.getDbUsername());
            bw.newLine();
            bw.write(project.getDbPassword());
            bw.close();
        } catch (Exception e) {

        }
        return project;
    }

    public void pushToRepo(String reponame) throws IOException, GitAPIException {
                Git.open(new File(location.toString() + "/" + reponame))
                .add().addFilepattern(".").call();

        Git.open(new File(location.toString()+ "/" + reponame))
                .commit().setMessage("commit test").call();
        Git.open(new File(location.toString()+ "/" + reponame))
                .push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(username,password))
                .call();
    }

    public void save(MultipartFile file, String reponame) throws FileUploadException, GitAPIException, IOException {
        try {
            Path root = Paths.get("upload"+"/"+ reponame);
            if(!Files.exists(root))
                Files.createDirectory(root);
            Path resolve = root.resolve(file.getOriginalFilename());
            if (resolve.toFile()
                    .exists()) {
                throw new FileUploadException("File already exists: " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), resolve);

        } catch (Exception e) {
            throw new FileUploadException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Project getProjectDetails(Project project) throws IOException {
        Path pathToFile = Paths.get(location.toString()+"/"+project.getName()+"/"+"projectDetails.txt");
        Files.createDirectories(pathToFile.getParent());
        Files.createFile(pathToFile);

        BufferedWriter bw = new BufferedWriter(new FileWriter(pathToFile.toString()));
        try {
            bw.write(project.getSubdomain());
            bw.newLine();
            bw.write(project.getNamespace());
            bw.close();
        } catch (Exception e) {

        }

        return project;
    }
}
