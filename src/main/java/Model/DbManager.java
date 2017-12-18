package Model;


import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;

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

    public JSONObject getCompanyById(int id){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_company_info where company_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String password="";String address="";String website_link="";
        String company_name="";
        JSONObject companyObj= new JSONObject();
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
                address=rs.getString("address");
                website_link=rs.getString("website_link");
                company_name=rs.getString("company_name");
            }
            rs.close();
            pstmt.close();
            conn.close();
            companyObj.put("email",email);
            companyObj.put("password",password);
            companyObj.put("first_name",company_name);
            companyObj.put("last_name", website_link);
            companyObj.put("onCall",address);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return companyObj;
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


    public JSONObject insertJob(JobInsert jobInsert){
        JSONObject insertedJob= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_job_info(completed, date,rate,dress_code,duration,open,job_title, time, company_id) " +
                "Values(?,?, ?, ?,?,?,?,?,?);";
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
            pstmt.setBoolean(1, jobInsert.isCompleted());
            pstmt.setDate(2, Date.valueOf(jobInsert.getDate()));
            pstmt.setString(3,jobInsert.getRate());
            pstmt.setString(4,jobInsert.getDress_code());
            pstmt.setDouble(5,jobInsert.getDuration());
            pstmt.setBoolean(6, jobInsert.isOpen());
            pstmt.setString(7,jobInsert.getJob_title());
            pstmt.setInt(8,jobInsert.getTime());
            pstmt.setInt(9,jobInsert.getCompany_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedJob.put(jobInsert.toString(),"Inserted");
            insertedJob.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedJob;

    }


    public JSONObject assignStudentJob(StudentJob studJob){
        JSONObject insertedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_job_map(student_id,company_id, job_id) " +
                "Values(?,?,?);";
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
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2, studJob.getCompany_id());
            pstmt.setInt(3,studJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudentJob.put(studJob.toString(),"Inserted");
            insertedStudentJob.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedStudentJob;



    }



    public JSONObject removeStudentJob(StudentJob studJob){
        JSONObject deletedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="delete from t_student_job_map where student_id =? and job_id =?";
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
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2,studJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            deletedStudentJob.put(studJob.toString(),"deleted");
            deletedStudentJob.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return deletedStudentJob;

    }

    public JSONArray getStudentJobList(Student student){
        JSONObject selectedStudentJob= new JSONObject();
        JSONArray selectedJobs= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        ArrayList<Integer> studentsJobs= new ArrayList<>();
        int job_id;
        int studentJobID;
        boolean completed;
        Date date;
        String rate;
        String dress_code;
        double duration;
        boolean open;
        Time clock_out;
        Time clock_in;
        String job_title;
        int time;
        int company_id;
        String sql2= "select * from t_job_info where job_id=?";
        String sql="select * from t_student_job_map where student_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                studentJobID=rs.getInt("job_id");
                studentsJobs.add(studentJobID);
            }

            pstmt = conn.prepareStatement(sql2);
            for(int i=0;i<studentsJobs.size();i++){
                pstmt.setInt(1, studentsJobs.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){
                    job_id=rs.getInt("job_id");
                    completed=rs.getBoolean("completed");
                    date=rs.getDate("date");
                    rate=rs.getString("rate");
                    dress_code= rs.getString("dress_code");
                    duration = rs.getDouble("duration");
                    open= rs.getBoolean("open");
                    clock_out= rs.getTime("clock_out");
                    clock_in=rs.getTime("clock_in");
                    job_title= rs.getString("job_title");
                    company_id=rs.getInt("company_id");
                    time=rs.getInt("time");
                    selectedStudentJob.put("job_id",job_id);
                    selectedStudentJob.put("completed",completed);
                    selectedStudentJob.put("date",date);
                    selectedStudentJob.put("rate",rate);
                    selectedStudentJob.put("dress_code",dress_code);
                    selectedStudentJob.put("duration",duration);
                    selectedStudentJob.put("open", open);
                    selectedStudentJob.put("clock_out", clock_out);
                    selectedStudentJob.put("clock_in", clock_in);
                    selectedStudentJob.put("job_title", job_title);
                    selectedStudentJob.put("company_id",company_id);
                    selectedStudentJob.put("time", time);
                    selectedJobs.put(selectedStudentJob);

                }

            }

            pstmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return selectedJobs;
    }

    /*
    public JSONArray getStudentJobList(Job job){

        //TODO: complete method

    }
    */




}
