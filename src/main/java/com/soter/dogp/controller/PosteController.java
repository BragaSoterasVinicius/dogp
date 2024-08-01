package com.soter.dogp.controller;

import com.soter.dogp.objcts.Posts;
import com.soter.dogp.objcts.User;
import com.soter.dogp.service.PostService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class PosteController {

    @Autowired
    private PostService postService;
    @GetMapping("/poste")
    public String posteLoader(HttpSession session, Model model){
        Integer posteId = (Integer)session.getAttribute("POSTEID");
        List<Posts> posts = postService.getPostsByPoste(posteId);
        model.addAttribute("posteId", posteId);
        model.addAttribute("listsPosts", posts);
        return "feed";
    }

    @PostMapping ("/postar")
    public String postar(HttpSession session, Model model, @ModelAttribute("Posts") Posts newPosts){
        model.addAttribute("postModel", new Posts());
        Integer user_id = (Integer) session.getAttribute("USERID");
        String post = newPosts.getPost();
        Integer posteId = (Integer) session.getAttribute("POSTEID");
        postService.makePost(user_id, post, posteId);
        return "redirect:/poste/"+posteId.toString();
    }
}
