package bomber.connectionhandler.json;

public class HandlerInputJson {

    private String topic;
    private Data data;

    public HandlerInputJson() {
    }

    public HandlerInputJson(String topic, Data data) {
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


