package com.sports.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sports.player.repository.PlayerRepository;
import com.sports.player.repository.SportsRepository;
import com.sports.player.repository.model.Players;
import com.sports.player.repository.model.Sports;

/**
 *
 * Spring Boot application starter class
 */
@SpringBootApplication
@EnableJpaAuditing
public class Application implements CommandLineRunner{
	
	@Autowired
	private PlayerRepository playerRepository;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		
		Players players = new Players();
		players.setEmail("a@gmail.com");
		players.setAge(10);
		players.setGender("M");
		players.setLevel(2);
		
		Players players1 = new Players();
		players1.setEmail("a1@gmail.com");
		players1.setAge(11);
		players1.setGender("F");
		players1.setLevel(3);
		
		Sports sports = new Sports();
		sports.setName("tennis");
		Sports sports1 = new Sports();
		sports1.setName("soccer");
		Sports sports2 = new Sports();
		sports2.setName("basketball");
		
		Set<Sports> sportsSetList1 = new HashSet<Sports>();
		sportsSetList1.add(sports);
		sportsSetList1.add(sports1);
		sportsSetList1.add(sports2);
		players.setSports(sportsSetList1);
		
		Set<Sports> sportsSetList2 = new HashSet<Sports>();
		sportsSetList2.add(sports);
		sportsSetList2.add(sports2);
//		players1.setSports(sportsSetList2);
    
		List<Players> list = new ArrayList<Players>();
		list.add(players1);
		list.add(players);
		
		playerRepository.saveAll(list);

	}	
}
