package bomber.connectionhandler;

public class Player {
    private long gameid = 0;
    private int id = 0; //is id needed?
    private String name = null;

    public Player(long gameid, int id, String name) {
        this.gameid = gameid;
        this.id = id;
        this.name = name;
    }

    public Player() {

    }

    public long getGameid() {
        return gameid;
    }

    public void setGameid(long gameid) {
        this.gameid = gameid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
