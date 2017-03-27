package com.hyoungki.study.dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CountingConnectionMaker implements ConnectionMaker{

	int		counter	= 0;
	private ConnectionMaker	realConnnectionMaker;

	public CountingConnectionMaker(ConnectionMaker realConnectionMaker) {
		this.realConnnectionMaker	= realConnectionMaker;
	}
	
	public Connection makeConnection() throws ClassNotFoundException, SQLException {
		this.counter ++;
		return realConnnectionMaker.makeConnection();
	}
	
	public int getCounter() {
		return this.counter;
	}
}
