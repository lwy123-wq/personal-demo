package com.example.demo.chat;

import com.example.demo.entity.Message;
import com.example.demo.entity.User;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.atomic.AtomicInteger;

public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static AtomicInteger online = new AtomicInteger();
    Message message=null;
    int i=0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        message = new Gson().fromJson(msg.text(), Message.class);
        if(message==null){
           sendMessageByChannel(ctx.channel(), new Message(message.getName(),"消息错误", System.currentTimeMillis()));
        }else {
            //ChatConfig.name.put(message.getName(),ctx.channel());
            //ChannelId channelId = channelIdMap.get(message.getId());
           if(message.getName()==null){
               System.out.println(message.getName()+"xxxxxxxxxxxxx");
               sendMessageByChannel(ctx.channel(),new Message(message.getName(),"对方已下线", System.currentTimeMillis()));
           }else {

               //sendMessageByChannel(channelGroup.find(ctx.channel().id()),message);
               sendMessageForAll(message);
           }
        }
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

        i++;
        User user=new User();
        channelGroup.add(ctx.channel());
        Channel channel=ctx.channel();
        //ChatConfig..put(ctx.channel().name().asShortText(), ctx.channel().id());
        online.set(channelGroup.size());
        sendMessageForAll(new Message(ChatConfig.name.get(i),ChatConfig.name.get(i), System.currentTimeMillis()));
        System.out.println(ctx.channel().remoteAddress() + "上线！" + "--->" + ChatConfig.name.get(i));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        int i=0;
        i++;
        Channel channel=ctx.channel();
        channelGroup.remove(ctx.channel());
        //ChatConfig.name.remove(ChatConfig.name1.get(channel));
        ChatConfig.name.remove(i);
        online.set(channelGroup.size());
        sendMessageForAll(new Message(ChatConfig.name.get(i), ChatConfig.name.get(i), System.currentTimeMillis()));
        System.out.println(ctx.channel().remoteAddress() + "下线！");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }


    private void sendMessageByChannel(Channel channel, Message message) {
        channel.writeAndFlush(new TextWebSocketFrame(new Gson().toJson(message)));
    }

    private void sendMessageForAll(Message message) {
        for (Channel channel : channelGroup) {
            channel.writeAndFlush(new TextWebSocketFrame(new Gson().toJson(message)));
        }
    }
}
