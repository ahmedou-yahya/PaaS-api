package com.example.piepaas_api.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Base64;

@Service
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JenkinsService {

//    @Autowired
//    RestTemplateBuilder restTemplateBuilder;

    RestTemplate restTemplate = new RestTemplate();
    String url= "http://212.47.251.129:8080/job/job/buildWithParameters?APPNAME=";
    String username="abdellahi";
    String password="11076ae8b5c6b206ac84af1aa53af546b8";

    public void triggerJob(String appName) {

        HttpHeaders headers = createHeaders(username, password);

        restTemplate.exchange(url+appName, HttpMethod.POST,new HttpEntity<>(headers),String.class);
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

