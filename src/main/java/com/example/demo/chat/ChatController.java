package com.example.demo.chat;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ChatController {

    @GetMapping("login1")
    public String login(Model model, @RequestParam("userId") String userId, @RequestParam("sendId") String sendId) {
        model.addAttribute("userId", userId);
        model.addAttribute("sendId", sendId);
        return "chat";
    }

    @GetMapping("/sendMsg")
    public String login(@RequestParam("sendId") String sendId) throws InterruptedException {
        while (true) {
            Channel channel = ChatConfig.concurrentHashMap.get(sendId);
            if (channel != null) {
                channel.writeAndFlush(new TextWebSocketFrame("test"));
                Thread.sleep(1000);
            }
        }

    }

}

