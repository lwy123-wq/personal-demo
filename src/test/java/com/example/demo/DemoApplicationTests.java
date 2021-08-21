package com.example.demo;

import com.example.demo.dao.PersonDao;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    PersonDao personDao=new PersonDao();
    @Test
    void test() {
        /*Person person=new Person("aaa");
        //personDao.createP("ccc");
        personDao.addPerson(person,"ccc");*/
    }

}
