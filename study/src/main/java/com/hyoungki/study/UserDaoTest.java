package com.hyoungki.study;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hyoungki.study.dao.ConnectionMaker;
import com.hyoungki.study.dao.CountingConnectionMaker;
import com.hyoungki.study.dao.CountingDaoFactory;
import com.hyoungki.study.dao.DConnectionMaker;
import com.hyoungki.study.dao.DaoFactory;
import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.User;

/**
 * Hello world!
 *
 */
public class UserDaoTest 
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
//    	ApplicationContext	context		= 
//    			new AnnotationConfigApplicationContext(DaoFactory.class);
    	ApplicationContext	context		= 
    			new AnnotationConfigApplicationContext(CountingDaoFactory.class);
    	UserDao				dao			= context.getBean("userDao", UserDao.class);
        
        User		user	= new User();
        user.setId("whiteship");
        user.setName("이형기");
        user.setPassword("married");
        
        dao.add(user);
        
        System.out.println(user.getId() + " 등록 성공");
        
        User		user2	= dao.get(user.getId());
        System.out.println(user2.getName());
        System.out.println(user2.getPassword());
        
        System.out.println(user2.getId() + " 조회 성공");
        
        CountingConnectionMaker	ccm		= context.getBean("connectionMaker", CountingConnectionMaker.class);
        
        System.out.println("Connection counter : " + ccm.getCounter());
    }
}

