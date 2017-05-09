package com.hyoungki.study.service;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.Level;
import com.hyoungki.study.domain.User;

import static com.hyoungki.study.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.hyoungki.study.service.UserService.MIN_RECOMMEND_FOR_GOLD;

public class UserLevelUpgradeDefaultPolicy implements UserLevelUpgradePolicy{

	UserDao	userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao	= userDao;
	}
	
	public boolean canUpgradeLevel(User user) {
		Level	currentLevel	= user.getLevel();
		
		switch(currentLevel)
		{
			case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
			case SILVER: return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
			case GOLD: return false;
			default: throw new IllegalArgumentException("Unknow Level: " + currentLevel);
		}
	}

	public void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
}
