package com.joaomariajaneiro.neechathon.repository;

import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.model.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {
    List<Transaction> findBySourceTeam(Team sourceTeam);
    List<Transaction> findByDestTeamName(String destTeamName);
}
