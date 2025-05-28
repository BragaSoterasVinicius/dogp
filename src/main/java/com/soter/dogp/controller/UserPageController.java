package com.soter.dogp.controller;

import com.soter.dogp.objcts.Cheiro;
import com.soter.dogp.objcts.Posts;
import com.soter.dogp.service.*;
import jakarta.servlet.http.HttpSession;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserPageController {

    @Autowired
    private PostService postService;

    @Autowired
    private SmellService smellService;

    @Autowired
    private UserService userService;

    @Autowired
    private DogService dogService;

    @Autowired
    private PersonalizeService personalizeService;

    @GetMapping("/perfil")
    public String perfil(HttpSession session, Model model) {
        String name = (String) session.getAttribute("USERNAME");
        Integer user_id = (Integer) session.getAttribute("USERID");
        List<Posts> listMyPosts = postService.getPostsByUserId(user_id);
        List<Cheiro> listCheiros = smellService.getCheiradosByUser(session);
        String posteBg = personalizeService.getBackgroundByUserId(user_id);
        String userDog = dogService.getUserDog(user_id);
        int userPoste = userService.getOriginPosteByUserId(user_id);
        model.addAttribute("username", name);
        model.addAttribute("listsPosts", listMyPosts);
        model.addAttribute("listCheiros", listCheiros);
        model.addAttribute("backgroundImage", posteBg);
        model.addAttribute("dog", userDog);
        model.addAttribute("userPoste", userPoste);
        return "usermenu";
    }

    @GetMapping("/addSmellToSearchList")
    public String addSmellToSearchList(Model model, HttpSession session, @ModelAttribute("Cheiro") Cheiro newCheiro) {
        model.addAttribute("cheiroModel", new Cheiro());
        Integer cheirado = newCheiro.getCheirado_id();
        smellService.addSmellToSearchList(session, cheirado);
        return "redirect:/perfil";
    }

    @GetMapping("/changeApelido")
    public String changeApelido(Model model, HttpSession session, @ModelAttribute("Cheiro") Cheiro newCheiro) {
        model.addAttribute("cheiroModelApelido", new Cheiro());
        String apelido = newCheiro.getApelido();
        Integer cheiroId = newCheiro.getId();
        smellService.changeApelido(cheiroId, apelido);
        return "redirect:/perfil";

    }

    @PostMapping("/changeBg")
    public String poster(HttpSession session,
                         @ModelAttribute("inputAModel")
                         Object formModel,
                         Model model, @RequestParam("image") MultipartFile file)
            throws IOException, ParseException {
        Integer userid = (Integer) session.getAttribute("USERID");
        model.addAttribute("formModel", new Object());
        StringBuilder fileNames = new StringBuilder();
        Integer originPoste = userService.getOriginPosteByUserId(userid);
        personalizeService.setupBackground(originPoste, originPoste, file.getOriginalFilename());
        Path fileNameAndPath = Paths.get("/root/midia/galeria-do-dogp/background", file.getOriginalFilename());
        if(file.getSize()>0) {
            Files.write(fileNameAndPath, file.getBytes());
        }
        return "redirect:/perfil";
    }
}
