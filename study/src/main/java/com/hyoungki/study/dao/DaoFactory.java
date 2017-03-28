package com.hyoungki.study.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		UserDao		userDao		= new UserDao();
		userDao.setConnectionMaker(connectionMaker());
				
		return userDao;
	}
	
	@Bean
	public AccountDao accountDao() {
		return new AccountDao(connectionMaker());
	}
	
	@Bean
	public ConnectionMaker connectionMaker() {
		return new DConnectionMaker();
	}
}
