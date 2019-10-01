package com.joaomariajaneiro.neechathon.dao;

public class SimpleTransaction {
    private String sourceTeam;
    private String destTeam;
    private String description;
    private Long amount;

    public SimpleTransaction() {
    }

    public SimpleTransaction(String sourceTeam, String destTeam, String description, Long amount) {
        this.sourceTeam = sourceTeam;
        this.destTeam = destTeam;
        this.description = description;
        this.amount = amount;
    }

    public String getSourceTeam() {
        return sourceTeam;
    }

    public SimpleTransaction setSourceTeam(String sourceTeam) {
        this.sourceTeam = sourceTeam;
        return this;
    }

    public String getDestTeam() {
        return destTeam;
    }

    public SimpleTransaction setDestTeam(String destTeam) {
        this.destTeam = destTeam;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SimpleTransaction setDescription(String description) {
        this.description = description;
        return this;
    }

    public Long getAmount() {
        return amount;
    }

    public SimpleTransaction setAmount(Long amount) {
        this.amount = amount;
        return this;
    }
}
