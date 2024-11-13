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

    @Column(name = "sendercolor")
    private String sendercolor;

    @Column(name = "receivercolor")
    private String receivercolor;

    public Play(Game game, User sender, User receiver, String senderColor, String receiverColor) {
        this.game = game;
        this.sender = sender;
        this.receiver = receiver;
        this.status = InvitationStatus.EN_ATTENTE;
        this.sendercolor = senderColor;
        this.receivercolor = receiverColor;
    }

    public Play(Game game, User sender, User receiver) {
        this.game = game;
        this.sender = sender;
        this.receiver = receiver;
        this.status = InvitationStatus.EN_ATTENTE;
    }

    @Override
    public String toString() {
        return "Play" +
                "id=" + id +
                ", game=" + game +
                ", sender=" + sender +
                ", receiver=" + receiver +
                ", status=" + status +
                '}';
    }
}
