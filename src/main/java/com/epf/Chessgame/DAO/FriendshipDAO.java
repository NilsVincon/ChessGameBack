package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.Friendship;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendshipDAO extends CrudRepository<Friendship,Long> {
}
