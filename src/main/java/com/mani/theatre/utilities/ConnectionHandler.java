package com.mani.theatre.utilities;

import java.sql.Connection;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ConnectionHandler {
	
	public static Connection create()
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring-connection.xml");
		((ClassPathXmlApplicationContext) context).registerShutdownHook();
		Connection conn = ((ConnectionManager) context.getBean("bookconnection")).getConnection();
		((ClassPathXmlApplicationContext) context).close();
		return conn;
	}
	public static Connection getConnection() {
		ApplicationContext context = new ClassPathXmlApplicationContext("/spring-connection.xml");
		((ClassPathXmlApplicationContext) context).registerShutdownHook();
		Connection conn = ((ConnectionManager) context.getBean("connection")).getConnection();
		((ClassPathXmlApplicationContext) context).close();
		return conn;
	}
}
