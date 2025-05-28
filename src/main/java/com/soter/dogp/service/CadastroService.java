package com.soter.dogp.service;

import com.soter.dogp.objcts.User;
import com.soter.dogp.repo.UserRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class CadastroService {

    @Autowired
    private UserRepo userRepo;

    public void saveUser(String nome, String email, String senha){
        if(doesUserNotExists(email)){
            saveUserToBd(nome, email, senha);
        }
    }
    public boolean doesUserNotExists(String email){
        return userRepo.searchForUserSenha(email)== null;
    }
    public void saveUserToBd(String nome, String email, String senha){
        String encodedSenha = Integer.toString(senha.hashCode());
        Integer homePost = setUpHomePoste();
        userRepo.saveNewUser(nome, email, encodedSenha, homePost);
        setCadastroLastPosteAsHomePoste(homePost, email);
    }
    public void setCadastroLastPosteAsHomePoste(Integer homePoste, String email){
        userRepo.alterLastPost(homePoste, email);
    }

    //Esse método vai ter que ser alterado dada um futuro aumento em escalabilidade
    public Integer setUpHomePoste(){
        Random r = new Random();
        /*List<Integer> usedPostes = userRepo.getLastHomePostes();
        int space = usedPostes.size()+1;
        int homePoste = r.nextInt(space);
        Como definir qual vai ser a home dos usuários? Depende muito de quantos usuarios existem.
        Dado que eu espero poucos, é apropriado deixar na casa de dezenas msm*/
        List<Integer> usedPostes = userRepo.getUsedHomePostes();
        //int space = usedPostes.size()+1;
        //NUMERO DE CASAS POSSIVEIS
        int space = 9400;
        for(int p = 0; p < space; p++ ){
        Integer homePost = r.nextInt(space);
        if (!usedPostes.contains(homePost)){
            return homePost;
        }
        }
        return 0;
        //long l = listSize + (long) listSize / 10;
        //int space = (int) l;
        //Integer newHome = r.nextInt(space - listSize) + listSize;
        //É definido um novo espaço com dez porcento do tamanho de opções usadas.
        //Por enquanto o novo usuário vai ficar no final na lista de postes, por exemplo,
        //se temos 100 postes ocupados, o novo usuário vai ganhar um poste entre o 101 e o 110.

    }
}
