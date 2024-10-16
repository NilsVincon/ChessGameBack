package com.epf.Chessgame.Model;

import com.epf.Chessgame.Model.pieces.Position;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table
public class Move {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "row", column = @Column(name = "initial_row")),
            @AttributeOverride(name = "column", column = @Column(name = "initial_column"))
    })
    private Position initialPosition;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "row", column = @Column(name = "final_row")),
            @AttributeOverride(name = "column", column = @Column(name = "final_column"))
    })
    private Position finalPosition;

    @Override
    public String toString() {
        return "Move{" +
                "initialPosition=" + initialPosition +
                ", finalPosition=" + finalPosition +
                '}';
    }
}
