package com.sattazalyk.car_gps2.serverData;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class DBHandler {

    Connection connection;
    String URL = "jdbc:mysql://127.0.0.1:3367/";
    String sqlInsert = "INSERT INTO accounts (first_name, last_name, phone, iid, pass) " +
            "VALUES(?,?,?,?,?)";

    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("jdbc has complete");
            connection = DriverManager.getConnection(URL, "root", "170502Zz");
            System.out.println("Connection has complete");
        } catch(ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
        return connection;
    }
    public void signUp(String phone, String pass){

    }

    public void registration(String firstName, String lastName,
                             String phone, String iid, String pass){
        try {

            PreparedStatement preparedStatement = getConnection().prepareStatement(sqlInsert);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,phone);
            preparedStatement.setString(4,iid);
            preparedStatement.setString(5,pass);
            preparedStatement.executeUpdate();

           /* Statement statement = connection.createStatement();

            statement.executeUpdate(sqlInsert);*/
            System.out.println("Added Account");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
