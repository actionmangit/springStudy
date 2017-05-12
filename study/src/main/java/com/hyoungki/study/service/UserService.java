package com.hyoungki.study.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.Level;
import com.hyoungki.study.domain.User;

public class UserService {
	
	public static final int MIN_LOGCOUNT_FOR_SILVER	= 50;
	public static final int MIN_RECOMMEND_FOR_GOLD	= 30;
	
	UserDao	userDao;
	UserLevelUpgradePolicy	userLevelUpgradePolicy;
	
	private DataSource	dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource	= dataSource;
	}
	
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
	
	public void upgradeLevels() throws Exception {
		TransactionSynchronizationManager.initSynchronization();
		Connection	c	= DataSourceUtils.getConnection(dataSource);
		c.setAutoCommit(false);
				
		try {
			List<User>	users	= userDao.getAll();
			
			for(User user : users) {
				if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
//					userLevelUpgradePolicy.upgradeLevel(user);
					upgradeLevel(user);
				}
			}
			
			c.commit();
		} catch (Exception e) {
			c.rollback();
			throw e;
		} finally {
			DataSourceUtils.releaseConnection(c, dataSource);
			TransactionSynchronizationManager.unbindResource(this.dataSource);
			TransactionSynchronizationManager.clearSynchronization();
		}
		
		
	}
	
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
}
