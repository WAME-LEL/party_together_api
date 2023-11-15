package com.party.partytogether.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue
    @Column(name = "games_id")
    private Long id;

    private String title;


    public static Game createGame(String title){
        Game game = new Game();
        game.setTitle(title);

        return game;
    }
}
