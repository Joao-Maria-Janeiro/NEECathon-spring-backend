package com.joaomariajaneiro.neechathon.repository;

import com.joaomariajaneiro.neechathon.model.Purchase;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends CrudRepository<Purchase, Long> {
    List<Purchase> findByTeamName(String teamName);
}

