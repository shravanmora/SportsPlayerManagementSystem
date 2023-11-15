package com.sports.player.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sports.player.repository.model.Sports;


@Repository
public interface SportsRepository extends JpaRepository<Sports, Long>{

}