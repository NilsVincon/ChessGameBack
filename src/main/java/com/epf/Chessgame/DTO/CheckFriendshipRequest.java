package com.epf.Chessgame.DTO;

import com.epf.Chessgame.Model.User;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CheckFriendshipRequest {
    private User user1;
    private User user2;

    public CheckFriendshipRequest() {
    }

}