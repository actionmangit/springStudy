package com.hyoungki.study;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hyoungki.study.dao.JdbcContext;
import com.hyoungki.study.dao.UserDaoJdbc;
import com.hyoungki.study.domain.User;

public class UserDaoTest 
{
    private	UserDaoJdbc	dao;
    private JdbcContext jdbcContext;
	
    private User	user1;
    private User	user2;
    private User	user3;
	
	public static void main( String[] args ) throws ClassNotFoundException, SQLException {
        JUnitCore.main("com.hyoungki.study.UserDaoTest");
    }
    
    @Before
    public void setUp() {
		this.user1	= new User("kimbo", "김보", "1234");
		this.user2	= new User("lhk", "횽긔", "1234");
		this.user3	= new User("mung", "뭉이", "1234");
		
		dao				= new UserDaoJdbc();
		jdbcContext		= new JdbcContext();
		
		DataSource		dataSource	= new SingleConnectionDataSource(
				"jdbc:oracle:thin:@localhost:1521:xe", "curix", "1234", true);
		
		jdbcContext.setDataSource(dataSource);
		
		dao.setDataSource(dataSource);
    }

    @Test
    public void getAll() throws SQLException, ClassNotFoundException {
    	dao.deleteAll();
    	
    	List<User> users0	= dao.getAll();
    	assertThat(users0.size(), is(0));
    	
    	dao.add(user1);
    	List<User> users1	= dao.getAll();
    	assertThat(users1.size(), is(1));
    	checkSameUser(user1, users1.get(0));
    	
    	dao.add(user2);
    	List<User> users2	= dao.getAll();
    	assertThat(users2.size(), is(2));
    	checkSameUser(user1, users2.get(0));
    	checkSameUser(user2, users2.get(1));

    	dao.add(user3);
    	List<User> users3	= dao.getAll();
    	assertThat(users3.size(), is(3));
    	checkSameUser(user1, users3.get(0));
    	checkSameUser(user2, users3.get(1));
    	checkSameUser(user3, users3.get(2));
    }
    
    private void checkSameUser(User user1, User user2) {
    	assertThat(user1.getId(), is(user2.getId()));
    	assertThat(user1.getName(), is(user2.getName()));
    	assertThat(user1.getPassword(), is(user2.getPassword()));
    }
    
	@Test
	public void andAndGet() throws ClassNotFoundException, SQLException {
		
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
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
		dao.deleteAll();
		assertThat(dao.getCount(), is(0));
		
		dao.get("unknow_id");
	}
}

