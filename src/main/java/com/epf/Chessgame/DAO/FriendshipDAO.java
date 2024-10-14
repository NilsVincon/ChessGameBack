package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.Friendship;
import com.epf.Chessgame.Model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendshipDAO extends CrudRepository<Friendship,Long> {

    @Query("SELECT CASE WHEN COUNT(f) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Friendship f WHERE (f.friend1 = :user1 AND f.friend2 = :user2) " +
            "OR (f.friend1 = :user2 AND f.friend2 = :user1)")
    boolean areFriends(@Param("user1") User user1, @Param("user2") User user2);

    Optional<Friendship> findByFriend1AndFriend2(User friend1, User friend2);
}
