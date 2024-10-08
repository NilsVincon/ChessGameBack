package com.epf.Chessgame.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Play {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_game")
    private Game game;

    @OneToOne
    @JoinColumn(name = "user1")
    private User user1;

    @OneToOne
    @JoinColumn(name = "user2")
    private User user2;

}
