package bomber.matchmaker.service;


import bomber.matchmaker.connection.Connection;
import bomber.matchmaker.dao.GameSessionDao;
import bomber.matchmaker.model.GameSession;
import org.jetbrains.annotations.NotNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Queue;

@Service
public class BomberService {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(BomberService.class);

    @Autowired
    private GameSessionDao gameSessionDao;


    @Transactional
    public void addTodb(@NotNull Integer gameId, @NotNull Queue<? extends Connection> queue, @NotNull Date date) {
        GameSession gameSession = new GameSession(queue);
        gameSession.setGameId(gameId);
        gameSession.setTime(date);
        gameSessionDao.save(gameSession);
        log.info("Added a new line to DataBase: {}", gameSession);
    }
}
