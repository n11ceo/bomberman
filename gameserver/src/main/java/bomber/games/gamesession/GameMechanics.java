package bomber.games.gamesession;

import bomber.connectionhandler.EventHandler;
import bomber.connectionhandler.PlayerAction;
import bomber.games.gameobject.*;
import bomber.games.geometry.Point;
import bomber.games.model.GameObject;
import bomber.games.model.Movable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class GameMechanics {

    private static final Logger log = LoggerFactory.getLogger(GameMechanics.class);
    private Map<Integer, PlayerAction> actionOnMap = new HashMap<>();


    private final int gameZone_X = 17;//0,16 - стенки по X
    private final int gameZone_Y = 13; //0,12 - стенки по Y
    public int playersCount = 4;//Число игроков
    private final int brickSize = 32;//в будущем, когда будет накладываться на это дело фронтенд, это пригодится
    private final int bonusCount = 4;//3*Количество бонусов, которые отспаунятся
    private final List<Integer> listPlayerId;

    public GameMechanics() {
        this.listPlayerId = new ArrayList<>(EventHandler.getSessionIdList());
    }

    public void setupGame(Map<Integer, GameObject> replica, AtomicInteger idGenerator) { //VOID, map instance already exists, no args gameMech is in session

        /*for (int x = 0; x <= gameZone_X; x++) {
            for (int y = 0; y <= gameZone_Y; y++) {
                if (y == 0 || x == 0 || x*brickSize == (gameZone_X*brickSize - brickSize) || y*brickSize == (gameZone_Y*brickSize - brickSize)) {
                    idGenerator.getAndIncrement();
                    replica.put(idGenerator.get(), new Wall(idGenerator.get(), new Point(x*brickSize, y*brickSize)));//Первый игрок
                }
                if (!(y == 0 || x == 0 || x*brickSize == (gameZone_X*brickSize - brickSize) ||
                        y*brickSize == (gameZone_Y*brickSize - brickSize)) && ((x % 2 == 0) && (y % 2 == 0))) {
                    idGenerator.getAndIncrement();
                    replica.put(idGenerator.get(), new Wall(idGenerator.get(), new Point(x*brickSize, y*brickSize)));//Первый игрок
                }
            }
        }*/


        replica.put(listPlayerId.get(0), new Player(listPlayerId.get(0), new Point(brickSize, brickSize)));//Первый игрок
        replica.put(listPlayerId.get(1), new Player(listPlayerId.get(1), new Point(gameZone_X * brickSize - brickSize * 2, brickSize)));
        replica.put(listPlayerId.get(2), new Player(listPlayerId.get(2), new Point(brickSize, gameZone_Y * brickSize - brickSize * 2)));
        replica.put(listPlayerId.get(3), new Player(listPlayerId.get(3), new Point(gameZone_X * brickSize - brickSize * 2, gameZone_Y * brickSize - brickSize * 2)));
        try {
            EventHandler.sendPossess(listPlayerId.get(0));
            EventHandler.sendPossess(listPlayerId.get(1));
            EventHandler.sendPossess(listPlayerId.get(2));
            EventHandler.sendPossess(listPlayerId.get(3));
        } catch (IOException e) {
            log.error("We are unable to sendPosses");
        }



        /*idGenerator.getAndIncrement();
        //Второй игрок
        idGenerator.getAndIncrement();
        //Третий игрок
        idGenerator.getAndIncrement();
        //Четвертый игрок*/

    }

    public void readInputQueue(ConcurrentLinkedQueue<PlayerAction> inputQueue) {

        while (!inputQueue.isEmpty()) { //делать до тех пор пока очередь не опустеет
            Integer playerId = inputQueue.element().getId(); //заранее узнаем id игрока, возглавляющего очередь
            if (!actionOnMap.containsKey(playerId)) { //если действий от этого игрока еще не было
                actionOnMap.put(playerId, inputQueue.element());//Запишем действие в мапу
            }
            log.info("Здесь будет actionOnMap");
            log.info(actionOnMap.toString());
            inputQueue.remove();//удаляем главу этой очереди
        }
    }

    public void clearInputQueue(ConcurrentLinkedQueue<PlayerAction> inputQueue) {
        inputQueue.clear();
    }


/*
    public void doMechanic(Map<Integer, GameObject> replica, ConcurrentLinkedQueue<PlayerAction> inputQueue ) {
        readInputQueue(inputQueue);

        log.info("---------------------------");
        log.info(replica.toString());
        log.info("===========================");
        for (PlayerAction playerAction : actionOnMap.values()) {
            log.info("queue = {}", playerAction);
        }

    }
*/


    public Map<Integer, GameObject> doMechanic(Map<Integer, GameObject> replica) {

        for (GameObject gameObject : replica.values()) {
            MechanicsSubroutines mechanicsSubroutines = new MechanicsSubroutines();//подняли вспомогательные методы
            if (gameObject instanceof Player) {
                Player currentPlayer = ((Player) gameObject);
                if (actionOnMap.containsKey(currentPlayer.getId())) {
                    log.info("currentPlayerId = " + currentPlayer.getId());
                    switch (actionOnMap.get(currentPlayer.getId()).getType()) { //либо шагает Up,Down,Right,Left, либо ставит бомбу Bomb

                        case UP: //если идет вверх
                            currentPlayer.setPosition(((Player) gameObject).move(Movable.Direction.UP));//задали новые координаты
                        /*if (mechanicsSubroutines.collisionCheck(gameObject, replica)) {//Если никуда не врезается, то
                            replica.replace(gameObject.getId(), currentPlayer);//перемещаем игрока
                        }*/
                            //Если проверку не прошла, то все остается по старому
                            break;

                        case DOWN:
                            currentPlayer.setPosition(((Player) gameObject).move(Movable.Direction.DOWN));//задали новые координаты
                        /*if (mechanicsSubroutines.collisionCheck(gameObject, replica)) {//Если никуда не врезается, то
                            replica.replace(gameObject.getId(), currentPlayer);//перемещаем игрока
                        }*/
                            //Если проверку не прошла, то все остается по старому

                            break;
                        case LEFT:
                            currentPlayer.setPosition(((Player) gameObject).move(Movable.Direction.LEFT));//задали новые координаты
                        /*if (mechanicsSubroutines.collisionCheck(gameObject, replica)) {//Если никуда не врезается, то
                            replica.replace(gameObject.getId(), currentPlayer);//перемещаем игрока
                        }*/
                            //Если проверку не прошла, то все остается по старому

                            break;
                        case RIGHT:
                            currentPlayer.setPosition(((Player) gameObject).move(Movable.Direction.RIGHT));//задали новые координаты
                       /* if (mechanicsSubroutines.collisionCheck(gameObject, replica)) {//Если никуда не врезается, то
                            replica.replace(gameObject.getId(), currentPlayer);//перемещаем игрока
                        }*/
                            //Если проверку не прошла, то все остается по старому

                            break;
                    /*case BOMB:
                        replica.put(idGenerator.getAndIncrement(), new Bomb(idGenerator.get(),
                                currentPlayer.getPosition(), currentPlayer.getBombPower()));*/

                        default:
                            break;
                    }
                }
                /*if (!(mechanicsSubroutines.bonusCheck(currentPlayer, replica) == null)) { //если был взят бонус
                    Bonus getBonus = (Bonus) replica.get(mechanicsSubroutines.bonusCheck(currentPlayer, replica));
                    switch (getBonus.getType()) { //Узнаем что это за бонус

                        case BONUS_BOMB:
                            currentPlayer.setMaxBombs(currentPlayer.getMaxBombs() + 1);
                            break;

                        case BONUS_RANGE:
                            currentPlayer.setBombPower(currentPlayer.getBombPower() + 1);
                            break;
                        case BONUS_SPEED:
                            currentPlayer.setVelocity(currentPlayer.getVelocity() + 1); //вот тут конечно надо бы оптимизировать
                            break;
                        default:
                            break;
                    }

                }*/
            }

            /*if (gameObject instanceof Bomb) { //начинаем работать с бомбами
                if (!(((Bomb) gameObject).getLifeTime() == 0)) { //если эта бомба еще не взорвалась
                    ((Bomb) gameObject).decrementLifeTime(); //отнимем время до взрыва
                } else { //если взорвалась то

                    //Слооожнаа
                    boolean up = true;
                    boolean down = true;
                    boolean left = true;
                    boolean right = true;
                    //это все индикаторы, отслеживающие был ли уже взрыв объекта по одной их 4х сторон взрыва, чего то более изящного не придумал

                    //Начальные координаты (он же эпицентр взрыва)
                    int epicenterX = gameObject.getPosition().getX();
                    int epicenterY = gameObject.getPosition().getY();


                    for (GameObject boxObject : replica.values()) { //пройдем по реплике в поисках жертв
                        if ((boxObject instanceof Box) | (boxObject instanceof Wall)) { //упростим прогон, пробежавшись только по коробкам
                            for (int i = 1; i <= ((Bomb) gameObject).getBombPower(); i++) { //надо узнать силу взрыва
                                for (int j = 1; j <= 4; j++) { //взрыв на все 4 стороны
                                    switch (j) {
                                        case 1: //если идет вверх
                                            if (up) {
                                                Point currentPoint = new Point(epicenterX, epicenterY + i * brickSize);//сместились вверх от эпицентра, в зависимости от глубины взрыва
                                                if (mechanicsSubroutines.createExplosions(currentPoint, gameObject, replica)) {
                                                    replica.put(idGenerator.getAndIncrement(), new Explosion(idGenerator.get(), currentPoint)); //место не занято, отрисуем взрыв
                                                } else {
                                                    up = false; //взрыв был потрачен либо в стену, либо в коробку, дальше взрыв не пойдет
                                                }
                                            }
                                            break;
                                        case 2://если идет вниз
                                            if (down) {
                                                Point currentPoint = new Point(epicenterX, epicenterY - i * brickSize);
                                                if (mechanicsSubroutines.createExplosions(currentPoint, gameObject, replica)) {
                                                    replica.put(idGenerator.getAndIncrement(), new Explosion(idGenerator.get(), currentPoint));
                                                } else {
                                                    down = false;
                                                }
                                            }
                                            break;
                                        case 3://если идет влево
                                            if (left) {
                                                Point currentPoint = new Point(epicenterX - i * brickSize, epicenterY);
                                                if (mechanicsSubroutines.createExplosions(currentPoint, gameObject, replica)) {
                                                    replica.put(idGenerator.getAndIncrement(), new Explosion(idGenerator.get(), currentPoint));
                                                } else {
                                                    left = false;
                                                }
                                            }
                                            break;
                                        case 4://если идет вправо
                                            if (right) {
                                                Point currentPoint = new Point(epicenterX + i * brickSize, epicenterY);
                                                if (mechanicsSubroutines.createExplosions(currentPoint, gameObject, replica)) {
                                                    replica.put(idGenerator.getAndIncrement(), new Explosion(idGenerator.get(), currentPoint));
                                                } else {
                                                    right = false;
                                                }
                                            }
                                            break;
                                        default:
                                            break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            replica.remove(gameObject.getId()); //Удалим бомбу из replica
            if (gameObject instanceof Explosion) { //Найдем тех, кого в итоге убило ^__^ ну и протикаем продолжительность взрыва
                if (((Explosion) gameObject).getLifeTime() == 0) { //если время закончилось
                    replica.remove(gameObject.getId());//удалим его
                } else { //если взрыв еще держится
                    ((Explosion) gameObject).decrementLifeTime(); //отнимем время до взрыва


                    for (GameObject playerObject : replica.values()) { //Далее охотимся на игроков
                        if (playerObject instanceof Player) {
                            mechanicsSubroutines.youDied(replica, ((Player) playerObject), ((Explosion) gameObject));
                        }
                    }
                }
            }*/
        }
        return replica;
    }
}