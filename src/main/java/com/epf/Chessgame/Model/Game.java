package com.epf.Chessgame.Model;


import com.epf.Chessgame.Enum.GameStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Entity
@Table
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date game_date;
    private Time game_time;
    @Enumerated(EnumType.STRING)
    private GameStatus status;
    @OneToOne
    @JoinColumn(name = "winner")
    private User winner;

    public  Game(){
        this.status = GameStatus.A_VENIR;
    }

    public void startGame(){
        this.status = GameStatus.EN_COURS;
        this.game_date = new Date();
        this.game_time = new Time(System.currentTimeMillis());
    }

    public void endGame(User winner){
        this.status = GameStatus.TERMINEE;
        this.winner = winner;
    }

    @Override
    public String toString() {
        return "Game{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }
}
