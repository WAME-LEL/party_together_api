package com.party.partytogether.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


//게임 도메인
@Entity
@Getter
@Setter
public class Game {
    @Id
    @GeneratedValue
    @Column(name = "game_id")
    private Long id;

    private String title;


    //==생성 메서드==//
    public static Game createGame(String title){
        Game game = new Game();
        game.setTitle(title);

        return game;
    }
}
