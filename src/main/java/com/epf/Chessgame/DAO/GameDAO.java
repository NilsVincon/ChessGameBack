package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDAO extends CrudRepository<Game,Long> {
}
