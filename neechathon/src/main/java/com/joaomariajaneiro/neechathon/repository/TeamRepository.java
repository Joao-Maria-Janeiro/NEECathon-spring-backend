package com.joaomariajaneiro.neechathon.repository;

import com.joaomariajaneiro.neechathon.model.Project;
import com.joaomariajaneiro.neechathon.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByName(String name);
    Team findByProject(Project project);
}
