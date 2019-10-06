package com.joaomariajaneiro.neechathon.controller;

import com.google.common.collect.ImmutableList;
import com.joaomariajaneiro.neechathon.model.Purchase;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.PurchaseRepository;
import com.joaomariajaneiro.neechathon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/purchases")
@RestController
@CrossOrigin
public class PurchaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Purchase> getAllPurchases(@RequestHeader Map<String, String> headers) {

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
            return purchaseRepository.findAll();
        } else {
            return ImmutableList.of();
        }
    }
}
