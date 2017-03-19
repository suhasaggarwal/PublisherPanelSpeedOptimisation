package com.cuberoot.util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector
{
    
    
   public Connection getPooledConnection()
   {
	   try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	   Connection conn = null;
	   try {
		conn = DriverManager.getConnection("jdbc:mysql://52.90.244.81:3306/datastore","root","Qwerty12@");
	    
	   
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
      return conn;
    }   

}
 

