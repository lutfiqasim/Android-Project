package edu.birzeit.chatproject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessageModel {
    private String messageId;
    private String senderId;
    private String messageText;


    private String timestamp;

    public MessageModel() {

    }

    public MessageModel(String messageId, String senderId, String messageText) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.messageText = messageText;
        this.timestamp = getTimeDate();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }


    public static String getTimeDate() {
        try {
            Date netDate = new Date();
            SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.getDefault());
            return sfd.format(netDate);
        } catch (Exception e) {
            return "date";
        }
    }


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp() {
        this.timestamp = getTimeDate();
    }
}
