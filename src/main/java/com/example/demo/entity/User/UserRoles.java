package com.example.demo.entity.User;

import org.springframework.security.core.GrantedAuthority;

public enum UserRoles implements GrantedAuthority{
    USUARIO("usuario"),
    PROFESSOR("professor"),
    ADMINISTRADOR("administrador");

    private final String role;
    UserRoles(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }

    @Override
    public String getAuthority(){
        return this.role;
    }
}
