package bomber.connectionhandler;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;

public class HandlerJson {

    private String topic;
    private Data data;

    public HandlerJson() {
    }

    public HandlerJson(String topic, Data data) {
        this.topic = topic;
        this.data = data;
    }

    public String getTopic() {
        return topic;
    }

    public Data getData() {
        return data;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setData(Data data) {
        this.data = data;
    }
}


