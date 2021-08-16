package com.example.demo.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;

public class ChatConfig {
    public static ConcurrentHashMap<String, Channel> concurrentHashMap = new ConcurrentHashMap<String, Channel>();
    public static ConcurrentHashMap<String, ChannelId> map = new ConcurrentHashMap<String, ChannelId>();
    public static ConcurrentHashMap<Integer, String> name = new ConcurrentHashMap<Integer,String>();
    public int num=0;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

