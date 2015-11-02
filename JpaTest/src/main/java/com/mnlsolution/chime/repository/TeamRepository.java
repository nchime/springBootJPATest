package com.mnlsolution.chime.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.mnlsolution.chime.domain.Team;

public interface TeamRepository  extends JpaRepository<Team, Integer> {
	
	Team findByTeamName(@Param("name") String name);	

}
