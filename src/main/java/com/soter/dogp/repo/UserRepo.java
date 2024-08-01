package com.soter.dogp.repo;

import com.soter.dogp.objcts.Posts;
import com.soter.dogp.objcts.User;
import jakarta.transaction.Transactional;
import org.hibernate.type.TrueFalseConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users(nome, email, senha, home_post) VALUES (:nome, :email, :senha, :homePoste)", nativeQuery = true)
    void saveNewUser(String nome, String email, String senha, Integer homePoste);
    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET last_post= :posteNum WHERE email= :email", nativeQuery = true)
    void alterLastPost(Integer posteNum, String email);

    @Query(value = "SELECT senha FROM users WHERE email = :email", nativeQuery = true)
    String searchForUserSenha(String email);


    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    User getUserDataByEmail(String email);

    @Query(value = "SELECT DISTINCT home_post FROM users", nativeQuery = true)
    List<Integer> getUsedHomePostes();


}
