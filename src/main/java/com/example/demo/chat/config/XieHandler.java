package com.example.demo.chat.config;

import com.example.demo.entity.Message;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class XieHandler implements Handler {

    @Override
    @PostMapping(value = "/xie")
    @ResponseBody
    public String handler(Message msg) {
        if (msg!=null){
            System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzz");
            System.out.println(msg.getContent()+"jjjjjjjjjjjj");
            return "success";
        }
        return "error";
    }
}
