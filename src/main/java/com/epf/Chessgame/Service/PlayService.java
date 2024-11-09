package com.epf.Chessgame.Service;

import com.epf.Chessgame.DAO.PlayDAO;
import com.epf.Chessgame.Model.Play;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayService {
    private final PlayDAO playDAO;

    public PlayService(PlayDAO playDAO) {
        this.playDAO = playDAO;
    }

    public Iterable<Play> getPlays() {
        return playDAO.findAll();
    }
    public List<Play> getPlaysBySender(Long senderId) {
        return playDAO.findBySenderId(senderId);
    }

    public List<Play> getPlaysByReceiver(Long receiverId) {
        return playDAO.findByReceiverId(receiverId);
    }

    public Play getPlay(Long id) {
        return playDAO.findById(id).orElse(null);
    }

    public void createPlay(Play play) {
        playDAO.save(play);
    }

    public Play updatePlay(Play play) {
        return playDAO.save(play);
    }
    public void deletePlay(Long id) {
        playDAO.deleteById(id);
    }

    public Play findPlayByGameId(Long gameId) {
        return playDAO.findPlayByGameId(gameId);
    }
}


