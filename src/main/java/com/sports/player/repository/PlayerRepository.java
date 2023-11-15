package com.sports.player.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.sports.player.repository.model.Players;

@Repository
public interface PlayerRepository extends JpaRepository<Players, Long>{
	Players findByEmailContaining(String email);
	
	@Query("SELECT p  FROM Players p")
	List<Players> findNoPlayers();
	
	Page<Players> findByEmailContaining(String email, Pageable pageable);
}