package com.joaomariajaneiro.neechathon.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")

@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "USER_USERNAME", nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "USER_EMAIL", unique = true)
    private String email;

    @Column(name = "USER_LINKEDIN")
    private String linkedIn;

    @Column(name = "USER_GITHUB")
    private String gitHub;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Team team;

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public User setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
        return this;
    }

    public String getGitHub() {
        return gitHub;
    }

    public User setGitHub(String gitHub) {
        this.gitHub = gitHub;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public User setTeam(Team team) {
        this.team = team;
        return this;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    private int active;

    private String roles = "";

    private String permissions = "";

    public User(String username, String password, String roles, String permissions) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.permissions = permissions;
        this.active = 1;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getActive() {
        return active;
    }

    public String getRoles() {
        return roles;
    }

    public String getPermissions() {
        return permissions;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length() > 0){
            return Arrays.asList(this.permissions.split(","));
        }
        return new ArrayList<>();
    }
}
