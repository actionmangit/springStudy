package com.hyoungki.study.service;

import com.hyoungki.study.dao.UserDao;
import com.hyoungki.study.domain.Level;
import com.hyoungki.study.domain.User;

import static com.hyoungki.study.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static com.hyoungki.study.service.UserService.MIN_RECOMMEND_FOR_GOLD;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

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
		sendUpgradeEmail(user);
	}
	
	private void sendUpgradeEmail(User user) {
		JavaMailSenderImpl		mailSender	= new JavaMailSenderImpl();
		mailSender.setHost("mail.server.com");
		
		SimpleMailMessage	mailMessage	= new SimpleMailMessage();
		
		mailMessage.setTo(user.getEmail());
		mailMessage.setFrom("lhk0023@uprism.com");
		mailMessage.setSubject("Upgrade 안내");
		mailMessage.setText("사용자님의 등급이 " + user.getLevel().name() + "로 업그레이드 되었습니다.");
		
		mailSender.send(mailMessage);
	}
}
