package bomber.connectionhandler;

import bomber.connectionhandler.json.HandleInputJson;
import bomber.connectionhandler.json.Replica;
import bomber.games.gameobject.Bomb;
import bomber.games.geometry.Point;
import bomber.games.model.GameObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class JsonTest {
    @Test
    public void possessTest() {
        String json = EventHandler.handlePossess(123);
        Assert.assertEquals(json, "{\"topic\":\"POSSESS\",\"data\":123}");
    }

    @Test
    public void replicaTest() {
        Replica replica = new Replica();
        List<Bomb> list = new ArrayList<>();
        list.add(new Bomb(1, new Point(10, 20)));
        list.add(new Bomb(2, new Point(1, 2)));
        list.add(new Bomb(3, new Point(1, 10)));
        String json = EventHandler.handleReplica(replica, list);
        System.out.println(json);

    }

    @Test
    public void handleInputJsonTest() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileReader fin = new FileReader("C:/Users/Arif/Desktop/Texnoatom/bomberman/Replica.txt");
        int c;
        String json = "";
        while ((c = fin.read()) != -1) {
            json += (char) c;
        }
        HandleInputJson handleInputJson = EventHandler.handleMoveAndPlanBomb(json);
        System.out.println(handleInputJson.getTopic());
        System.out.println(handleInputJson.getData().getDirection());
    }


}