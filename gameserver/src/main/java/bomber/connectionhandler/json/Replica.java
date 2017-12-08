package bomber.connectionhandler.json;


import com.fasterxml.jackson.annotation.JsonGetter;

import java.net.URI;

public class Replica {

    public static URI txt;
    private Topic topic;
    private DataReplica data = new DataReplica();

    public Replica() {
    }

    public Topic getTopic() {
        return topic;
    }

    @JsonGetter("data")
    public DataReplica getData() {
        return data;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public void setData(DataReplica data) {
        this.data = data;
    }


}


