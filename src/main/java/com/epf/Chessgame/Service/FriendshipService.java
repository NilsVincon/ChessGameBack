package com.epf.Chessgame.Service;

import com.epf.Chessgame.DAO.FriendshipDAO;
import com.epf.Chessgame.Model.Friendship;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {

    private final FriendshipDAO friendshipDAO;

    public FriendshipService(FriendshipDAO friendshipDAO) {
        this.friendshipDAO = friendshipDAO;
    }


    public Iterable<Friendship> getFriendships() {
        return friendshipDAO.findAll();
    }

    public Friendship getFriendship(Long id) {
        return friendshipDAO.findById(id).orElse(null);
    }

    public void createFriendship(Friendship friendship) {
        friendshipDAO.save(friendship);
    }

    public Friendship updateFriendship(Friendship friendship) {
        return friendshipDAO.save(friendship);
    }

    public void deleteFriendship(Long id) {
        friendshipDAO.deleteById(id);
    }

}
