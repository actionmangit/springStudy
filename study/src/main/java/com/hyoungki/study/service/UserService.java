package com.hyoungki.study.service;

import java.util.List;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.Level;
import com.hyoungki.study.domain.User;

public class UserService {
	UserDao	userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao	= userDao;
	}
	
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	public void upgradeLevels() {
		List<User>	users	= userDao.getAll();
		
		for(User user : users) {
			Boolean	changed	= null;
			
			if (user.getLevel() == Level.BASIC && user.getLogin() >= 50) {
				user.setLevel(Level.SIVER);
				changed	= true;
			}
			else if (user.getLevel() == Level.SIVER && user.getRecommend() >= 30) {
				user.setLevel(Level.GOLD);
				changed	= true;
			}
			else if (user.getLevel() == Level.GOLD) {changed = false;}
			else {changed = false;}
			
			if (changed) {userDao.update(user);}
		}
	}
}
