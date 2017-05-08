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
			if (canUpgradeLevel(user)) {
				upgradeLevel(user);
			}
		}
	}
	
	private void upgradeLevel(User user) {
		if (user.getLevel() == Level.BASIC) user.setLevel(Level.SIVER);
		else if (user.getLevel() == Level.SIVER) user.setLevel(Level.GOLD);
		userDao.update(user);
	}
	
	private boolean canUpgradeLevel(User user) {
		Level	currentLevel	= user.getLevel();
		
		switch(currentLevel)
		{
			case BASIC: return (user.getLogin() >= 50);
			case SIVER: return (user.getRecommend() >= 30);
			case GOLD: return false;
			default: throw new IllegalArgumentException("Unknow Level: " + currentLevel);
		}
	}
}
