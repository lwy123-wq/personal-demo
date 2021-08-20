package com.example.demo.chat;

import com.example.demo.entity.Message;
import com.google.gson.Gson;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.atomic.AtomicInteger;
public class ServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static AtomicInteger online = new AtomicInteger();
    Message message=null;
    public static int i=0;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        message = new Gson().fromJson(msg.text(), Message.class);
        if(message==null){
           sendMessageByChannel(ctx.channel(), new Message(message.getName(),"消息错误", System.currentTimeMillis()));
        }else {
            ChannelId channelId = ChatConfig.map.get(message.getName());
            System.out.println(message.getName());
           if(message.getName()==null){
               System.out.println(message.getName()+"xxxxxxxxxxxxx");
               sendMessageByChannel(ctx.channel(),new Message(message.getName(),"对方已下线", System.currentTimeMillis()));
           }else {
               sendMessageByChannel(channelGroup.find(channelId),message);
               sendMessageByChannel(channelGroup.find(ctx.channel().id()),message);

           }
        }
    }
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        i++;
        channelGroup.add(ctx.channel());
        //ChatConfig..put(ctx.channel().name().asShortText(), ctx.channel().id());
        online.set(channelGroup.size());
        sendMessageForAll(new Message(ChatConfig.name.get(i),ChatConfig.name.get(i), System.currentTimeMillis()));
        System.out.println(ctx.channel().remoteAddress() + "上线！" + "--->" + ChatConfig.name.get(i));
        ChatConfig.map.put(ChatConfig.name.get(i),ctx.channel().id());
        System.out.println(i);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        channelGroup.remove(ctx.channel());
        ChatConfig.name.remove(i);
        online.set(channelGroup.size());
        sendMessageForAll(new Message(ChatConfig.name.get(i), ChatConfig.name.get(i)+"下线！", System.currentTimeMillis()));
        System.out.println(ctx.channel().remoteAddress() + "下线！");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if(evt instanceof IdleStateEvent){
            IdleStateEvent event=(IdleStateEvent) evt;
            if(event.state()== IdleState.ALL_IDLE){
                System.out.println(ctx.channel().remoteAddress()+"读写空闲\n");
                sendMessageByChannel(ctx.channel(),new Message("","您已好久未和您的好友聊天了",System.currentTimeMillis()));
            }
        }
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
