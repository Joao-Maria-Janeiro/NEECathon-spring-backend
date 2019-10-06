package com.joaomariajaneiro.neechathon.bootstrap;

import com.google.common.collect.ImmutableList;
import com.joaomariajaneiro.neechathon.model.*;
import com.joaomariajaneiro.neechathon.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ImportSampleData implements ImportData {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;


    private PasswordEncoder passwordEncoder;

    public ImportSampleData(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void importSampleData() {


        System.out.println("Initializing data......");

        Team team1 = new Team();
        team1.setName("FIRST");
        teamRepository.save(team1);

        User user1 = new User();
        user1.setEmail("teste@teste.com").setGitHub("URL").setLinkedIn("URL!").setPassword(passwordEncoder.encode("testeteste")).setTeam(team1).setUsername("JOAO");
        userRepository.save(user1);

        User user2 = new User();
        user2.setEmail("teste@dadsadsa.com").setGitHub("URLGit").setLinkedIn("URLLinked").setPassword(passwordEncoder.encode("testeteste")).setTeam(team1).setUsername("MANUEL");
        userRepository.save(user2);


        Project project = new Project();
        project.setDescription("BEST TEAM").setTitle("FIRST TEAM TO JOIN").setGitHubURL("URL").setTeam(team1).setTeamName(team1.getName());
        projectRepository.save(project);

        List<User> users = team1.getUsers();
        users.add(user1);
        users.add(user2);
        team1.setUsers(users);
        team1.setProject(project);
        team1.setAdmin(true);
        teamRepository.save(team1);

//        List<Product> products = new ArrayList<>();
//        Product coil = new Product().setName("Coil").setPrice(20L);
//        Product capacitor = new Product().setName("Capacitor").setPrice(20L);
//        Product resistor = new Product().setName("Resistor").setPrice(5L);
//        Product lightSensor = new Product().setName("Light Sensor").setPrice(10L);
//
//        products.add(coil);
//        products.add(capacitor);
//        products.add(resistor);
//        products.add(lightSensor);
//
//        productRepository.saveAll(products);


        Team team2 = new Team();
        team2.setName("SECOND");
        teamRepository.save(team2);

        User user3 = new User();
        user3.setEmail("admin@admin.com").setGitHub("URL").setLinkedIn("URL!").setPassword(passwordEncoder.encode("admin")).setTeam(team2).setUsername("BEST USER");
        userRepository.save(user3);

        User user4 = new User();
        user4.setEmail("yay@yay.com").setGitHub("URLGit").setLinkedIn("URLLinked").setPassword(passwordEncoder.encode("admin")).setTeam(team2).setUsername("YAY BOY");
        userRepository.save(user4);


        Project project2 = new Project();
        project2.setDescription("What a project\n OMG!").setTitle("Is this actually the best project?").setGitHubURL("URL").setTeam(team2).setTeamName(team2.getName());
        projectRepository.save(project2);

        List<User> newUsers = new ArrayList<>();
        newUsers.add(user3);
        newUsers.add(user4);

        team2.setUsers(newUsers);
        team2.setProject(project2);
        teamRepository.save(team2);


        Team team3 = new Team();
        team3.setName("THIRD");
        teamRepository.save(team3);

        User user5 = new User();
        user5.setEmail("admin@google.com").setGitHub("URL").setLinkedIn("URL!").setPassword(passwordEncoder.encode("admin")).setTeam(team2).setUsername("POPCORN");
        userRepository.save(user5);

        User user6 = new User();
        user6.setEmail("yay@yahoo.com").setGitHub("URLGit").setLinkedIn("URLLinked").setPassword(passwordEncoder.encode("admin")).setTeam(team2).setUsername("YAHOOO?");
        userRepository.save(user6);


        Project project3 = new Project();
        project3.setDescription("What a project this is\n OMG!").setTitle("Is this actually the best project?").setGitHubURL("URL").setTeam(team2).setTeamName(team2.getName());
        projectRepository.save(project3);

        List<User> newUsers1 = new ArrayList<>();
        newUsers1.add(user5);
        newUsers.add(user6);

        team3.setUsers(newUsers1);
        team3.setProject(project3);
        teamRepository.save(team3);


        team1.setTransactions(ImmutableList.of(transactionRepository.save(new Transaction().setDescription("TEST").setAmount(20L).setSourceTeam("FIRST").setDestTeamName("THIRD"))));
        team3.setTransactions(ImmutableList.of(transactionRepository.save(new Transaction().setDescription("TEST").setAmount(20L).setSourceTeam("FIRST").setDestTeamName("THIRD"))));
        team1.setPurchases(ImmutableList.of(
                purchaseRepository.save(new Purchase().setProduct(productRepository.save(new Product().setName("COIL").setPrice(20L).setQuantity(5).setDescription("SIMPLE COIL").setImage_path("random")))
                        .setQuantity(5L).setSourceTeamCash(team1.getCash()).setTeamName(team1.getName()).setTimestamp(LocalDateTime.now()).setTotalAmount(5L * 20L).setUser(user1))));
        teamRepository.save(team1);
        teamRepository.save(team3);

        System.out.println("All data was initialized");


    }
}
