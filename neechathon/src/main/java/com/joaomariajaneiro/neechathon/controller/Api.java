package com.joaomariajaneiro.neechathon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joaomariajaneiro.neechathon.model.Project;
import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.ProjectRepository;
import com.joaomariajaneiro.neechathon.repository.TeamRepository;
import com.joaomariajaneiro.neechathon.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/api")
@RestController
@CrossOrigin
public class Api {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createTeam(@RequestBody String payload) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);


        String name = "";
        try {
            name = jsonNode.get("name").asText();
        } catch (Exception e) {
            return "You need to provide team a name";
        }

        Team team = new Team();
        try {
            team.setName(name);
            teamRepository.save(team);
        } catch (Exception e) {
            return "There was an error creating the team, the name has already been used";
        }

        List<User> users = team.getUsers();


        // Create User1
        String username = "";
        int user1 = 1;
        try {
            username = jsonNode.get("username1").asText();
        } catch (Exception e) {
            user1 = 0;
        }

        if (user1 == 1) {
            String password = "";
            try {
                password = jsonNode.get("password1").asText();
            } catch (Exception e) {
                return "You need to provide a password";
            }

            String email = "";
            try {
                email = jsonNode.get("email1").asText();
            } catch (Exception e) {
                return "You need to provide an email";
            }

            String linkedIn = "";
            if (jsonNode.has("linkedIn1")) {
                try {
                    linkedIn = jsonNode.get("linkedIn1").asText();
                } catch (Exception e) {
                    return "You need to provide a link to your linkedIn profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            String gitHub = "";
            if (jsonNode.has("gitHub1")) {
                try {
                    gitHub = jsonNode.get("gitHub1").asText();
                } catch (Exception e) {
                    return "You need to provide your GitHub profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            User user;
            try {
                user = new User();
                user.setEmail(email).setGitHub(gitHub).setLinkedIn(linkedIn).setPassword(passwordEncoder.encode(password)
                ).setUsername(username).setTeam(team);
                userRepository.save(user);
            } catch (Exception e) {
                teamRepository.delete(team);
                return "A user with that email already exists " + email;
            }
            users.add(user);
        }


        // Create User2
        user1 = 1;
        username = "";
        try {
            username = jsonNode.get("username2").asText();
        } catch (Exception e) {
            user1 = 0;
        }

        if (user1 == 1) {
            String password = "";
            try {
                password = jsonNode.get("password2").asText();
            } catch (Exception e) {
                return "You need to provide a password";
            }

            String email = "";
            try {
                email = jsonNode.get("email2").asText();
            } catch (Exception e) {
                return "You need to provide an email";
            }

            String linkedIn = "";
            if (jsonNode.has("linkedIn2")) {
                try {
                    linkedIn = jsonNode.get("linkedIn2").asText();
                } catch (Exception e) {
                    return "You need to provide a link to your linkedIn profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            String gitHub = "";
            if (jsonNode.has("gitHub2")) {
                try {
                    gitHub = jsonNode.get("gitHub2").asText();
                } catch (Exception e) {
                    return "You need to provide your GitHub profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            User user2;
            try {
                user2 = new User();
                user2.setEmail(email).setGitHub(gitHub).setLinkedIn(linkedIn).setPassword(passwordEncoder.encode(password)).setUsername(username).setTeam(team);
                userRepository.save(user2);
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "A user with that email already exists " + email;
            }
            users.add(user2);
        }


        // Create User3
        user1 = 1;
        username = "";
        try {
            username = jsonNode.get("username3").asText();
        } catch (Exception e) {
            user1 = 0;
        }
        if (user1 == 1) {
            String password = "";
            try {
                password = jsonNode.get("password3").asText();
            } catch (Exception e) {
                return "You need to provide a password";
            }

            String email = "";
            try {
                email = jsonNode.get("email3").asText();
            } catch (Exception e) {
                return "You need to provide an email";
            }

            String linkedIn = "";
            if (jsonNode.has("linkedIn3")) {
                try {
                    linkedIn = jsonNode.get("linkedIn3").asText();
                } catch (Exception e) {
                    return "You need to provide a link to your linkedIn profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            String gitHub = "";
            if (jsonNode.has("gitHub3")) {
                try {
                    gitHub = jsonNode.get("gitHub3").asText();
                } catch (Exception e) {
                    return "You need to provide your GitHub profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            User user2;
            try {
                user2 = new User();
                user2.setEmail(email).setGitHub(gitHub).setLinkedIn(linkedIn).setPassword(passwordEncoder.encode(password)).setUsername(username).setTeam(team);
                userRepository.save(user2);
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "A user with that email already exists " + email;
            }
            users.add(user2);
        }

        // Create User4
        user1 = 1;
        username = "";
        try {
            username = jsonNode.get("username4").asText();
        } catch (Exception e) {
            user1 = 0;
        }
        if (user1 == 1) {
            String password = "";
            try {
                password = jsonNode.get("password4").asText();
            } catch (Exception e) {
                return "You need to provide a password";
            }

            String email = "";
            try {
                email = jsonNode.get("email4").asText();
            } catch (Exception e) {
                return "You need to provide an email";
            }


            String linkedIn = "";
            if (jsonNode.has("linkedIn4")) {
                try {
                    linkedIn = jsonNode.get("linkedIn4").asText();
                } catch (Exception e) {
                    return "You need to provide a link to your linkedIn profile URL (if you don't have one we strongly recommend you create one)";
                }
            }


            String gitHub = "";
            if (jsonNode.has("gitHub4")) {
                try {
                    gitHub = jsonNode.get("gitHub4").asText();
                } catch (Exception e) {
                    return "You need to provide your GitHub profile URL (if you don't have one we strongly recommend you create one)";
                }
            }

            User user2;
            try {
                user2 = new User();
                user2.setEmail(email).setGitHub(gitHub).setLinkedIn(linkedIn).setPassword(passwordEncoder.encode(password)).setUsername(username).setTeam(team);
                userRepository.save(user2);
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "A user with that email already exists " + email;
            }

            users.add(user2);
        }


        try {
            team.setUsers(users);
        } catch (Exception e) {
            return "There was an error creating the team";
        }

        if (jsonNode.has("title")) {
            String title = "";
            try {
                title = jsonNode.get("title").asText();
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "You need to provide a project title";
            }

            String description = "";
            try {
                title = jsonNode.get("description").asText();
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "You need to provide a project description";
            }

            String gitHubURL = "";
            try {
                gitHubURL = jsonNode.get("gitHubURL").asText();
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "You need to provide a URL for the GitHub project";
            }

            Project project;
            try {
                project = new Project();
                project.setTeamName(team.getName()).setGitHubURL(gitHubURL).setTitle(title).setDescription(description).setTeam(team);
                projectRepository.save(project);
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "A project with that name already exists";
            }

            try {
                team.setProject(project);
                teamRepository.save(team);
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "There was an error creating the team";
            }
        } else {
            try {
                teamRepository.save(team);
            } catch (Exception e) {
                userRepository.deleteAll(users);
                team.setUsers(new ArrayList<>());
                teamRepository.save(team);
                teamRepository.delete(team);
                return "There was an error creating the team";
            }
        }

        return "All users, project and team were created successfully!!";
    }
}
