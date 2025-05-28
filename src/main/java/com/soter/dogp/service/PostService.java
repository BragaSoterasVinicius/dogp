package com.soter.dogp.service;

import com.soter.dogp.objcts.PersonalPost;
import com.soter.dogp.objcts.Posts;
import com.soter.dogp.repo.PostRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private SmellService smellService;

    public PostService(SmellService smellService) {
        this.smellService = smellService;
    }

    public List<Posts> getPostsByPoste(Integer posteNumber) {
        List<Posts> postePosts = postRepo.findPostsByPosteId(posteNumber);
        Collections.reverse(postePosts);
        return postePosts;
    }

    public List<Posts> getPostsByUserId(Integer user_id) {
        List<Posts> postePosts = postRepo.getPostsByUserId(user_id);
        return postePosts;
    }

    public void makePost(Integer userId, String text, Integer poste, String imgname) {
        postRepo.createPost(userId, text, poste, imgname);
    }

    public List<PersonalPost> buildPersonalPosts(List<Posts> listaOriginal, HttpSession session) {
        List<PersonalPost> postsPessoais = new ArrayList<>();
        for (int n = 0; n < listaOriginal.size(); n++) {
            postsPessoais.add(buildPersonalIndividualPost(listaOriginal.get(n), session));
        }
        return postsPessoais;

    }

    private PersonalPost buildPersonalIndividualPost(Posts posts, HttpSession session) {
        PersonalPost postPessoal = new PersonalPost();
        postPessoal.setId(posts.getId());
        postPessoal.setDatetime(posts.getDatetime());
        postPessoal.setUserId(posts.getUserid());
        postPessoal.setPost(posts.getPost());
        postPessoal.setPosteId(posts.getPosteId());
        postPessoal.setApelido(smellService.getApelido(posts.getUserid(), (Integer) session.getAttribute("USERID")));
        postPessoal.setImgName(posts.getImgname());
        Integer user_id = (Integer) session.getAttribute("USERID");
        boolean hunted = smellService.isAuthorBeingHuntedByTheUser(user_id, posts.getUserid());
        postPessoal.setHunted(hunted);
        return postPessoal;
    }
}
