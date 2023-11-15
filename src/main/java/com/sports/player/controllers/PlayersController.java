package com.sports.player.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.apache.el.stream.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.sports.player.repository.PlayerRepository;
import com.sports.player.repository.model.Players;
import com.sports.player.repository.model.Sports;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PlayersController {

	@Autowired
	private PlayerRepository playerRepository;

	// Get ports
	@GetMapping("/sports")
	public ResponseEntity<Set<Sports>> getSports(@RequestParam(required = false) String email) {
		Players players = new Players();

		if (email == null)
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		else
			players = playerRepository.findByEmailContaining(email);

		if (players == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(players.getSports(), HttpStatus.OK);
	}

	// Get No players
	@GetMapping("/noplayers")
	public ResponseEntity<List<Players>> getNoPlayers() {
		List<Players> players = new ArrayList<Players>();
		playerRepository.findNoPlayers().forEach(players::add);
		players = players.stream().filter(p -> p.getSports().size() == 0).toList();
		if (players.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(players, HttpStatus.OK);
	}

	// Delete by id
	@DeleteMapping("/player/{id}")
	public ResponseEntity<HttpStatus> deletePlayer(@PathVariable("id") long id) {
		playerRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	// Save player
	@PostMapping("/player")
	public ResponseEntity<HttpStatus> savePlayer(@RequestBody Players player) {
		if (player != null) {
			playerRepository.save(player);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	// update player by id
	@PutMapping("/player/{id}")
	public ResponseEntity<Players> updatePlayers(@RequestBody Players players, @PathVariable("id") Long id) {
		Players playerObj = playerRepository.findById(id).get();
		playerObj.setEmail(players.getEmail());
		playerObj.setAge(players.getAge());
		playerObj.setGender(players.getGender());
		playerObj.setLevel(players.getLevel());
		playerObj.setSports(players.getSports());
		Players savedPlayer = playerRepository.save(playerObj);
		return new ResponseEntity<>(savedPlayer, HttpStatus.OK);
	}
	
	@GetMapping("/players/pagination")
	  public ResponseEntity<Map<String, Object>> getAllPlayers(
	        @RequestParam(required = false) String email,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "3") int size
	      ) {

	    try {
	      List<Players> players = new ArrayList<Players>();
	      Pageable paging = PageRequest.of(page, size);
	      
	      Page<Players> pageTuts;
	      if (email == null)
	        pageTuts = playerRepository.findAll(paging);
	      else
	        pageTuts = playerRepository.findByEmailContaining(email, paging);

	      players = pageTuts.getContent();

	      Map<String, Object> response = new HashMap<>();
	      response.put("players", players);
	      response.put("currentPage", pageTuts.getNumber());
	      response.put("totalItems", pageTuts.getTotalElements());
	      response.put("totalPages", pageTuts.getTotalPages());

	      return new ResponseEntity<>(response, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
}
