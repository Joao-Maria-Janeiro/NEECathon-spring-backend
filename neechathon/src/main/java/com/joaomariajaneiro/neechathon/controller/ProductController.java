package com.joaomariajaneiro.neechathon.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.joaomariajaneiro.neechathon.model.Product;
import com.joaomariajaneiro.neechathon.model.User;
import com.joaomariajaneiro.neechathon.repository.ProductRepository;
import com.joaomariajaneiro.neechathon.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@Slf4j
@RequestMapping("/api")
@RestController
@CrossOrigin
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserService userService;

    ObjectMapper objectMapper = new ObjectMapper();


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public @ResponseBody
    Iterable<Product> getAllProducts(@RequestHeader Map<String, String> headers) {

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
            return productRepository.findAll();
        } else {
            return ImmutableList.of();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createProduct(@RequestBody String payload,
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

        if (user.getTeam().isAdmin()) {
            Product product;
            try {
                product = new Product(
                        jsonNode.get("name").asText(),
                        jsonNode.get("price").asLong(),
                        jsonNode.get("description").asText(),
                        jsonNode.get("quantity").asInt(),
                        jsonNode.get("image_path").asText());
            } catch (Exception e) {
                return "There was an error creating the product, did you set all fields?";
            }
            try {
                productRepository.save(product);
            } catch (Exception e) {
                return "The product was not created, there was an error. Is this a duplicate " +
                        "product?";
            }
            return "Product created with success";
        } else {
            return "User must be a part of the admin team";
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateProduct(@RequestBody String payload,
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

        if (user.getTeam().isAdmin()) {
            Product product;
            try {
                product = productRepository.findByName(jsonNode.get("name").asText());
            } catch (Exception e) {
                return "That product does not exist";
            }
            try {
                if (jsonNode.get("increase").asBoolean()) {
                    productRepository.save(product.setQuantity(product.getQuantity() + 1));
                } else {
                    productRepository.save(product.setQuantity(product.getQuantity() - 1));
                }
            } catch (Exception e) {
                return "Unable to update product";
            }
            return "Product updated";
        } else {
            return "User must be a part of the admin team";
        }
    }
}
