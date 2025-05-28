package com.soter.dogp.controller;

import com.soter.dogp.objcts.PersonalPost;
import com.soter.dogp.objcts.Posts;
import com.soter.dogp.objcts.User;
import com.soter.dogp.service.CadastroService;
import com.soter.dogp.service.DogService;
import com.soter.dogp.service.PersonalizeService;
import com.soter.dogp.service.PostService;
import com.soter.dogp.service.UserService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PosteController {

    @Autowired
    private CadastroService cadastroService;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private DogService dogService;
    @Autowired
    private ImageController imageController;
    @Autowired
    private PersonalizeService personalizeService;

    @GetMapping("/poste")
    public String posteLoader(HttpSession session, Model model) {
        Integer posteId = (Integer) session.getAttribute("POSTEID");
        if (posteId == null) {
            return "redirect:/cadastro";
        }
        String email = (String) session.getAttribute("USEREMAIL");
        String name = (String) session.getAttribute("USERNAME");
        Integer userid = (Integer) session.getAttribute("USERID");
        userService.setUserLastPoste(posteId, email);
        dogService.getImagesByPosteId(posteId);
        List<Posts> posts = postService.getPostsByPoste(posteId);
        List<PersonalPost> personalPosts = postService.buildPersonalPosts(posts, session);
        List<String> dogGallery = dogService.getImagesByPosteId(posteId);
        String posteBg = personalizeService.getBackgroundId(posteId);
        model.addAttribute("userid", userid);
        model.addAttribute("posteId", posteId);
        model.addAttribute("username", name);
        model.addAttribute("listsPosts", personalPosts);
        model.addAttribute("dogGallery", dogGallery);
        model.addAttribute("backgroundImage", posteBg);
        return "feed";
    }

    @GetMapping("/gotoMyPoste")
    public String gotoMyPoste(HttpSession session) {
        String email = (String) session.getAttribute("USEREMAIL");
        Integer homePoste = userService.getOriginPosteByUserId((Integer) session.getAttribute("USERID"));
        userService.setUserLastPoste(homePoste, email);
        session.setAttribute("POSTEID", homePoste);
        return "redirect:/poste";
    }

    @PostMapping("/postar")
    public String postar(HttpSession session, Model model, @ModelAttribute("Posts") Posts newPosts, @RequestParam("image")MultipartFile file) throws IOException {
        model.addAttribute("postModel", new Posts());
        Integer user_id = (Integer) session.getAttribute("USERID");
        String post = newPosts.getPost();
        Integer posteId = (Integer) session.getAttribute("POSTEID");
        String imgname = file.getOriginalFilename();
        Path fileNameAndPath = Paths.get("/root/midia/galeria-do-dogp/posts", file.getOriginalFilename());
        if(file.getSize()>0) {
            Files.write(fileNameAndPath, file.getBytes());
        }else{
            imgname = null;
        }
        postService.makePost(user_id, post, posteId, imgname);
        return "redirect:/poste";
    }

    @GetMapping("/nextPoste")
    public String nextPoste(HttpSession session, Model model) {
        Integer rnPoste = (Integer) session.getAttribute("POSTEID");
        session.setAttribute("POSTEID", rnPoste + 1);
        return "redirect:/poste";
    }

    @GetMapping("/lastPoste")
    public String lastPoste(HttpSession session, Model model) {
        Integer rnPoste = (Integer) session.getAttribute("POSTEID");
        if (rnPoste > 0) {
            session.setAttribute("POSTEID", rnPoste - 1);
        }
        return "redirect:/poste";
    }

    @GetMapping("/jumpTo")
    public String jumpTo(HttpSession session, Model model, @ModelAttribute("Posts") Posts postePage) {
        Integer pageJump = postePage.getPosteId();
        if (pageJump >= 0) {
            session.setAttribute("POSTEID", pageJump);
        }
        return "redirect:/poste";
    }

}
