package com.todo.service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TodoConnection {
	private static Connection con = null;
	
	public static void closeConnection() {
		if(con!=null) {
			try {
				con.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConnection() {
		if(con==null) {
			try {
				Class.forName("org.sqlite.JDBC");
				con = DriverManager.getConnection("jdbc:sqlite:"+"TodoListApp.db");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return con;
	}

}