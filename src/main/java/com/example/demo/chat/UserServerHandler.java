package com.example.demo.chat;

import com.example.demo.entity.User;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class UserServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChatConfig config=new ChatConfig();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        int i=config.getNum();
        int num=i+1;
        config.setNum(num);
        User message = new Gson().fromJson(msg.text(), User.class);
        String username=message.getUsername();
        ChatConfig.name.put(num,username);
        System.out.println(ChatConfig.name.get(num));
        System.out.println(num);
        //ChatConfig.name1.put(ctx.channel(),username);
        //System.out.println(ChatConfig.name.get(username));

    }

 /*   @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addLast(new ChatServerHandler());
    }*/
/*
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }*/
}
