package com.mani.theatre.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private Connection conn;
	private String userName, password, className, ipAddress;
	private int port;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public void createConnection() {
		try {
			String URL = "jdbc:mysql://" + ipAddress + ":" + port + "/rt?autoReconnect=true&useSSL=false";
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			setConn(DriverManager.getConnection(URL, userName, password));
		} catch (SQLException exp) {
			System.err.println(exp.getMessage());
		}
	}

	public Connection getConnection() {
		try {
			if (conn == null || conn.isClosed())
				createConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	private void setConn(Connection conn) {
		this.conn = conn;
	}

	/*
	 * public void closeConnection() {
	 * System.out.println("Trying to close the connection"); try { if (conn !=
	 * null) conn.close(); } catch (SQLException e) { e.printStackTrace(); } }
	 */
}
