package br.com.TCCMapa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private final String url = "jdbc:postgresql://ec2-54-87-112-29.compute-1.amazonaws.com:5432/d9tshl1h6g7c2";
	private final String login = "jejgedcmxsgyod";
	private final String senha = "edf74730d1260c2c85671207ff6ff58a0349982d3308aa626bf04e56c0573d39";
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, login, senha);
	}
}
