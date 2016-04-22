package com.achals.messenger.bot.model;

import java.util.List;

/**
 * Created by achalshah on 4/19/16.
 */
public class MessagePost
{
    public String object;
    public List<Entry> entry;

    /**
     * Created by achalshah on 4/19/16.
     */
    public static class Entry
    {
        public long id;
        public long time;
        public List<Messaging> messaging;
    }

    public static class Messaging
    {
        public Sender sender;
        public Recipient recipient;
        public long timestamp;
        public Message message;
        public Delivery delivery;
    }

    public static class Sender
    {
        public long id;
    }

    public static class Recipient
    {
        public long id;
    }

    public static class Message
    {
        public String mid;
        public long seq;
        public String text;
    }

        public static class Delivery
    {
        public List<String> mids;
        public long watermark;
        public long seq;
    }
}
