package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.Play;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayDAO extends CrudRepository<Play,Long> {
    Play findPlayByGameId(Long gameId);
}
