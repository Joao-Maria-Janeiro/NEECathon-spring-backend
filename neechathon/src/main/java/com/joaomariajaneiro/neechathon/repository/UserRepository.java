package com.joaomariajaneiro.neechathon.repository;

import com.joaomariajaneiro.neechathon.model.Team;
import com.joaomariajaneiro.neechathon.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findByTeam(Team team);
    User findByEmail(String email);
}
