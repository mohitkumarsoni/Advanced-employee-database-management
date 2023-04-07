package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionBuilder {
    static Connection con;
    public static Connection createConnection(String user ,String pass){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/company",user,pass);
            System.out.println("connection successful !!");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error connecting database");
        }
        return con;
    }

    public static Connection createConnection(){
        return con;
    }
}
