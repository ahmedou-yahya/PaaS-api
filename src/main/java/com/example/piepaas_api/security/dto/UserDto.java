package com.example.piepaas_api.security.dto;


import com.example.piepaas_api.security.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements Serializable
{
    private static final long serialVersionUID = -7294904497247619478L;

    private Long id;
    private String username;
    private Long idClient;
    private String token;
    private Collection<Role> roles;}
//    private UserProfile userProfile;
