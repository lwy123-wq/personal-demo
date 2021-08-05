package com.example.demo.chat;

import com.example.demo.controller.UserController;
import com.example.demo.entity.User;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServerHandler extends SimpleChannelInboundHandler<User> {
    @Autowired
    UserController userController=new UserController();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {
        String username=msg.getUsername();
        String password=msg.getPassword();
        String aa=userController.login(username,password);
        if(aa=="success"){
           ChatConfig.concurrentHashMap.put(username,ctx.channel());
        }
    }
}
