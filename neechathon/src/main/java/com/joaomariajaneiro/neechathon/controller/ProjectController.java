package com.joaomariajaneiro.neechathon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaomariajaneiro.neechathon.model.Project;
import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.repository.ProjectRepository;
import com.joaomariajaneiro.neechathon.repository.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RequestMapping("/projects")
@RestController
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Project> getAllProjects(){
        return projectRepository.findAll();
    }


    @RequestMapping(value = "/{teamName}", method = RequestMethod.GET)
    public Project getProjectFromTeamName(@PathVariable String teamName){
        Team team;
        try {
            team = teamRepository.findByName(teamName);
        }catch (Exception e){
            return null;
        }
        try {
            return team.getProject();
        }catch (Exception e){
            return null;
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject(@RequestBody String payload) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        String title = "";
        try {
            title = jsonNode.get("title").asText();
        }catch (Exception e){
            return "You need to provide a title";
        }

        String description = "";
        try {
            title = jsonNode.get("description").asText();
        }catch (Exception e){
            return "You need to provide a description";
        }

        String gitHubURL = "";
        try {
            gitHubURL = jsonNode.get("gitHubURL").asText();
        }catch (Exception e){
            return "You need to provide a URL for the GitHub project";
        }

        String teamName = "";
        try {
            teamName = jsonNode.get("teamName").asText();
        }catch (Exception e){
            return "You need to provide the team name";
        }

        Team team = teamRepository.findByName(teamName);
        Project teamProj = team.getProject();
        team.setProject(null);
        try {
            projectRepository.delete(teamProj);
        }catch (Exception e){
            System.out.println("-");
        }



        Project project = new Project();
        project.setTeamName(teamName).setGitHubURL(gitHubURL).setTitle(title).setDescription(description).setTeam(team);
        projectRepository.save(project);
        team.setProject(project);
        teamRepository.save(team);


        return "The project was created with success";

    }
}
