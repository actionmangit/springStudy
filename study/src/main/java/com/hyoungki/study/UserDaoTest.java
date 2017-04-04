package com.hyoungki.study;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.User;

public class UserDaoTest 
{
    public static void main( String[] args ) throws ClassNotFoundException, SQLException
    {
        JUnitCore.main("com.hyoungki.study.UserDaoTest");
    }
    
	@Test
	public void andAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext		context	= new
				ClassPathXmlApplicationContext("applicationContext.xml");
		
		UserDao		dao		= context.getBean("userDao", UserDao.class);
		User		user1	= new User("lhk", "횽긔", "1234");
		User		user2	= new User("kimbo", "김보", "1234");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		User		user	= new User("kimbo", "김보", "1234");
		
		dao.add(user1);
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		User		userget1	= dao.get(user1.getId());
		assertThat(userget1.getName(), is(user1.getName()));
		assertThat(userget1.getPassword(), is(user1.getPassword()));
		
		User		userget2	= dao.get(user2.getId());
		assertThat(userget2.getName(), is(user2.getName()));
		assertThat(userget2.getPassword(), is(user2.getPassword()));
		
	}

	@Test
	public void count() throws SQLException, ClassNotFoundException {
		ApplicationContext		context	= new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao		dao		= context.getBean("userDao", UserDao.class);
		User		user1	= new User("lhk", "횽긔", "1234");
		User		user2	= new User("kimbo", "김보", "1234");
		User		user3	= new User("mung", "뭉이", "1234");
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.add(user1);
		assertThat(dao.getCount(), is(1));
		
		dao.add(user2);
		assertThat(dao.getCount(), is(2));
		
		dao.add(user3);
		assertThat(dao.getCount(), is(3));
		
	}
	
	@Test(expected=EmptyResultDataAccessException.class)
	public void getUserFailure() throws SQLException, ClassNotFoundException {
		ApplicationContext		context	= new GenericXmlApplicationContext("applicationContext.xml");
		
		UserDao		dao	= context.getBean("userDao", UserDao.class);
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknow_id");
	}
}

