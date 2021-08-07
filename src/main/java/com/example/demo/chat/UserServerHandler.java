package com.example.demo.chat;

import com.example.demo.entity.User;
import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class UserServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        User message = new Gson().fromJson(msg.text(), User.class);
        String username=message.getUsername();
        String password=message.getPassword();
        System.out.println(username+"sssssssssss"+password);

        ChatConfig.name.put(username,ctx.channel());
        ChatConfig.name1.put(ctx.channel(),username);
        System.out.println(ChatConfig.name.get(username));

    }

 /*   @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        ctx.pipeline().addLast(new ChatServerHandler());
    }*/
}
