package edu.stevens.cs522.chat.oneway.server.contracts;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import edu.stevens.cs522.chat.oneway.server.entities.Message;
import edu.stevens.cs522.chat.oneway.server.managers.IEntityCreator;

/**
 * Created by Rafael on 2/22/2016.
 */
public class MessageContract {
    public static final String APP_NAMESPACE = "edu.stevens.cs522.bookstore";
    public static final String SCHEME = "content";
    public static final String AUTHORITY = APP_NAMESPACE;
    public static final String CONTENT = "message";
    public static final int CURSOR_LOADER_ID = 1;

    public static final Uri CONTENT_URI = new Uri.Builder()
            .scheme(SCHEME)
            .authority(AUTHORITY)
            .path(CONTENT + "s")
            .build();
    public static final String CONTENT_TYPE = "vnd.android.cursor/vnd."
            + APP_NAMESPACE + "."
            + CONTENT + "s";
    public static final String CONTENT_TYPE_ITEM = "vnd.android.cursor.item/vnd."
            + APP_NAMESPACE + "."
            + CONTENT;

    public static final String TABLE_NAME = CONTENT + "s";
    public static final String ID = "_id";
    public static final String ID_FULL = TABLE_NAME + "." + ID;
    public static final String MESSAGE_TEXT = "message_text";
    public static final String PEER_ID = "peer_fk";
    public static final String PEER_ID_FULL =  TABLE_NAME + "." + "peer_fk";
    public static final String SENDER = "sender";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY," +
                    MESSAGE_TEXT + " TEXT NOT NULL," +
                    PEER_ID + " INTEGER NOT NULL," +
                    "FOREIGN KEY(" + PEER_ID + ") REFERENCES " + PeerContract.TABLE_NAME + "(_id) ON DELETE CASCADE" +
                    ");"
             + "CREATE INDEX MessagePeerIndex ON " + TABLE_NAME + "(" + PEER_ID + ");";
    public static final IEntityCreator<Message> DEFAULT_ENTITY_CREATOR = new IEntityCreator<Message>() {
        @Override
        public Message create(Cursor cursor) {
            return new Message(cursor);
        }
    };

    public static Uri withExtendedPath(Object path) {
        if (path != null) {
            String stringPath = String.valueOf(path);
            Uri.Builder builder = CONTENT_URI.buildUpon();
            if (!stringPath.isEmpty()) {
                builder.appendPath(stringPath);
            }
            return builder.build();
        } else {
            throw new IllegalArgumentException("Null argument path: " + path);
        }
    }

    public static long getId(Uri uri) {
        return Long.parseLong(uri.getLastPathSegment());
    }

    public static long getId(Cursor cursor) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(ID));
    }

    public static String getMessage(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(MESSAGE_TEXT));
    }

    public static void putMessage(ContentValues values, String message) {
        values.put(MESSAGE_TEXT, message);
    }

    public static long getPeerId(Cursor cursor) {
        return cursor.getLong(cursor.getColumnIndexOrThrow(PEER_ID));
    }

    public static void putPeerId(ContentValues values, long peerId) {
        values.put(PEER_ID, peerId);
    }

    public static String getSender(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndexOrThrow(SENDER));
    }

//    public static void putSender(ContentValues values, long peerId) {
//        values.put(PEER_ID, peerId);
//    }





}
