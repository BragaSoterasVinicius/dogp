package com.soter.dogp.service;

import com.soter.dogp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginService {
    @Autowired
    private UserRepo userRepo;
    public boolean auth(String email, String senha){
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String encodedSenha = bcrypt.encode(senha);
        String senhaOnDb = userRepo.searchForUserSenha(email);
        return Objects.equals(encodedSenha, senhaOnDb);
    }
}
