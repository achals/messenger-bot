package com.achals.messenger.bot.model;


/**
 * Created by achalshah on 4/17/16.
 */

public class Message
{
    private final String text;

    public Message(String text)
    {
        this.text = text;
    }

    public String getText()
    {
        return text;
    }
}
