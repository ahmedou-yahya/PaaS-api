package com.example.piepaas_api;

import com.example.piepaas_api.entity.Client;
import com.example.piepaas_api.security.entity.Role;
import com.example.piepaas_api.security.repository.RoleRepository;
import com.example.piepaas_api.security.entity.User;
import com.example.piepaas_api.security.repository.UserRepository;
import com.example.piepaas_api.service.GerritApiService;
import com.example.piepaas_api.service.JenkinsService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
public class PiepaasApiApplication {

    public static void main(String[] args)  {
        SpringApplication.run(PiepaasApiApplication.class, args);
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofGigabytes(1));
        factory.setMaxRequestSize(DataSize.ofGigabytes(1));
        return factory.createMultipartConfig();
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/files")
                        .allowedOrigins("http://localhost:4200");
            }
        };
    }

    @Bean
    public CommandLineRunner test(UserRepository userRepository,
                                  GerritApiService gerritApiService,
                                  RoleRepository roleRepository,
                                  PasswordEncoder passwordEncoder , JenkinsService jenkinsService) throws Exception {
//        jenkinsService.triggerJob("example");
//                    Users and roles
        return args -> {
            if (userRepository.findByUsername("admin") == null) {
            User admin = new User("admin", passwordEncoder.encode("admin"), true);
            User user = new User("user", passwordEncoder.encode("1234"), true);

            Role role1 = new Role("ROLE_ADMIN");
            Role role2 = new Role("ROLE_USER");

            List<Role> adminRoles = new ArrayList<>();
            List<Role> userRoles = new ArrayList<>();

            adminRoles.add(role1);
            adminRoles.add(role2);
            admin.setRoles(adminRoles);

            userRoles.add(role2);
            user.setRoles(userRoles);

            Client client =new Client();
            client.setName("Google(GCP)");
            user.setClient(client);

            client.setUser(user);

              roleRepository.save(role1);
              roleRepository.save(role2);

              userRepository.save(user);
              userRepository.save(admin);
            }
        };
    }

}

