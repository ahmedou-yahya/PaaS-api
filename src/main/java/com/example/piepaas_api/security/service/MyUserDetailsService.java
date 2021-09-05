package com.example.piepaas_api.security.service;

import com.example.piepaas_api.security.entity.Role;
import com.example.piepaas_api.security.entity.User;
import com.example.piepaas_api.security.repository.UserRepository;
import com.example.piepaas_api.security.model.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
      User user= userRepository.findByUsername(userName);
       if(user==null)
           throw new UsernameNotFoundException("Nom d'utilisateur introuvable");
        return new MyUserDetails(user,getGrantedAuthorities(user.getRoles()));

    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<Role> roles)
    {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles)
        {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }

}