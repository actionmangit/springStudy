package com.hyoungki.study.dao;

import java.util.List;

import com.hyoungki.study.domain.User;

public interface UserDao {
	void add(User user);
	User get(String id);
	List<User> getAll();
	void deleteAll();
	int getCount();
}
