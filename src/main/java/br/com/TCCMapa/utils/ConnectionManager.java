package br.com.TCCMapa.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

	private final String url = "jdbc:postgresql://localhost:5432/ProjetoFinal";
	private final String login = "postgres";
	private final String senha = "147862Dj";
	
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, login, senha);
	}
}
