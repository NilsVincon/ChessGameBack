package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.Play;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayDAO extends CrudRepository<Play,Long> {
    Play findPlayByGameId(Long gameId);

    List<Play> findBySenderId(Long senderId);
    List<Play> findByReceiverId(Long receiverId);
}
