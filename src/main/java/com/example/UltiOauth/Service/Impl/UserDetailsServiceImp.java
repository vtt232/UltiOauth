package com.example.UltiOauth.Service.Impl;

import com.example.UltiOauth.Entity.UserEntity;
import com.example.UltiOauth.Entity.UserPrincipal;
import com.example.UltiOauth.Entity.UserRole;

import com.example.UltiOauth.Repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImp(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    private Collection<SimpleGrantedAuthority> mapRolesToAuthorities(List<UserRole> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }


    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Optional<UserEntity> user = userRepository.findByUsername(name);
        if(user.isPresent()){
            List<UserRole> roles = new ArrayList<>();
            roles.add(user.get().getRole());
            Collection<SimpleGrantedAuthority> authorities= mapRolesToAuthorities(roles);

            return new UserPrincipal(user.get().getUsername(), user.get().getPassword(),true, authorities);

        }
        else{
            throw new UsernameNotFoundException("User " + name + " not found");
        }
    }

}
