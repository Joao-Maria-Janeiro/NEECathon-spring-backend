package com.joaomariajaneiro.neechathon.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PURCHASE")
public class Purchase {

    @Column(name = "PURCHASE_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "PURCHASE_TEAM")
    private String teamName;

    @Column(name = "PURCHASE_PRODUCT")
    private Product product;

    @Column(name = "PURCHASE_QUANTITY")
    private Long quantity;

    @Column(name = "PURCHASE_AMOUNT")
    private Long totalAmount;

    @Column(name = "PURCHASE_USER")
    private User user;

    @Column(name = "PURCHASE_SOURCE_TEAM_CASH")
    private Long sourceTeamCash;

    @Column(name = "PURCHASE_TIMESTAMP")
    private LocalDateTime timestamp;

    public Purchase() {
    }

    public Purchase(String teamName, Product product, Long quantity, Long totalAmount, User user, Long sourceTeamCash, LocalDateTime timestamp) {
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

    public Purchase setTeamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public Purchase setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Long getQuantity() {
        return quantity;
    }

    public Purchase setQuantity(Long quantity) {
        this.quantity = quantity;
        return this;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public Purchase setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Purchase setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getSourceTeamCash() {
        return sourceTeamCash;
    }

    public Purchase setSourceTeamCash(Long sourceTeamCash) {
        this.sourceTeamCash = sourceTeamCash;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Purchase setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
