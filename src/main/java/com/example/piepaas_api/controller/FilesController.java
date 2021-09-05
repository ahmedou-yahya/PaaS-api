package com.example.piepaas_api.controller;
import com.example.piepaas_api.model.UploadResponseMessage;
import com.example.piepaas_api.service.GerritService;
import com.example.piepaas_api.service.JenkinsService;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("files")
public class FilesController {

    @Autowired
    private  GerritService gerritService;
    @Autowired
    private  JenkinsService jenkinsService;

    @PostMapping("/{reponame}")
    public ResponseEntity<UploadResponseMessage> uploadFile(@RequestParam("files") List<MultipartFile> files, @PathVariable final String reponame) throws GitAPIException, IOException {

        String fileNames="";
        for(MultipartFile file:files){
            fileNames=fileNames+","+file.getOriginalFilename();
            gerritService.save(file, reponame);
            gerritService.pushToRepo(reponame);
        }
        jenkinsService.triggerJob(reponame);

        return ResponseEntity.status(HttpStatus.OK)
                             .body(new UploadResponseMessage("Uploaded the files successfully: " + fileNames));
    }
}
