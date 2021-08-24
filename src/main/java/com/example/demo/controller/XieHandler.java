package com.example.demo.controller;

import com.example.demo.entity.Message;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class XieHandler implements Handler {

    @Override
    @PostMapping(value = "/xie")
    @ResponseBody
    public int handler(Message msg) {
        int res=0;
        if (msg!=null){
            System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzz");
            System.out.println(msg.getContent()+"jjjjjjjjjjjj");
            return res=1;
        }
        return res=0;
    }
}
