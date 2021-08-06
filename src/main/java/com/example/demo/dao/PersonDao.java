package com.example.demo.dao;

import com.example.demo.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PersonDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
  /*  public void createP(String id){
        String sql="CREATE TABLE "+id+"(name varchar(30), id int, PRIMARY KEY(id))ENGINE=innodb DEFAULT CHARSET=utf8";
        jdbcTemplate.execute(sql);
        //jdbcTemplate.update("alter table xxx rename to ?",name);
    }*/

    //CREATE TABLE  id(name varchar(30), id int , PRIMARY KEY(id))ENGINE=innodb DEFAULT CHARSET=utf8

    public Person findByName(String name, String Manager){
        final Person person = new Person();
        String sql = "SELECT name FROM "+Manager+ " WHERE name=?";
        jdbcTemplate.query(sql, new Object[]{name}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
               person.setName(resultSet.getString(1));
            }
        });
        return person;
    }



    public Person findById(String id,String Manager){
        final Person person = new Person();
        String sql = "SELECT name FROM "+Manager+ " WHERE id=?";
        jdbcTemplate.query(sql, new Object[]{id}, new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(1));
            }
        });
        return person;
    }


//    public boolean findNameFromTable(String id){
//        final boolean[] is = {true};
//        String sql="SELECT id FROM user";
//        jdbcTemplate.query(sql, new Object[]{id}, new RowCallbackHandler() {
//            @Override
//            public void processRow(ResultSet rs) throws SQLException {
//                if (rs.getString(0)==id){
//                    is[0] =false;
//                }
//            }
//        });
//        return is[0];
//    }


    public void addPerson(Person person,String manger){
        boolean is=false;
        String name1=person.getName();
        String sql="INSERT INTO "+manger+"(name) values("+name1+")";
        jdbcTemplate.execute(sql);
    }
}
