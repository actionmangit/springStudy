package com.hyoungki.study.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.dao.EmptyResultDataAccessException;

import com.hyoungki.study.domain.User;

//public abstract class UserDao {
public class UserDao {	
//	private ConnectionMaker connectionMaker;
//	
//	public void setConnectionMaker(ConnectionMaker connectionMaker) {
//		this.connectionMaker	= connectionMaker;
//	}
	
	private DataSource	dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource	= dataSource;
	}
	
	public void add(User user) throws SQLException, ClassNotFoundException {

//		Connection			c	= connectionMaker.makeConnection();
		Connection			c	= dataSource.getConnection();
		
		PreparedStatement	ps	= c.prepareStatement(
				"insert into users(id, name, password) values(?,?,?)");
		
		ps.setString(1, user.getId());
		ps.setString(2, user.getName());
		ps.setString(3, user.getPassword());
		
		ps.executeUpdate();
		
		ps.close();
		c.close();
	}
	
	public User get(String id) throws SQLException, ClassNotFoundException {
		
//		Connection			c		= connectionMaker.makeConnection();
		Connection			c	= dataSource.getConnection();
		
		PreparedStatement	ps		= c.prepareStatement(
				"select * from users where id = ?");
		ps.setString(1, id);

		ResultSet			rs		= ps.executeQuery();
		
		User				user	= null;
		
		if (rs.next()) {
			user	= new User();
			user.setId(rs.getString("id"));
			user.setName(rs.getString("name"));
			user.setPassword(rs.getString("password"));
		}
		
		rs.close();
		ps.close();
		c.close();

		if (user == null) throw new EmptyResultDataAccessException(1);
		
		return user;
	}
	
	public void deleteAll() throws SQLException {
		Connection			c	= null;
		PreparedStatement	ps	= null;

		try {
			c	= dataSource.getConnection();
			ps	= c.prepareStatement("delete from users");
			ps.executeUpdate();
		} catch (SQLException e) {
			throw e;
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
		}
			
		if (c != null) {
			try {
				c.close();
			} catch (SQLException e) {
			}
		}
	}
	
	public int getCount() throws SQLException {
		Connection			c	= null;
		PreparedStatement	ps	= null;
		ResultSet			rs	= null;
		
		try {
			c	= dataSource.getConnection();
			ps	= c.prepareStatement("select count(*) from users");
			rs	= ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			throw e;
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			
			if (c != null) {
				try {
					c.close();
				} catch (SQLException e) {
				}
			}		
		}
	}


//	public abstract Connection getConnection() throws ClassNotFoundException, SQLException;
	
//	private Connection getConnection() throws ClassNotFoundException, SQLException {
//
//		Class.forName("oracle.jdbc.driver.OracleDriver");
//		Connection			c		= DriverManager.getConnection(
//				"jdbc:oracle:thin:@localhost:1521:xe", "curix", "1234");
//		
//		return c;
//	}
}
