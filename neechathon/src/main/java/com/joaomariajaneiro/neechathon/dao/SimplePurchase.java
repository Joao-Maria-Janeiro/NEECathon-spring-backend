package com.joaomariajaneiro.neechathon.dao;

import com.joaomariajaneiro.neechathon.model.Product;

import java.time.LocalDateTime;

public class SimplePurchase {

    private Long id;

    private String teamName;

    private Product product;

    private Long quantity;

    private Long totalAmount;

    private String user;

    private Long sourceTeamCash;

    private LocalDateTime timestamp;

    public SimplePurchase() {
    }

    public SimplePurchase(Long id, String teamName, Product product, Long quantity,
                          Long totalAmount, String user, Long sourceTeamCash,
                          LocalDateTime timestamp) {
        this.id = id;
        this.teamName = teamName;
        this.product = product;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
        this.user = user;
        this.sourceTeamCash = sourceTeamCash;
        this.timestamp = timestamp;
    }

    public String getTeamName() {
        return teamName;
    }

    public SimplePurchase setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public SimplePurchase setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public SimplePurchase setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public SimplePurchase setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public String getUser() {
        return user;
    }

    public SimplePurchase setUser(String user) {
        this.user = user;
        return this;
    }

    public Long getSourceTeamCash() {
        return sourceTeamCash;
    }

    public SimplePurchase setSourceTeamCash(Long sourceTeamCash) {
        this.sourceTeamCash = sourceTeamCash;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public SimplePurchase setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
