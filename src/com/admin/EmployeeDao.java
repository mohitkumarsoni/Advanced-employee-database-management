package com.admin;

import com.connection.ConnectionBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeDao {

    public static boolean addNewEmployee(Employee emp) {
        boolean f = false;
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query = "insert into employee(name,id,address,salary,email,phone) values(?,?,?,?,?,?)";

            PreparedStatement prs = con.prepareStatement(query);
            prs.setString(1,emp.getName());
            prs.setInt(2,emp.getId());
            prs.setString(3,emp.getAddress());
            prs.setLong(4,emp.getSalary());
            prs.setString(5,emp.getEmail());
            prs.setLong(6, emp.getPhone());

            prs.executeUpdate();
            f=true;

            System.out.println("successfully added new employee : "+emp);



        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
    public static boolean updateEmployeeDetail(String name, int updateChoice) {
        //if id update
        boolean f = false;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Connection con = ConnectionBuilder.createConnection();
            System.out.println("enter new id");
            int newId = Integer.parseInt(br.readLine());

            String query = "update employee set id=? where name=?";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setInt(1,newId);
            prs.setString(2, name);

            prs.executeUpdate();
            f=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  f;
    }//update id case 2
    public static boolean updateEmployeeDetail(int id,int updateChoice){
        //if other update
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean f = false;
        try {
            Connection con = ConnectionBuilder.createConnection();
            switch (updateChoice){
                case 1->{
                    System.out.print("enter new name : ");
                    String newName = br.readLine();

                    String query = "update employee set name=? where id=?";
                    PreparedStatement prs = con.prepareStatement(query);

                    prs.setString(1,newName);
                    prs.setInt(2,id);

                    prs.executeUpdate();
                    f=true;
                }
                case 3->{
                    System.out.print("enter new address : ");
                    String newAddress = br.readLine();

                    String query = "update employee set address=? where id=?";
                    PreparedStatement prs = con.prepareStatement(query);

                    prs.setString(1,newAddress);
                    prs.setInt(2,id);

                    prs.executeUpdate();
                    f=true;
                }
                case 4->{
                    System.out.print("enter new salary : ");
                    long newSalary = Long.parseLong(br.readLine());

                    String query = "update employee set salary=? where id=?";

                    PreparedStatement prs = con.prepareStatement(query);
                    prs.setLong(1,newSalary);
                    prs.setInt(2,id);

                    prs.executeUpdate();
                    f=true;
                }
                case 5->{
                    System.out.print("enter new email");
                    String newEmail = br.readLine();

                    Pattern validateEmail = Pattern.compile("[a-zA-Z0-9._-]+@[a-zA-Z.-]+\\.com");
                    Matcher matcher = validateEmail.matcher(newEmail);

                    if(matcher.matches()){
                        String query = "update employee set email=? where id=?";
                        PreparedStatement prs = con.prepareStatement(query);
                        prs.setString(1,newEmail);
                        prs.setInt(2, id);
                        prs.executeUpdate();
                        f=true;
                    }else {
                        System.out.println("invalid e-mail");
                    }
                }
                case 6->{
                    System.out.print("enter new phone number : ");
                    long newNumber = Long.parseLong(br.readLine());

                    String newNumberValidation = Long.toString(newNumber);
                    Pattern validatePhone = Pattern.compile("\\d{10}");
                    Matcher matcher = validatePhone.matcher(newNumberValidation);

                    if(matcher.matches()){
                        String query = "update employee set phone=? where id=?";
                        PreparedStatement prs = con.prepareStatement(query);
                        prs.setLong(1,newNumber);
                        prs.setInt(2,id);
                        prs.executeUpdate();
                        f=true;
                    }else {
                        System.out.println("invalid number");
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
    public static boolean deleteEmployee(int id) {
        boolean f= false;
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query ="delete from employee_info where id =?";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setInt(1,id);
            prs.executeUpdate();
            f=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
    public static void showAllEmployees() {
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query = "select * from employee";
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);
            while (resultSet.next()){
                System.out.println("name : "+ resultSet.getString(1));
                System.out.println("id : "+resultSet.getString(2));
                System.out.println("address : "+resultSet.getString(3));
                System.out.println("salary : "+resultSet.getLong(4));
                System.out.println("e-mail : "+resultSet.getString(5));
                System.out.println("phone : "+resultSet.getLong(6));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void detailByIdForAdmin(int id) {
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query = "select * from employee where id=?";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setInt(1,id);

            ResultSet resultSet =prs.executeQuery();
            while (resultSet.next()){
                System.out.println("name : "+ resultSet.getString(1));
                System.out.println("id : "+resultSet.getString(2));
                System.out.println("address : "+resultSet.getString(3));
                System.out.println("salary : "+resultSet.getLong(4));
                System.out.println("e-mail : "+resultSet.getString(5));
                System.out.println("phone : "+resultSet.getLong(6));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void detailByNameForAdmin(String name) {
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query = "select * from employee where name=?";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setString(1,name);
            ResultSet resultSet = prs.executeQuery();
            while (resultSet.next()){
                System.out.println("name : "+ resultSet.getString(1));
                System.out.println("id : "+resultSet.getString(2));
                System.out.println("address : "+resultSet.getString(3));
                System.out.println("salary : "+resultSet.getLong(4));
                System.out.println("e-mail : "+resultSet.getString(5));
                System.out.println("phone : "+resultSet.getLong(6));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void allEmployeesToUser() {
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query = "select name,id, email from employee";
            Statement stmt = con.createStatement();
            ResultSet resultSet = stmt.executeQuery(query);

            while (resultSet.next()){
                System.out.println("name : "+resultSet.getString(1));
                System.out.println("id : "+resultSet.getInt(2));
                System.out.println("email : "+resultSet.getString(3));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void detailByIdForUser(int id) {
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query ="select name,id,email from employee where id=?";
            PreparedStatement prs = con.prepareStatement(query);
            prs.setInt(1,id);

            ResultSet result =  prs.executeQuery();
            while (result.next()){
                System.out.println("name : "+result.getString(1));
                System.out.println("id : "+result.getInt(2));
                System.out.println("email :"+result.getString(3));
                System.out.println("+++++++++++++++++++++++++++++++++++++++");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void detailByNameForUser(String name) {
        try {
            Connection con = ConnectionBuilder.createConnection();
            String query = "select name, id, email from employee where name=?";
            PreparedStatement prs= con.prepareStatement(query);
            prs.setString(1,name);

            ResultSet result = prs.executeQuery();
            while(result.next()) {
                System.out.println("name : "+result.getString(1));
                System.out.println("id : "+result.getInt(2));
                System.out.println("email : "+result.getString(3));
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static boolean editYourPersonalDetailsForUser(int id, int userUpdateChoice) {
        boolean f= false;
        try {
            BufferedReader br= new BufferedReader(new InputStreamReader(System.in));

            switch (userUpdateChoice){
                case 1->{
                    //edit address
                    System.out.print("enter new address : ");
                    String newAddress = br.readLine();

                    Connection con = ConnectionBuilder.createConnection();
                    String query = "update employee set address=? where id=?";
                    PreparedStatement prs = con.prepareStatement(query);
                    prs.setString(1,newAddress);
                    prs.setInt(2,id);
                    prs.executeUpdate();
                    f=true;
                }
                case 2->{
                    //edit email
                    System.out.print("enter new email");
                    String newEmail = br.readLine();

                    Pattern pattern = Pattern.compile("[a-zA-z0-9._-]+@[a-zA-Z0-9-]+\\.(com)");
                    Matcher matcher = pattern.matcher(newEmail);

                    if(matcher.matches()){
                        Connection con = ConnectionBuilder.createConnection();
                        String query = "update employee set email=? where id=?";
                        PreparedStatement prs = con.prepareStatement(query);
                        prs.setString(1,newEmail);
                        prs.setInt(2,id);
                        prs.executeUpdate();
                        f=true;
                    }else {
                        System.out.println("invalid email format, cant be updated !!!");
                    }
                }
                case 3->{
                    System.out.print("enter new number : ");
                    long newNumber = Long.parseLong(br.readLine());
                    String numValidation = Long.toString(newNumber);

                    Pattern pattern = Pattern.compile("[6-9][0-9]{9}");
                    Matcher matcher = pattern.matcher(numValidation);

                    if(matcher.matches()){
                        Connection con = ConnectionBuilder.createConnection();
                        String query = "update employee set phone=? where id=?";
                        PreparedStatement prs = con.prepareStatement(query);
                        prs.setLong(1,newNumber);
                        prs.setInt(2,id);
                        prs.executeUpdate();
                        f=true;
                    }else {
                        System.out.println("invalid number, cant be updated");
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return f;
    }
}
