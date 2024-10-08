package com.epf.Chessgame.Service;

import com.epf.Chessgame.DAO.GameDAO;
import com.epf.Chessgame.DAO.UserDAO;
import com.epf.Chessgame.Model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {
    private final GameDAO gameDAO;

    @Autowired
    private UserService userService;

    public GameService(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    public Iterable<Game> getGames() {
        return gameDAO.findAll();
    }

    public Game getGame(Long id) {
        return gameDAO.findById(id).orElse(null);
    }

    public Game createGame(Game game) {
        return gameDAO.save(game);
    }

    public Game updateGame(Game game) {
        return gameDAO.save(game);
    }

    public void deleteGame(Long id) {
        gameDAO.deleteById(id);
    }

    public void addWinner(Long id, Long id_winner) {
        Game game = gameDAO.findById(id).orElse(null);
        game.setWinner(userService.getUser(id_winner));
        gameDAO.save(game);
    }
}
