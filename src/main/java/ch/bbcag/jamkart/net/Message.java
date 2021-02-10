package ch.bbcag.jamkart.net;

import java.util.HashMap;
import java.util.Map;

public class Message {
    private Map<String, String> data = new HashMap<>();
    public static final String TYPE = "type";

    public Message() {}

    public Message(MessageType messageType) {
        addValue(TYPE, messageType);
    }

    public Map<String, String> getData() {
        return data;
    }

    public Object getValue(String key) {
        return data.get(key);
    }

    public void addValue(String key, Object value) {
        data.put(key, value.toString());
    }

    public String getStringFromMap() {
        StringBuilder dataString = new StringBuilder();
        for(Map.Entry<String, String> entry : getData().entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            dataString.append(key).append(":").append(value).append("\n");
        }
        return dataString.toString();
    }

    public void setMapFromString(String dataString) {
        data.clear();

        String[] entries = dataString.split("\n");
        for(String entry : entries) {
            String[] keyValue = entry.split(":");
            String key = keyValue[0];
            String value = keyValue[1];

            data.put(key, value);
        }
    }

    public MessageType getMessageType() {
        return MessageType.valueOf(data.get(TYPE));
    }

    @Override
    public String toString() {
        return getStringFromMap();
    }
}
