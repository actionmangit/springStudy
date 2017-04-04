package com.hyoungki.study;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.User;


public class UserDaoTest123 
{

	@Test
	public void andAndGet() throws ClassNotFoundException, SQLException {
		ApplicationContext		context	= new
				ClassPathXmlApplicationContext("applicationContext.xml");
		
		UserDao		dao		= context.getBean("userDao", UserDao.class);
		User		user	= new User();
		user.setId("gyumee");
		user.setName("김보");
		user.setPassword("1234");
		
		dao.add(user);
		
		User		user2	= dao.get(user.getId());
		
		assertThat(user2.getName(), is(user.getName()));
		assertThat(user2.getPassword(), is(user.getPassword()));
	}
}
