package com.soter.dogp.objcts;

import com.soter.dogp.service.PostService;
import com.soter.dogp.service.SmellService;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.Date;

@Entity
@Table(name = "posts" )
public class Posts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "datetime")
    private Date datetime;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "post")
    private String post;

    @Column(name = "poste_id")
    private Integer poste_id;
    @Column(name = "imgname")
    private String imgname;

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
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
}
