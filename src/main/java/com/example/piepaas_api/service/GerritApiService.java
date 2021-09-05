package com.example.piepaas_api.service;

import org.json.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class GerritApiService {
    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    RestTemplate restTemplate = new RestTemplate();
    String url= "http://gerrit.piepaas.me";
    String username="admin";
    String password="myL/ia0tawfadRP43mJtRqTG7VS5aDsQ7X/griHlQQ";

    public ResponseEntity<String> getVersion() {
        HttpHeaders headers= createHeaders(username,password);
        return restTemplate.exchange(url+"/a/config/server/version",HttpMethod.GET,new HttpEntity<>("body",headers),String.class);
    }

    public ResponseEntity<String> createRepo(String name)  {
        HttpHeaders headers= createHeaders(username,password);
        String body="{\"description\": \"This is a demo project.\",\"submit_type\": \"INHERIT\",\"owners\": []}";

        HttpEntity httpEntity=new HttpEntity<>(body,headers);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return restTemplate.exchange(url+"/a/projects/"+name, HttpMethod.PUT,new HttpEntity<>(body,headers),String.class);

    }
    public ResponseEntity<String> getAllReposCall()  {
        HttpHeaders headers= createHeaders(username,password);
        HttpEntity httpEntity=new HttpEntity<>(headers);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return restTemplate.exchange(url+"/a/projects/?d", HttpMethod.GET,new HttpEntity<>(headers),String.class);

    }
    public Set<String> findAllRepos(){
        String jsonString = getAllReposCall().getBody().substring(5); //assign your JSON String here
        JSONObject obj = new JSONObject(jsonString);
        Set<String> repos=obj.keySet();
        repos.remove("All-Projects");
        repos.remove("All-Users");
        return repos;


    }
    public void deleteRepoByName(String name)  {
        HttpHeaders headers=new HttpHeaders();
        headers.set("Authorization","Basic YWRtaW46bXlML2lhMHRhd2ZhZFJQNDNtSnRScVRHN1ZTNWFEc1E3WC9ncmlIbFFR");
        HttpEntity httpEntity=new HttpEntity<>(headers);
        String body="{\"description\": \"This is a demo project.\",\"submit_type\": \"INHERIT\",\"owners\": []}";

        restTemplate.delete(url+"/a/projects/"+name, HttpMethod.DELETE,new HttpEntity<>(body,headers));

    }
    public ResponseEntity<String> getReposByName(String name)  {
        HttpHeaders headers= createHeaders(username,password);
        HttpEntity httpEntity=new HttpEntity<>(headers);
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return restTemplate.exchange(url+"/a/projects/"+name, HttpMethod.GET,new HttpEntity<>(headers),String.class);

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
}
