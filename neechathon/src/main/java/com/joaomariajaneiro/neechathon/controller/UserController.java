package com.joaomariajaneiro.neechathon.controller;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.ProjectRepository;
import com.joaomariajaneiro.neechathon.repository.TeamRepository;
import com.joaomariajaneiro.neechathon.repository.UserRepository;
import com.joaomariajaneiro.neechathon.security.JwtProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;

@Slf4j
@RequestMapping("/users")
@RestController
@CrossOrigin
public class UserController {


    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    @RequestMapping(value = "/getToken", method = RequestMethod.POST)
    public String getToken(@RequestBody String payload) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        return JWT.create()
                .withSubject(jsonNode.get("email").asText())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .sign(HMAC256(JwtProperties.SECRET.getBytes()));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@RequestBody String payload) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);

        String username = "";
        try {
            username = jsonNode.get("username").asText();
        } catch (Exception e) {
            return "You need to provide a username";
        }

        String password = "";
        try {
            password = jsonNode.get("password").asText();
        } catch (Exception e) {
            return "You need to provide a password";
        }

        String email = "";
        try {
            email = jsonNode.get("email").asText();
        } catch (Exception e) {
            return "You need to provide an email";
        }

        String linkedIn = "";
        try {
            linkedIn = jsonNode.get("linkedIn").asText();
        } catch (Exception e) {
            return "You need to provide a link to your linkedIn profile URL (if you don't have one we strongly recommend you create one)";
        }


        String gitHub = "";
        try {
            gitHub = jsonNode.get("gitHub").asText();
        } catch (Exception e) {
            return "You need to provide your GitHub profile URL (if you don't have one we strongly recommend you create one)";
        }

        String teamName = "";
        try {
            teamName = jsonNode.get("teamName").asText();
        } catch (Exception e) {
            return "You need to provide the name of the team you'll be joining";
        }

        Team team;
        try {
            team = teamRepository.findByName(teamName);
        } catch (Exception e){
            return "That team doesn't exist";
        }
        User user = new User();
        try {
            user.setEmail(email).setGitHub(gitHub).setLinkedIn(linkedIn).setPassword(password).setUsername(username).setTeam(team);
            userRepository.save(user);
        } catch (Exception e) {
            return "There was an error creating the user" + e.toString();
        }

        try{
            List<User> users = team.getUsers();
            users.add(user);
            team.setUsers(users);
            teamRepository.save(team);
        }catch (Exception e){
            userRepository.delete(user);
            return "There was an error trying to create this user";
        }


        return "User successfully created";
    }
}
