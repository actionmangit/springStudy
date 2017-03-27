package com.hyoungki.study.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DUserDao extends UserDao{

	public DUserDao(ConnectionMaker connectionMaker) {
		super(connectionMaker);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		return null;
	}
}
