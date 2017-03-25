package com.example.BeatyPhoneServer.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DButil {
	private final String SQL_USERNAME = "root";		//数据库用户名
	private final String SQL_PASSWORD = "tb550403";		//数据库密码
	private final String SQL_DRIVER = "com.mysql.jdbc.Driver";		//数据库驱动
	private final String SQL_URL = "jdbc:mysql://localhost:3306/beautyphonedb";	//数据库地址，在这里使用本地数据库
	private Connection connection = null;		//数据库连接
	private PreparedStatement pstmt = null;		//sql的执行对象
	private ResultSet resultSet = null;		//数据库返回结果

	public DButil(){
		try {
			//System.out.println("开始加载数据库驱动");
			Class.forName(SQL_DRIVER);
			//System.out.println("加载数据库驱动成功");
			//connection = getConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//System.out.println("加载数据库驱动异常");
		}
	}

	private Connection getConnection(){
		try {
			connection = DriverManager.getConnection(SQL_URL, SQL_USERNAME, SQL_PASSWORD);
			//System.out.println("连接数据库成功");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return connection;
	}


	public ResultSet dbQuery(String order){
		connection = getConnection();
		try {
			pstmt = connection.prepareStatement(order);
			resultSet = pstmt.executeQuery(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return resultSet;
		}
		return resultSet;

	}

	public int dbDelete(String order){
		connection = getConnection();
		int row = 0;
		try {
			pstmt = connection.prepareStatement(order);
			row = pstmt.executeUpdate(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return row;
	}

	public int dbUpdata(String order){
		connection = getConnection();
		int row = 0;
		try {
			pstmt = connection.prepareStatement(order);
			row = pstmt.executeUpdate(order);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		return row;
	}

	public int dbAdd(String order){
		connection = getConnection();
		int row = 0;
		try {
			pstmt = connection.prepareStatement(order);
			System.out.print("\n" + order + "\n");
			row = pstmt.executeUpdate(order);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				connection.close();
				//System.out.print("\n" +"链接关闭成功" + "\n");
				return 0;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				//System.out.print("\n" +"链接关闭失败" + "\n");
			}
			return 0;
		}
		return row;
	}

}
