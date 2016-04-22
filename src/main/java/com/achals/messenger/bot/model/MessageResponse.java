package com.achals.messenger.bot.model;

/**
 * Created by achalshah on 4/19/16.
 */
public class MessageResponse {
    public Recipient recipient;
    public MessageData message;

    public static class Recipient {
        public long id;
    }

    public static class MessageData {
        public String text;
    }

}
