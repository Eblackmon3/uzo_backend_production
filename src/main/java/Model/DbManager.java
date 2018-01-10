package Model;


import AmazonController.s3Operations;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;

import static AmazonController.s3Operations.uploadFile;

public class DbManager {
    /*
        e
     */

    public JSONObject getStudentById(int id){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String first="";String last="";
        String university=""; String resume_location="";
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
                first=rs.getString("first_name");
                last=rs.getString("last_name");
                university=rs.getString("university");
                resume_location=rs.getString("resume_location");
            }
            rs.close();
            pstmt.close();
            conn.close();
            studentObj.put("email",email);
            studentObj.put("first_name",first);
            studentObj.put("last_name", last);
            studentObj.put("university",university);
            studentObj.put("resume_location",resume_location);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                studentObj.put("Error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }

        return studentObj;
    }

    public JSONObject insertStudent(Student student){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_info(email, password, first_name, last_name, university," +
                "phone_number) Values(?,?,?, ?,?,?);";
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
            pstmt.setString(3,student.getFirst_name());
            pstmt.setString(4,student.getLast_name());
            pstmt.setString(5,student.getUniversity());
            pstmt.setString(6,student.getPhone_number());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(student.toString(),"Inserted");
            insertedStudent.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("Error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }

        }
        return insertedStudent;
    }

    public JSONObject getCompanyById(int id){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_company_info where company_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String address="";String website_link="";
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
                address=rs.getString("address");
                website_link=rs.getString("website_link");
                company_name=rs.getString("company_name");
            }
            rs.close();
            pstmt.close();
            conn.close();
            companyObj.put("email",email);
            companyObj.put("company_name",company_name);
            companyObj.put("website_link", website_link);
            companyObj.put("address",address);

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
                    selectedStudentJob=new JSONObject();
                }

            }

            pstmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return selectedJobs;
    }


    public JSONArray getJobStudentList(Job job){
        //One of the students that are associated with a job
        JSONObject selectedJobsStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String resume_location;
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_student_job_map where job_id =?";
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
            pstmt.setInt(1, job.getJob_id());
            rs= pstmt.executeQuery();

            while(rs.next()){
                jobStudentID=rs.getInt("student_id");
                jobsStudents.add(jobStudentID);
            }

            pstmt = conn.prepareStatement(sql2);
            System.out.println(jobsStudents.size());
            for(int i=0;i<jobsStudents.size();i++){
                pstmt.setInt(1, jobsStudents.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){
                    student_id=rs.getInt("student_id");
                    email=rs.getString("email");
                    first_name=rs.getString("first_name");
                    last_name=rs.getString("last_name");
                    university=rs.getString("university");
                    resume_location=rs.getString("resume_location");
                    selectedJobsStudent.put("student_id",student_id);
                    selectedJobsStudent.put("email",email);
                    selectedJobsStudent.put("first_name",first_name);
                    selectedJobsStudent.put("last_name",last_name);
                    selectedJobsStudent.put("university",university);
                    selectedJobsStudent.put("resume_location",resume_location);
                    selectedStudents.put(selectedJobsStudent);
                    selectedJobsStudent=new JSONObject();

                }

            }

            pstmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return selectedStudents;
    }

    public JSONObject insertStudentOnCall(JobOnCall onCall){
        JSONObject insertedOncall= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_job_on_call(student_id, job_id) Values(?,?);";
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
            pstmt.setInt(1, onCall.getStudent_id());
            pstmt.setInt(2,onCall.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedOncall.put(onCall.toString(),"Inserted");
            insertedOncall.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedOncall;
    }
    public JSONArray getSudentsOncallJobs(Student student){
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
        String sql="select * from t_job_on_call where student_id =?";
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
                    selectedStudentJob= new JSONObject();

                }

            }

            pstmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return selectedJobs;
    }

    public JSONArray getJobsOnCallStudents(Job job){
        //One of the students that are associated with a job
        JSONObject selectedJobsStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String resume_location;
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_job_on_call where job_id =?";
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
            pstmt.setInt(1, job.getJob_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                jobStudentID=rs.getInt("student_id");
                jobsStudents.add(jobStudentID);
            }

            pstmt = conn.prepareStatement(sql2);
            System.out.println(jobsStudents.size());
            for(int i=0;i<jobsStudents.size();i++){
                pstmt.setInt(1, jobsStudents.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){
                    student_id=rs.getInt("student_id");
                    email=rs.getString("email");
                    first_name=rs.getString("first_name");
                    last_name=rs.getString("last_name");
                    university=rs.getString("university");
                    resume_location=rs.getString("resume_location");

                    selectedJobsStudent.put("student_id",student_id);
                    selectedJobsStudent.put("email",email);
                    selectedJobsStudent.put("first_name",first_name);
                    selectedJobsStudent.put("last_name",last_name);
                    selectedJobsStudent.put("university",university);
                    selectedJobsStudent.put("resume_location",resume_location);
                    selectedStudents.put(selectedJobsStudent);
                    selectedJobsStudent= new JSONObject();

                }

            }

            pstmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return selectedStudents;

    }

    public JSONArray getStudentsByCompany(Company company ){
        //One of the students that are associated with a job
        JSONObject selectedJobsStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String resume_location;
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_student_job_map where company_id =?";
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
            pstmt.setInt(1, company.getCompany_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                jobStudentID=rs.getInt("student_id");
                jobsStudents.add(jobStudentID);
            }

            pstmt = conn.prepareStatement(sql2);
            System.out.println(jobsStudents.size());
            for(int i=0;i<jobsStudents.size();i++){
                pstmt.setInt(1, jobsStudents.get(i));
                rs= pstmt.executeQuery();
                while(rs.next()){
                    student_id=rs.getInt("student_id");
                    email=rs.getString("email");
                    first_name=rs.getString("first_name");
                    last_name=rs.getString("last_name");
                    university=rs.getString("university");
                    resume_location=rs.getString("resume_location");

                    selectedJobsStudent.put("student_id",student_id);
                    selectedJobsStudent.put("email",email);
                    selectedJobsStudent.put("first_name",first_name);
                    selectedJobsStudent.put("last_name",last_name);
                    selectedJobsStudent.put("university",university);
                    selectedJobsStudent.put("resume_location",resume_location);
                    selectedStudents.put(selectedJobsStudent);
                    selectedJobsStudent=new JSONObject();

                }

            }

            pstmt.close();
            conn.close();

        }catch(Exception e){
            e.printStackTrace();

        }
        return selectedStudents;



    }


    public JSONObject updateStudentUniversity(Student student ){
        JSONObject updateUniversity= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_info set university=? where student_id=?";
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
            pstmt.setString(1, student.getUniversity());
            pstmt.setInt(2,student.getStudent_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            updateUniversity.put("Student:"+student.getStudent_id()+" "+student.getUniversity(), "University Updated");
            updateUniversity.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return updateUniversity;
    }

    public JSONObject addRating(Student student){
        JSONObject updateUniversity= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        int previousRating=0;
        int previousTimesRated=0;
        String sql="select * from t_student_info where student_id =?";
        String sql2="update t_student_info set total_rating=?, times_rated=? where student_id=?;";
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
            pstmt.setInt(1, student.getStudent_id());
            rs= pstmt.executeQuery();
            while(rs.next()){
                previousRating=rs.getInt("total_rating");
                previousTimesRated=rs.getInt("times_rated");
            }

            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, previousRating+student.getTotal_rating());
            pstmt.setInt(2, previousTimesRated+1);
            pstmt.setInt(3, student.getStudent_id());
            affectedRows=pstmt.executeUpdate();

            pstmt.close();
            conn.close();
            updateUniversity.put("Student:"+student.getStudent_id(), "Rating updated");
            updateUniversity.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return updateUniversity;

    }


    public JSONObject getStudentAvgRating(Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        int total_rating=1;
        int times_rated=1;
        double averageRating;
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
            pstmt.setInt(1,student.getStudent_id());
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                total_rating=rs.getInt("total_rating");
                times_rated=rs.getInt("times_rated");
            }
            averageRating=(double)total_rating/(double)times_rated;
            rs.close();
            pstmt.close();
            conn.close();
            studentObj.put("average_rating",averageRating);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentObj;

    }

    public JSONObject insertJobCaptain(StudentJob studentJob){
        JSONObject insertedCaptain= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_job_info set captain= ? where job_id=?;";
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
            pstmt.setInt(1, studentJob.getStudent_id());
            pstmt.setInt(2, studentJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedCaptain.put("Student Captain:"+studentJob.getStudent_id(),"Inserted");
            insertedCaptain.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedCaptain;

    }

    public JSONObject insertJobCoCaptain(StudentJob studentJob){
        JSONObject insertedCaptain= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_job_info set co_captain= ? where job_id=?;";
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
            pstmt.setInt(1, studentJob.getStudent_id());
            pstmt.setInt(2, studentJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedCaptain.put("Student CoCaptain:"+studentJob.getStudent_id(),"Inserted");
            insertedCaptain.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return insertedCaptain;

    }

    public JSONObject getJobById(Job job){
        JSONObject selectedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        int job_id;
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
        int captain;
        int co_captain;
        DbConn jdbcObj = new DbConn();
        String sql= "select * from t_job_info where job_id=?";
        try {
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, job.getJob_id());
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
                captain=rs.getInt("captain");
                co_captain=rs.getInt("co_captain");
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
                selectedStudentJob.put("captain", captain);
                selectedStudentJob.put("co_captain",co_captain);
            }
            pstmt.close();
            conn.close();

        }catch( Exception e){
            e.printStackTrace();

        }


        return  selectedStudentJob;
    }

    public JSONObject uploadStudentResume(MultipartFile file, int student_id){
        String resume_location=s3Operations.uploadFile(student_id,file);
        JSONObject uploadeResume= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_info set resume_location=? where student_id=?";
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
            pstmt.setString(1, resume_location);
            pstmt.setInt(2,student_id);
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            uploadeResume.put("Student:"+student_id, "resume location:"+resume_location);
            uploadeResume.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();

        }
        return uploadeResume;

    }

    public JSONObject checkStudentEmail( Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where email=?";
        DbConn jdbcObj = new DbConn();

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
            pstmt.setString(1,student.getEmail());
            ResultSet rs= pstmt.executeQuery();
            if(rs.next()){
                studentObj.put("Student Email","Student email exist");

            }else{
                studentObj.put("Student Email","Student email does not exist ");

            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentObj;

    }

    public JSONObject checkStudentPassword( Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where email=? and password=?";
        DbConn jdbcObj = new DbConn();

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
            pstmt.setString(1,student.getEmail());
            pstmt.setString(2,student.getPassword());
            ResultSet rs= pstmt.executeQuery();
            if(rs.next()){
                studentObj.put("Student Email","Student login exist");

            }else{
                studentObj.put("Student Email","Student login does not exist ");

            }
            rs.close();
            pstmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentObj;

    }


}
