package edu.stevens.cs522.chat.oneway.server.entities;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.regex.Pattern;

import edu.stevens.cs522.chat.oneway.server.contracts.MessageContract;

/**
 * Created by Rafael on 2/22/2016.
 */
public class Message implements Parcelable {

    private static final char SEPARATOR_CHAR = '#';
    private static final Pattern SEPARATOR =
            Pattern.compile(Character.toString(SEPARATOR_CHAR), Pattern.LITERAL);

    protected long id;
    protected long sequentialNumber;
    protected String messageText;
    protected long timestamp;
    protected long peerId;
    protected String sender;

    public Message() {
    }

    public Message(String receivedMessage) {
        String[] splitted = SEPARATOR.split(receivedMessage);
        if(splitted.length > 1) {
            this.sender = splitted[0];
            this.messageText = splitted[1].trim();
            this.timestamp = System.currentTimeMillis();
        }
    }

    public Message(long sequentialNumber, String messageText, String sender) {
//        this.id = id;
        this.sequentialNumber = sequentialNumber;
        this.messageText = messageText;
        this.sender = sender;
        this.timestamp = System.currentTimeMillis();
    }

    public Message(long sequentialNumber, String messageText, String sender, long peerId) {
//        this.id = id;
        this.sequentialNumber = sequentialNumber;
        this.messageText = messageText;
        this.sender = sender;
        this.peerId = peerId;
        this.timestamp = System.currentTimeMillis();
    }

    public Message(Cursor in){
        id = MessageContract.getId(in);
        sequentialNumber = MessageContract.getSequentialNumber(in);
        messageText = MessageContract.getMessage(in);
        timestamp = MessageContract.getTimestamp(in);
        sender = MessageContract.getSender(in);
        peerId = MessageContract.getPeerId(in);
    }

    protected Message(Parcel in) {
        id = in.readLong();
        sequentialNumber = in.readLong();
        messageText = in.readString();
        timestamp = in.readLong();
        peerId = in.readLong();
        sender = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {dest.writeLong(id);
        dest.writeLong(sequentialNumber);
        dest.writeString(messageText);
        dest.writeLong(timestamp);
        dest.writeLong(peerId);
        dest.writeString(sender);
    }

    public void writeToProvider(ContentValues values) {
//        MessageContract.putId(values, id);
        MessageContract.putSequentialNumber(values, sequentialNumber);
        MessageContract.putMessage(values, messageText);
        MessageContract.putTimestamp(values, timestamp);
        MessageContract.putPeerId(values, peerId);
    }

    @Override
    public String toString() {
        return sender + "#" + messageText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSequentialNumber() {
        return sequentialNumber;
    }

    public void setSequentialNumber(long sequential_number) {
        this.sequentialNumber = sequential_number;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public long getPeerId() {
        return peerId;
    }

    public void setPeerId(long peerId) {
        this.peerId = peerId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static Creator<Message> getCREATOR() {
        return CREATOR;
    }

}
