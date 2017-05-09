package com.hyoungki.study.service;

import java.util.List;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.Level;
import com.hyoungki.study.domain.User;

public class UserService {
	
	public static final int MIN_LOGCOUNT_FOR_SILVER	= 50;
	public static final int MIN_RECOMMEND_FOR_GOLD	= 30;
	
	UserDao	userDao;
	UserLevelUpgradePolicy	userLevelUpgradePolicy;
	
	public void setUserDao(UserDao userDao) {
		this.userDao	= userDao;
	}

	public void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy	= userLevelUpgradePolicy;
	}
	
	public void add(User user) {
		if (user.getLevel() == null) user.setLevel(Level.BASIC);
		userDao.add(user);
	}
	
	public void upgradeLevels() {
		List<User>	users	= userDao.getAll();
		
		for(User user : users) {
			if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
				userLevelUpgradePolicy.upgradeLevel(user);
			}
		}
	}
}
