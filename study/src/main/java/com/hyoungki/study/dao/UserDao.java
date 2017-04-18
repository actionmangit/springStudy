package com.hyoungki.study.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.hyoungki.study.domain.User;

public class UserDao {	

	private JdbcContext jdbcContext;
	private JdbcTemplate jdbcTemplate;
	
	private DataSource	dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate	= new JdbcTemplate(dataSource);
		this.dataSource		= dataSource;
	}
	
	public void setJdbcContext(JdbcContext jdbcContext) {
		this.jdbcContext	= jdbcContext;
	}
	
	public void add(final User user) throws SQLException, ClassNotFoundException {
		this.jdbcTemplate.update("insert into users(id, name, password) values(?,?,?)",
				user.getId(), user.getName(), user.getPassword());
	}
	
	public void deleteAll() throws SQLException {
		
		this.jdbcTemplate.update("delete from users");
	}	
	
	public List<User> getAll() {
		
		return this.jdbcTemplate.query("select * from users order by id", 
			new RowMapper<User>() {
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					User user	= new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					
					return user;
				}
		});
	}
	
	public User get(String id) throws SQLException, ClassNotFoundException {
		
		return this.jdbcTemplate.queryForObject("select * from users where id = ?",
			new Object[] {id},
			new RowMapper<User>(){
				public User mapRow(ResultSet rs, int rowNum) throws SQLException {
					
					User user	= new User();
					user.setId(rs.getString("id"));
					user.setName(rs.getString("name"));
					user.setPassword(rs.getString("password"));
					return user;
				}
		});
	}
	
	private PreparedStatement makeStatement(Connection c) throws SQLException {
		PreparedStatement	ps;
		ps	= c.prepareStatement("delete from users");
		return ps;
	}
	
	
	public int getCount() throws SQLException {
		return this.jdbcTemplate.queryForInt("select count(*) from users");
	}
}
