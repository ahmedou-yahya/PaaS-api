package com.example.piepaas_api.security.mapper;

import com.example.piepaas_api.security.dto.UserDto;
import com.example.piepaas_api.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Data
public class UserMapper {

    public UserDto userToDto (User user){

        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .idClient(user.getClient()==null?null:user.getClient().getId())
                .roles(user.getRoles())
                .build();

    }
}
