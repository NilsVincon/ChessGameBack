package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.Move;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoveDAO extends CrudRepository<Move,Long> {
}
