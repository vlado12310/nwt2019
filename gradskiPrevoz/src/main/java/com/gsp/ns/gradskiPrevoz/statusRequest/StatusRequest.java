package com.gsp.ns.gradskiPrevoz.statusRequest;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;

import com.gsp.ns.gradskiPrevoz.user.User;

@Entity
public class StatusRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable=false)
    boolean approved;

    @ManyToOne(optional=false)
    User user;

    @Column
    User.UserStatus type;

    @Column
    String imgUrl;

    public StatusRequest() {
        super();
    }

    public StatusRequest(Long id, boolean approved, User user, User.UserStatus type, String imgUrl) {
        this.id = id;
        this.approved = approved;
        this.user = user;
        this.type = type;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User.UserStatus getType() {
        return type;
    }

    public void setType(User.UserStatus type) {
        this.type = type;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
