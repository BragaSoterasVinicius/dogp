package com.soter.dogp.objcts;

import com.soter.dogp.service.PostService;
import com.soter.dogp.service.SmellService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;

public class PersonalPost {

    private Integer id;

    private Date datetime;

    private Integer user_id;

    private String post;

    private Integer poste_id;

    private String apelido;

    private String imgName;
    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setPosteId(int posteId) {
        this.poste_id = posteId;
    }

    public int getId() {
        return id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public int getUserid() {
        return user_id;
    }

    public String getPost() {
        return post;
    }

    public int getPosteId() {
        return poste_id;
    }

    public boolean isHunted;

    public boolean hasImage;

    public void setImgName(String imagemName){
        imgName = imagemName;
    }
    public String getImgName(){
        return imgName;
    }
    public boolean hasImage(){
        return imgName != null;
    }
    public boolean isHunted() {
        return isHunted;
    }

    public void setHunted(boolean hunted) {
        isHunted = hunted;
    }
}
