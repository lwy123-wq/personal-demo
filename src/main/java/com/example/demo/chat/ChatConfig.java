package com.example.demo.chat;

import io.netty.channel.Channel;

import java.util.concurrent.ConcurrentHashMap;

public class ChatConfig {
    public static ConcurrentHashMap<String, Channel> concurrentHashMap = new ConcurrentHashMap<String, Channel>();
    public static ConcurrentHashMap<String, Channel> name = new ConcurrentHashMap<String, Channel>();
    public static ConcurrentHashMap<Channel,String> name1 = new ConcurrentHashMap<Channel,String>();
}
