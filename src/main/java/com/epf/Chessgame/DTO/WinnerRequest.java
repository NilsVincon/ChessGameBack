package com.epf.Chessgame.DTO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class WinnerRequest {
    private Long gameId;
    private Long winnerId;

    public WinnerRequest() {
    }

}

