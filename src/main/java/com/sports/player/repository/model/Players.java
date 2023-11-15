package com.sports.player.repository.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "players")
public class Players {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "email")
  private String email;

  @Column(name = "age")
  private Integer age;
  
  @Column(name = "level")
  private Integer level;

  @Column(name = "gender")
  private String gender;

  @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(name = "players_sports", joinColumns = { @JoinColumn(name = "players_id") }, inverseJoinColumns = {
      @JoinColumn(name = "sports_id")})
  private Set<Sports> sports = new HashSet<>();

  public Players() {

  }

  public Players(String email, Integer level, Integer age, String gender ) {
    this.email = email;
    this.age = age;
    this.level = level;
    this.gender = gender;
  }

  public void setId(Long id) {
	    this.id = id;
  }
  public long getId() {
    return id;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getLevel() {
	    return level;
	}

  public void setLevel(Integer level) {
	    this.level = level;
	}
	  
  public Set<Sports> getSports() {
    return sports;
  }

  public void setSports(Set<Sports> sports) {
    this.sports = sports;
  }

  public void addSports(Sports sports) {
    this.sports.add(sports);
    sports.getPlayers().add(this);
  }

  public void removeSports(long sportsId) {
	  Sports sports = this.sports.stream().filter(t -> t.getId() == sportsId).findFirst().orElse(null);
    if (sports != null) {
      this.sports.remove(sports);
      sports.getPlayers().remove(this);
    }
  }

  @Override
  public String toString() {
    return "Player [id=" + id + ", age=" + age + ", gender=" + gender + ", age=" + age + ", level=" +level+"]";
  }

}
