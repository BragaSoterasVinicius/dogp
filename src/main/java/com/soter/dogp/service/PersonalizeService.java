package com.soter.dogp.service;

import com.soter.dogp.repo.PostRepo;
import com.soter.dogp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PersonalizeService {
    @Autowired
    PostRepo postRepo;

    @Autowired
    UserService userService;

    public void setupBackground(Integer backgroundId, Integer poste, String filename) {
        Integer bg = postRepo.getBackgroundId(poste);
        if(bg == null){
            postRepo.insertBg(poste, backgroundId, filename);
        }else{
            postRepo.updateBg(filename, backgroundId);
        }

    }

    public String getBackgroundByUserId(Integer user_id) {
        Integer userPoste = userService.getOriginPosteByUserId(user_id);
        String bGImage = getBackgroundId(userPoste);
        return bGImage;
    }

    public String getBackgroundId(Integer poste) {
        Integer bg = postRepo.getBackgroundId(poste);
        if (bg == null) {
            return "0.png";
        }
        String name = postRepo.getNameByBackGroundId(bg);
        return name;
    }

    public String getLayoutColorByPosteId(Integer poste) {

        Integer bg = postRepo.getBackgroundId(poste);
        if (bg == null) {
            return null;
        }
        String layoutColor = postRepo.getLayoutColorFromPost(bg);
        return layoutColor;
    }

    public void setLayoutColorByPosteId(Integer poste, String layoutColor) {
        Integer bg = postRepo.getBackgroundId(poste);
        postRepo.updateLayoutColor(layoutColor, bg);
    }

}
