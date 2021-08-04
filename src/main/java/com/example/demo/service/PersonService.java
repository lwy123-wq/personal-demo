package com.example.demo.service;


import com.example.demo.entity.Person;

public interface PersonService {
    void register(String id);
    //注册创person表

    String add(String name,String manger);
    //往manager表里填加好友
    Person findById(String id, String manger);
    //通过id查询好友
}
