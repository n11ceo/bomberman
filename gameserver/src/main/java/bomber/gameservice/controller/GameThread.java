package bomber.gameservice.controller;


import bomber.connectionhandler.EventHandler;
import bomber.connectionhandler.PlayerAction;
import bomber.connectionhandler.json.Json;
import bomber.games.gameobject.Player;
import bomber.games.gamesession.GameSession;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.management.PlatformLoggingMXBean;

import static bomber.gameservice.controller.GameController.gameSessionMap;

public class GameThread implements Runnable {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameThread.class);
    private final long gameId;


    public GameThread(final long gameId) {
        this.gameId = gameId;
    }

    @Override
    public void run() {
        log.info("Start new thread called game-mechanics with gameId = " + gameId);
        GameSession gameSession = new GameSession((int) gameId);
        log.info("Game has been init gameId={}", gameId);
        gameSession.setupGameMap();
        gameSessionMap.put(gameId, gameSession);
        while (!Thread.currentThread().isInterrupted() || !gameSession.isGameOver()) {
            log.info("========================================");
            log.info(Json.replicaToJson(gameSession.getReplica()));
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                EventHandler.sendReplica(gameSession.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!gameSession.getInputQueue().isEmpty()) {
                gameSession.getGameMechanics().readInputQueue(gameSession.getInputQueue());
                gameSession.getGameMechanics().doMechanic(gameSession.getReplica());
            }
        }
    }

}