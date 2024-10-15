package com.epf.Chessgame.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Time;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "friend1")
    private User friend1;
    @OneToOne
    @JoinColumn(name = "friend2")
    private User friend2;

    public Friendship(User friend1, User friend2) {
        this.friend1 = friend1;
        this.friend2 = friend2;
    }
}
