package com.joaomariajaneiro.neechathon.model;


import javax.persistence.*;

@Entity
@Table(name = "PROJECT")
public class Project {

    @Id
    @Column(name = "PROJECT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PROJECT_TITLE")
    private String title;

    @Column(name = "PROJECT_DESCRIPTION")
    private String description;

    @Column(name = "PROJECT_GITHUB_URL")
    private String gitHubURL;

    @OneToOne
    @JoinColumn(name = "PROJECT_ID")
    private Team team;

    @Column(name = "PROJECT_TEAM_NAME")
    private String teamName;


    public String getTeamName() {
        return teamName;
    }

    public Project setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public Team getTeam() {
        return team;
    }

    public Project setTeam(Team team) {
        this.team = team;
        return this;
    }

    public Project() {
    }

    public Project(String title, String description, String gitHubURL, Team team) {
        this.title = title;
        this.description = description;
        this.gitHubURL = gitHubURL;
        this.team = team;
    }

    public Long getId() {
        return id;
    }

    public Project setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Project setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Project setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getGitHubURL() {
        return gitHubURL;
    }

    public Project setGitHubURL(String gitHubURL) {
        this.gitHubURL = gitHubURL;
        return this;
    }
}
