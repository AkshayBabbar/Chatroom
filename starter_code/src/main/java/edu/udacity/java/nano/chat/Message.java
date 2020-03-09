package edu.udacity.java.nano.chat;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * WebSocket message model
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Message",
        "UserName",
        "OnlineCount",
        "Type"
})
public class Message {
    // TODO: add message model.

    @JsonProperty("Message")
    private String message;
    @JsonProperty("UserName")
    private String userName;
    @JsonProperty("OnlineCount")
    private Integer onlineCount;
    @JsonProperty("Type")
    private MessageType type;
    @JsonIgnore

    @JsonProperty("Message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("Message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("UserName")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("UserName")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty("OnlineCount")
    public Integer getOnlineCount() {
        return onlineCount;
    }

    @JsonProperty("OnlineCount")
    public void setOnlineCount(Integer onlineCount) {
        this.onlineCount = onlineCount;
    }

    @JsonProperty("Type")
    public MessageType getType() {
        return type;
    }

    @JsonProperty("Type")
    public void setType(MessageType type) {
        this.type = type;
    }

    public Message(String message, String userName) {
        this.message = message;
        this.userName = userName;
        this.onlineCount = onlineCount;
        this.type = type;
    }
    public enum MessageType {
        ENTER, LEAVE, CHAT
    }


}