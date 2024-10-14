package com.epf.Chessgame.Model;

import com.epf.Chessgame.Enum.InvitationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
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
    @JoinColumn(name = "sender")
    private User sender;

    @OneToOne
    @JoinColumn(name = "receiver")
    private User receiver;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    public Play(Game game, User sender, User receiver) {
        this.game = game;
        this.sender = sender;
        this.receiver = receiver;
        this.status = InvitationStatus.EN_ATTENTE;
    }
}
