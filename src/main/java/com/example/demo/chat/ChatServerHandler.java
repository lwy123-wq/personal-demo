package com.example.demo.chat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;

import java.time.LocalDateTime;

public class ChatServerHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //传过来的是json字符串
        String text = textWebSocketFrame.text();
        JSONObject jsonObject = JSON.parseObject(text);
        //获取到发送人的用户id
        Object msg = jsonObject.get("msg");
        String userId = (String) jsonObject.get("userId");
        Channel channel = channelHandlerContext.channel();
        if (msg == null) {
            //说明是第一次登录上来连接，还没有开始进行聊天，将uid加到map里面
            register(userId, channel);
        } else {
            //有消息了,开始聊天了
            sendMsg(msg, userId);
        }

    }

    /**
     * 第一次登录进来
     *
     * @param userId
     * @param channel
     */
    private void register(String userId, Channel channel) {
        if (!ChatConfig.concurrentHashMap.containsKey(userId)) { //没有指定的userId
            ChatConfig.concurrentHashMap.put(userId, channel);
            // 将用户ID作为自定义属性加入到channel中，方便随时channel中获取用户ID
            AttributeKey<String> key = AttributeKey.valueOf("userId");
            channel.attr(key).setIfAbsent(userId);
        }
    }

    /**
     * 开发发送消息，进行聊天
     *
     * @param msg
     * @param userId
     */
    private void sendMsg(Object msg, String userId) {
        Channel channel1 = ChatConfig.concurrentHashMap.get(userId);
        if (channel1 != null) {
            channel1.writeAndFlush(new TextWebSocketFrame("服务器时间" + LocalDateTime.now() + " " + msg));
        }
    }


    /**
     * 一旦客户端连接上来，该方法被执行
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //log.info("handlerAdded 被调用" + ctx.channel().id().asLongText());
    }

    /**
     * 断开连接，需要移除用户
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        removeUserId(ctx);
    }

    /**
     * 移除用户
     *
     * @param ctx
     */
    private void removeUserId(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        AttributeKey<String> key = AttributeKey.valueOf("userId");
        String userId = channel.attr(key).get();
        ChatConfig.concurrentHashMap.remove(userId);
        //log.info("用户下线,userId：{}", userId);
    }

    /**
     * 处理移除，关闭通道
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

