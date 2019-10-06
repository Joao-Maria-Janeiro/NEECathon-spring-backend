package com.joaomariajaneiro.neechathon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.joaomariajaneiro.neechathon.dao.SimplePurchase;
import com.joaomariajaneiro.neechathon.model.Purchase;
import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.ProductRepository;
import com.joaomariajaneiro.neechathon.repository.PurchaseRepository;
import com.joaomariajaneiro.neechathon.repository.TeamRepository;
import com.joaomariajaneiro.neechathon.repository.UserRepository;
import com.joaomariajaneiro.neechathon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequestMapping("/purchases")
@RestController
@CrossOrigin
public class PurchaseController {
    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TeamRepository teamRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<SimplePurchase> getAllPurchases(@RequestHeader Map<String, String> headers) {

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
            return createSimplePurchaseList(purchaseRepository.findAll());
        } else {
            return ImmutableList.of();
        }
    }

    @RequestMapping(value = "/{teamName}", method = RequestMethod.GET)
    public List<SimplePurchase> getTransactionsFromTeamName(@PathVariable String teamName) {
        Team team;
        try {
            team = teamRepository.findByName(teamName);
        } catch (Exception e) {
            return null;
        }
        try {
            return team.getPurchases().stream().map((purchase) -> new SimplePurchase(
                    purchase.getId(),
                    purchase.getTeamName(), purchase.getProduct(), purchase.getQuantity(),
                    purchase.getTotalAmount(), purchase.getUser().getUsername(),
                    purchase.getSourceTeamCash(),
                    purchase.getTimestamp())).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public Iterable<SimplePurchase> getMyTransactions(@RequestHeader Map<String,
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

        return sourceTeam.getPurchases().stream().map((purchase) -> new SimplePurchase(
                purchase.getId(),
                purchase.getTeamName(), purchase.getProduct(), purchase.getQuantity(),
                purchase.getTotalAmount(), purchase.getUser().getUsername(),
                purchase.getSourceTeamCash(),
                purchase.getTimestamp())).collect(Collectors.toList());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createPurchase(@RequestBody String payload) throws IOException {
        JsonNode jsonNode = objectMapper.readTree(payload);
        try {
            if (jsonNode.get("sourceTeamCash").asLong() - jsonNode.get("amount").asLong() < 0) {
                return "You don't have enough cash";
            }
            Purchase purchase = new Purchase(jsonNode.get("teamName").asText(),
                    productRepository.findByName(jsonNode.get("product").asText()),
                    jsonNode.get("quantity").asLong(),
                    jsonNode.get("amount").asLong(),
                    userRepository.findByUsername(jsonNode.get("user").asText()),
                    jsonNode.get("sourceTeamCash").asLong() - jsonNode.get("amount").asLong(),
                    LocalDateTime.now()
            );
            purchaseRepository.save(purchase);
            Team team = teamRepository.findByName(jsonNode.get("teamName").asText());
            team.setCash(team.getCash() - jsonNode.get("amount").asLong());
            List<Purchase> purchases = team.getPurchases();
            purchases.add(purchase);
            team.setPurchases(purchases);
            teamRepository.save(team);
            return "Purchase Successful!";
        } catch (Exception e) {
            return "There was an error processing this purchase, please try again later";
        }
    }

    List<SimplePurchase> createSimplePurchaseList(Iterable<Purchase> transactions) {
        List<SimplePurchase> simpleTransactions = new ArrayList<>();
        transactions.forEach((purchase) -> {
                    SimplePurchase transaction1 = new SimplePurchase(
                            purchase.getId(),
                            purchase.getTeamName(), purchase.getProduct(), purchase.getQuantity(),
                            purchase.getTotalAmount(), purchase.getUser().getUsername(),
                            purchase.getSourceTeamCash(),
                            purchase.getTimestamp());
                    simpleTransactions.add(
                            transaction1);
                }
        );
        return simpleTransactions;
    }
}
