package bomber.matchmaker.thread;


import bomber.matchmaker.connection.ConnectionQueue;
import bomber.matchmaker.controller.MmController;
import bomber.matchmaker.request.MmRequests;
import bomber.matchmaker.service.BomberService;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

import static bomber.matchmaker.controller.MmController.MAX_PLAYER_IN_GAME;
import static java.util.concurrent.TimeUnit.SECONDS;

public class StartThread extends Thread {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(StartThread.class);
    private Integer gameId;
    private boolean suspendFlag;
    static final int TIMEOUT = 30;
    static final int MAX_TIMEOUTS = 3;
    private boolean isStarted;
    private BomberService bomberService;


    public StartThread(Integer gameId, BomberService bomberService) {
        super("StartThread_gameId=" + gameId);
        suspendFlag = false;
        this.gameId = gameId;
        this.bomberService = bomberService;
        isStarted = false;
    }

    @Override
    public void run() {

        while (suspendFlag) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.info("Wait of thread={} interrupted", currentThread());
            }
        }
        int tryCounter = 0;
        while (tryCounter++ < MAX_TIMEOUTS
                && !isStarted) {
            try {
                if (Integer.parseInt(MmRequests.checkStatus().body().string()) == MAX_PLAYER_IN_GAME) {
                    /*bomberService.addToDb(gameId, new Date());*/
                    log.info("Sending a request to start the game, gameID = {}", gameId);
                    MmRequests.start(this.gameId.toString());
                    isStarted = true;
                } else {
                    log.info("Timeout for {} SECONDS, waiting for players to CONNECT. {} TIMEOUTS left",
                            TIMEOUT, MAX_TIMEOUTS - tryCounter);
                    sleep(SECONDS.toMillis(TIMEOUT));
                }
            } catch (IOException e) {
                e.printStackTrace(); // Саша напиши лог
            } catch (InterruptedException e) {
                log.error("Sleep of thread={} interrupted", currentThread());
            }
        }
        if (!isStarted)
            log.error("failed to start the game");
        MmController.clear();
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public synchronized void suspendThread() throws InterruptedException { // Саша, Удали эти метод
        suspendFlag = true;
    }

    public synchronized void resumeThread() throws InterruptedException {
        suspendFlag = false;
        notify();
    }
}