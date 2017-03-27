package com.hyoungki.study.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class NUserDao extends UserDao{

	public NUserDao(ConnectionMaker connectionMaker) {
		super(connectionMaker);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
