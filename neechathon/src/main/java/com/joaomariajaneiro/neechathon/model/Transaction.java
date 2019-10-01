package com.joaomariajaneiro.neechathon.model;

import javax.persistence.*;

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

    public Transaction() {
    }

    public Transaction(String sourceTeam, String destTeamName, Long amount, String description) {
        this.sourceTeam = sourceTeam;
        this.destTeamName = destTeamName;
        this.amount = amount;
        this.description = description;
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
}
