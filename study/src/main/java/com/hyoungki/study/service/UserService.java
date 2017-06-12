package com.hyoungki.study.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.Level;
import com.hyoungki.study.domain.User;

public class UserService {
	
	public static final int MIN_LOGCOUNT_FOR_SILVER	= 50;
	public static final int MIN_RECOMMEND_FOR_GOLD	= 30;
	
	private PlatformTransactionManager transactionManager;
	private UserDao	userDao;
	private UserLevelUpgradePolicy	userLevelUpgradePolicy;

	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager	= transactionManager;
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
		
		TransactionStatus				status	=
					this.transactionManager.getTransaction(new DefaultTransactionDefinition());
				
		try {
			List<User>	users	= userDao.getAll();
			
			for(User user : users) {
				if (userLevelUpgradePolicy.canUpgradeLevel(user)) {
//					userLevelUpgradePolicy.upgradeLevel(user);
					upgradeLevel(user);
				}
			}
			
			this.transactionManager.commit(status);
		} catch (Exception e) {
			this.transactionManager.rollback(status);
			throw e;
		} 
	}
	
	protected void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
	}
}
