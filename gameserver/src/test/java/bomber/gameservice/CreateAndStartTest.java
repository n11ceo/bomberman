package bomber.gameservice;


import bomber.games.gamesession.GameSession;
import bomber.games.util.GeneratorIdSession;
import bomber.gameservice.controller.GameController;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static bomber.gameservice.controller.GameController.gameSessionMap;
import static org.junit.Assert.*;


public class CreateAndStartTest {

    GameController gameController;

    @Before
    public void init() {
        gameController = new GameController();
    }

    @Test
    public void create() {
        gameController.create("4");
        long gameId = gameController.add();
        assertEquals(GeneratorIdSession.getIdGenerator(), gameId);
    }


}
