package com.joaomariajaneiro.neechathon.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "name")
@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @Column(name = "TEAM_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TEAM_NAME", unique = true)
    private String name;

    @Column(name = "TEAM_CASH")
    private Long cash;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<User> users;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Transaction> transactions;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")
    private List<Purchase> purchases;

    @OneToOne
    private Project project;

    @Column(name = "TEAM_IS_ADMIN")
    private Boolean isAdmin;


    public Boolean isAdmin() {
        return isAdmin;
    }

    public Team setAdmin(Boolean admin) {
        isAdmin = admin;
        return this;
    }

    public Team() {
        this.transactions = new ArrayList<>();
        this.users = new ArrayList<>();
        this.cash = 200L;
    }

    public Team(String name, Long cash, List<User> users, List<Transaction> transactions,
                List<Purchase> purchases, Project project) {
        this.name = name;
        this.purchases = purchases;
        this.cash = 200L;
        this.users = users;
        this.transactions = transactions;
        this.project = project;
        this.isAdmin = false;
    }

    public Team(String name, Long cash, List<User> users, List<Transaction> transactions,
                List<Purchase> purchases, Project project, Boolean isAdmin) {
        this.name = name;
        this.purchases = purchases;
        this.cash = 200L;
        this.users = users;
        this.transactions = transactions;
        this.project = project;
        this.isAdmin = isAdmin;
    }

    public Long getId() {
        return id;
    }

    public Team setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public Long getCash() {
        return cash;
    }

    public Team setCash(Long cash) {
        this.cash = cash;
        return this;
    }

    public List<User> getUsers() {
        return users;
    }

    public Team setUsers(List<User> user) {
        this.users = user;
        return this;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Team setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        return this;
    }

    public Project getProject() {
        return project;
    }

    public Team setProject(Project project) {
        this.project = project;
        return this;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public Team setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
        return this;
    }
}
