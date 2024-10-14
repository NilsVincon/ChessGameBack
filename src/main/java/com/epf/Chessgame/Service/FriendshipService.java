package com.epf.Chessgame.Service;

import com.epf.Chessgame.DAO.FriendshipDAO;
import com.epf.Chessgame.Model.Friendship;
import com.epf.Chessgame.Model.User;
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

    public void deleteFriendship(Long id) {friendshipDAO.deleteById(id);}

    public boolean areFriends(User user1, User user2) {
        return friendshipDAO.areFriends(user1, user2);
    }

    public Friendship findFriendshipByUser(User friend1, User friend2) {
        return friendshipDAO.findByFriend1AndFriend2(friend1, friend2).orElse(null);
    }

}
