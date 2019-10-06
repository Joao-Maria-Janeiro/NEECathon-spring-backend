package com.joaomariajaneiro.neechathon.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @Column(name = "TRANSACTION_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TRANSACTION_SOURCE_TEAM")
    private String sourceTeam;

    @Column(name = "TRANSACTION_DESTINATION_TEAM_NAME")
    private String destTeamName;

    @Column(name = "TRANSACTION_AMOUNT")
    private Long amount;

    @Column(name = "TRANSACTION_DESCRIPTION")
    private String description;

    @Column(name = "TRANSACTION_USER")
    private User user;

    @Column(name = "TRANSACTION_SOURCE_TEAM_CASH")
    private Long sourceTeamCash;

    @Column(name = "PURCHASE_TIMESTAMP")
    private LocalDateTime timestamp;

    public Transaction() {
    }

    public Transaction(String sourceTeam, String destTeamName, Long amount, String description,
                       User user, Long sourceTeamCash, LocalDateTime timestamp) {
        this.sourceTeam = sourceTeam;
        this.destTeamName = destTeamName;
        this.amount = amount;
        this.description = description;
        this.user = user;
        this.sourceTeamCash = sourceTeamCash;
        this.timestamp = timestamp;
    }

    public User getUser() {
        return user;
    }

    public Transaction setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getSourceTeamCash() {
        return sourceTeamCash;
    }

    public Transaction setSourceTeamCash(Long sourceTeamCash) {
        this.sourceTeamCash = sourceTeamCash;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Transaction setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Transaction setId(Long id) {
        this.id = id;
        return this;
    }

    public String getSourceTeam() {
        return sourceTeam;
    }

    public Transaction setSourceTeam(String sourceTeam) {
        this.sourceTeam = sourceTeam;
        return this;
    }

    public String getDestTeamName() {
        return destTeamName;
    }

    public Transaction setDestTeamName(String destTeamName) {
        this.destTeamName = destTeamName;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public Transaction setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Transaction setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }
}
