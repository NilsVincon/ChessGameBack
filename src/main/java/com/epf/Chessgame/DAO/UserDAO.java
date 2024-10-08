package com.epf.Chessgame.DAO;

import com.epf.Chessgame.Model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends CrudRepository<User,Long> {
}
