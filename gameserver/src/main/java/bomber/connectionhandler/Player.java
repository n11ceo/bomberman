package bomber.connectionhandler;

public class Player {
    private long gameid = 0;
    private long id = 0; //is id needed?
    private String name = null;

    public Player(long gameid, long id, String name) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
