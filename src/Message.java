import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
    private static long counter = 0;
    private boolean isRead;
    private String receiver;
    private String sender;
    private String body;
    private final long messageID;

    public Message(String sender,String receiver, String body) {
        synchronized (this) {
            messageID = counter;
            counter++;
        }

        this.isRead = false;
        this.receiver = receiver;
        this.sender = sender;
        this.body = body;
    }

    public boolean isRead() {
        return isRead;
    }

    public void readMessage(boolean read) {
        isRead = true;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getMessageID() {
        return messageID;
    }

    @Override
    public String toString() {
        String message = messageID + ". from: " + sender;
        if (isRead) {
            return message + "*";
        }
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message message)) return false;
        return getMessageID() == message.getMessageID();
    }
}
