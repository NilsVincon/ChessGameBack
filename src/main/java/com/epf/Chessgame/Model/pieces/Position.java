package com.epf.Chessgame.Model.pieces;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
@Setter
@Getter
@Embeddable
public class Position {
    @Column
    private int row;
    @Column
    private int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public Position() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position position)) return false;
        return getRow() == position.getRow() && getColumn() == position.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }
}
