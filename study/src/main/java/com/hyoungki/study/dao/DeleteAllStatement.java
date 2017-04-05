package com.hyoungki.study.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAllStatement implements StatementStrategy{

	public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
		PreparedStatement		ps	= c.prepareCall("delete from users");
		return ps;
	}
}
