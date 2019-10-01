package com.joaomariajaneiro.neechathon.repository;

import com.joaomariajaneiro.neechathon.model.Project;
import com.joaomariajaneiro.neechathon.model.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Project, Long> {
    Project findByTitle(String title);
    Project findByTeam(Team team);
}
