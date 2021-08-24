package com.example.demo.controller;

import com.example.demo.entity.Message;

public interface Handler {
     int handler(Message msg);
}
