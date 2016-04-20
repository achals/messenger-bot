package com.achals.messenger.bot.model;

/**
 * Created by achalshah on 4/19/16.
 */
public class MessageResponse
{
    public long recipient;
    public MessageData message;

    public static class MessageData
    {
        public String text;
    }

}
