package com.joaomariajaneiro.neechathon.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.joaomariajaneiro.neechathon.dao.SimpleTransaction;
import com.joaomariajaneiro.neechathon.model.Product;
import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.model.Transaction;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.ProductRepository;
import com.joaomariajaneiro.neechathon.repository.TeamRepository;
import com.joaomariajaneiro.neechathon.repository.TransactionRepository;
import com.joaomariajaneiro.neechathon.repository.UserRepository;
import com.joaomariajaneiro.neechathon.security.JwtProperties;
import com.joaomariajaneiro.neechathon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping("/transactions")
@RestController
public class TransactionController {

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<SimpleTransaction> getAllTransactions(@RequestHeader Map<String,
            String> headers) {
        User user;
        try {
            user = userService.getUserFromToken(headers);
        } catch (Exception e) {
            return ImmutableList.of();
        }

        if (user == null) {
            return ImmutableList.of();
        }

        if (user.getTeam().isAdmin()) {
            List<SimpleTransaction> simpleTransactions = new ArrayList<>();
            transactionRepository.findAll().forEach((transaction) -> {
                        SimpleTransaction transaction1 = new SimpleTransaction(
                                transaction.getSourceTeam(), transaction.getDestTeamName(),
                                transaction.getDescription(), transaction.getAmount());
                        simpleTransactions.add(
                                transaction1);
                    }
            );
            return simpleTransactions;
        } else {
            return ImmutableList.of();
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Iterable<SimpleTransaction> getMyTransactions(@RequestHeader Map<String,
            String> headers) {

        User user;
        try {
            user = userService.getUserFromToken(headers);
        } catch (Exception e) {
            return ImmutableList.of();
        }
        if (user == null) {
            return ImmutableList.of();
        }


        Team sourceTeam;
        try {
            sourceTeam = user.getTeam();
        } catch (Exception e) {
            return ImmutableList.of();
        }

        return createSimpleTransactionList(sourceTeam.getTransactions());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProject(@RequestBody String payload,
                                @RequestHeader Map<String, String> headers) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);

        User user;
        try {
            user = userService.getUserFromToken(headers);
        } catch (Exception e) {
            return "Bad credentials";
        }
        if (user == null) {
            return "Bad credentials";
        }


        Team sourceTeam;
        try {
            sourceTeam = user.getTeam();
        } catch (Exception e) {
            return "The source team does not exist";
        }

        String description;
        try {
            description = jsonNode.get("description").asText();
        } catch (Exception e) {
            return "You must provide a description";
        }


        String destTeamName;
        try {
            destTeamName = jsonNode.get("destTeam").asText();
        } catch (Exception e) {
            return "You must provide a destination team";
        }

        if (destTeamName.trim().equals(sourceTeam.getName().trim())) {
            return "You can't do a transaction to your own team!";
        }

        Team destTeam;
        try {
            destTeam = teamRepository.findByName(destTeamName);
        } catch (Exception e) {
            return "The destination team does not exist";
        }

        if (destTeam == null) {
            return "The team you selected does not exist";
        }

        Long value = 0L;
        try {
            value = Long.valueOf((jsonNode.get("value").asText()));
        } catch (Exception e) {
            return "You must provide the amount for the transaction";
        }

        if (value > sourceTeam.getCash()) {
            return "You don't have enough money for that transfer, your team currently has: " + sourceTeam.getCash().toString();
        }

        LocalDateTime timestamp = LocalDateTime.now();

        // Add the transaction to the team's transactions
        try {
            List<Transaction> transactions = sourceTeam.getTransactions();
            transactions.add(transactionRepository.save(new Transaction(sourceTeam.getName(),
                    destTeamName, -value, description, user, sourceTeam.getCash() - value, timestamp)));
            sourceTeam.setTransactions(transactions);
        } catch (Exception e) {
            return "There was a problem with this transaction, please try again later";
        }

        try {
            List<Transaction> transactionsDest = destTeam.getTransactions();
            transactionsDest.add(transactionRepository.save(new Transaction(sourceTeam.getName(),
                    destTeamName, value, description, user, sourceTeam.getCash() + value, timestamp)));
            destTeam.setTransactions(transactionsDest);
        } catch (Exception e) {
            return "There was a problem with this transaction, please try again later";
        }

        // Update the cash for each team
        sourceTeam.setCash(sourceTeam.getCash() - value);
        destTeam.setCash(destTeam.getCash() + value);

        try {
            teamRepository.save(sourceTeam);
            teamRepository.save(destTeam);
        } catch (Exception e) {
            return "There was a problem with this transaction, please try again later";
        }

        return "Transaction successful";

    }

    @RequestMapping(value = "/{teamName}", method = RequestMethod.GET)
    public List<SimpleTransaction> getTransactionsFromTeamName(@PathVariable String teamName) {
        Team team;
        try {
            team = teamRepository.findByName(teamName);
        } catch (Exception e) {
            return null;
        }
        try {
            return createSimpleTransactionList(team.getTransactions());
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/buy", method = RequestMethod.POST)
    public String buyProducts(@RequestBody String payload, @RequestHeader Map<String,
            String> headers) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);

        String token;
        try {
            token = headers.get("authorization").replace(JwtProperties.TOKEN_PREFIX, "");
        } catch (Exception e) {
            return "The user is not logged in";
        }


        String email = JWT.require(Algorithm.HMAC256(JwtProperties.SECRET.getBytes()))
                .build()
                .verify(token)
                .getSubject();

        User user;

        try {
            user = userRepository.findByEmail(email);
        } catch (Exception e) {
            return "Bad credentials";
        }


        Team sourceTeam;
        try {
            sourceTeam = user.getTeam();
        } catch (Exception e) {
            return "The source team does not exist";
        }

        String productName = "";
        try {
            productName = jsonNode.get("product").asText();
        } catch (Exception e) {
            return "You must choose a product";
        }

        Product product;
        try {
            product = productRepository.findByName(productName);
        } catch (Exception e) {
            return "The product you were looking for does not exist";
        }

        int quantity = 0;
        try {
            quantity = Integer.valueOf(jsonNode.get("quantity").asText());
        } catch (Exception e) {
            return "You must choose the quantity of " + productName;
        }

        if (sourceTeam.getCash() < quantity * product.getPrice()) {
            return "Your team does not have enough funds";
        }

        sourceTeam.setCash(sourceTeam.getCash() - quantity * product.getPrice());
        try {
            teamRepository.save(sourceTeam);
        } catch (Exception e) {
            return "There was a problem with this acquisition, please try again later";
        }

        return "Your purchase was successful! \n Product: " + productName + "\n Quantity: " + quantity + "\n Please do not close this page and present this purchase to the NEEC team in the store!";

    }

    List<SimpleTransaction> createSimpleTransactionList(List<Transaction> transactions) {
        List<SimpleTransaction> simpleTransactions = new ArrayList<>();
        transactions.forEach((transaction) -> {
                    SimpleTransaction transaction1 = new SimpleTransaction(transaction.getSourceTeam(), transaction.getDestTeamName(), transaction.getDescription(), transaction.getAmount());
                    simpleTransactions.add(
                            transaction1);
                }
        );
        return simpleTransactions;
    }
}
