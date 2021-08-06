package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.dao.UserDao;
import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonDao personDao;

    @Autowired
    private UserDao userDao;
/*jhkglfsd*/
    @Autowired
    Person person;

    @Override
    public void register(String id) {

           // personDao.createP(id);

    }
    //id是主键，


    @Override
    public String add(String name, String manger) {
        if (userDao.findByName(name)!=null){
            person=new Person(userDao.findByName(name).getId(),name);
            if (personDao.findByName(name,manger)!=null){
                return "该朋友已添加";
            }
            personDao.addPerson(person,manger);
            return "成功";
        }
        else return "该用户不存在";
    }
    //添加好友


    @Override
    public Person findById(String id,String mm) {
        return personDao.findById(id,mm);
    }
    //查询好友
}
