package Model;


import org.json.JSONObject;

import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbManager {
/*
 *INSERT EXAMPE
 *﻿insert into t_Student_info(email, password, on_call) Values('ericblackmon52@yahoo.com','281330800fB',true)
 */
    public JSONObject getStudentById(int id){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String password="";String first="";String last="";
        boolean onCall=false;
        JSONObject studentObj= new JSONObject();
        try {
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                email=rs.getString("email");
                password=rs.getString("password");
                first=rs.getString("first_name");
                last=rs.getString("last_name");
                onCall=rs.getBoolean("on_call");
            }
            rs.close();
            pstmt.close();
            conn.close();
            System.out.println(email);
            studentObj.put("email",email);
            studentObj.put("password",password);
            studentObj.put("first_name",first);
            studentObj.put("last_name", last);
            studentObj.put("onCall",onCall);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentObj;
    }

    public JSONObject insertStudent(Student student){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_info(email, password, on_call,first_name, last_name) Values(?,?,?, ?, ?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getEmail());
            pstmt.setString(2,student.getPassword());
            pstmt.setBoolean(3,student.isOn_call());
            pstmt.setString(4,student.getFirst_name());
            pstmt.setString(5,student.getLast_name());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(student.toString(),"Inserted");
            insertedStudent.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedStudent;
    }

    public JSONObject insertCompany(Company company){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_company_info(email, address, website_link,company_name, password) Values(?,?,?, ?, ?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, company.getEmail());
            pstmt.setString(2,company.getAddress());
            pstmt.setString(3,company.getWebsite_link());
            pstmt.setString(4,company.getCompany_name());
            pstmt.setString(5,company.getPassword());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(company.toString(),"Inserted");
            insertedStudent.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedStudent;
    }


}
