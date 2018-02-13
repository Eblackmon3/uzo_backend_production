package Model.DataManagers;

import AmazonController.s3Operations;
import Model.DataObjects.*;
import Model.DbConn;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class StudentManager {

    public JSONObject getStudentById(Student student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_info where student_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String first="";String last="";
        String university=""; String phone_number=""; String address="";
        String date_of_birth= ""; String major=""; int year=0;
        JSONObject studentObj= new JSONObject();
        try {
            if(student.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
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
                email=rs.getString("email");
                first=rs.getString("first_name");
                last=rs.getString("last_name");
                university=rs.getString("university");
                phone_number= rs.getString("phone_number");
                address=rs.getString("address");
                date_of_birth=rs.getString("date_of_birth");
                major=rs.getString("major");
                year= rs.getInt("year");
            }
            rs.close();
            pstmt.close();
            conn.close();
            studentObj.put("email",email);
            studentObj.put("first_name",first);
            studentObj.put("last_name", last);
            studentObj.put("university",university);
            studentObj.put("phone_number",phone_number);
            studentObj.put("address",address);
            studentObj.put("date_of_birth", date_of_birth);
            studentObj.put("major",major);
            studentObj.put("year",year);


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
                "phone_number, address, date_of_birth, major, year) Values(?,?,?, ?,?,?, ?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(student.getEmail()==null || student.getPassword()==null ||student.getFirst_name()==null ||
                    student.getLast_name()==null || student.getUniversity()==null || student.getDate_of_birth()==null
                    ||student.getAddress()==null|| student.getMajor()==null ||student.getYear()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, student.getEmail().toLowerCase());
            pstmt.setString(2,student.getPassword());
            pstmt.setString(3,student.getFirst_name().toLowerCase());
            pstmt.setString(4,student.getLast_name().toLowerCase());
            pstmt.setString(5,student.getUniversity());
            pstmt.setString(6,student.getPhone_number());
            pstmt.setString(7,student.getAddress().toLowerCase());
            pstmt.setString(8,student.getDate_of_birth().toLowerCase());
            pstmt.setString(9,student.getMajor());
            pstmt.setInt(10,student.getYear());
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


    public JSONObject assignStudentJob(StudentJob studJob){
        JSONObject insertedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs;
        String sql="select * from t_student_job_map where student_id=? and company_id=? and job_id= ?";
        String sql2="insert into t_student_job_map(student_id,company_id, job_id) " +
                "Values(?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if (studJob.getCompany_id()==0||studJob.getJob_id()==0 || studJob.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2, studJob.getCompany_id());
            pstmt.setInt(3,studJob.getJob_id());
            rs= pstmt.executeQuery();
            if(rs.next()) {
                insertedStudentJob.put("Student id:"+studJob.getStudent_id(), "Already assigned to this job");
                return insertedStudentJob;
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, studJob.getStudent_id());
            pstmt.setInt(2, studJob.getCompany_id());
            pstmt.setInt(3,studJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            rs.close();
            pstmt.close();
            conn.close();
            insertedStudentJob.put(studJob.toString(),"Inserted");
            insertedStudentJob.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedStudentJob.put("Error",e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }

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
            if(studJob.getStudent_id()==0|| studJob.getJob_id()==0){
                throw new Exception("Missing Parameter");
            }
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
            try{
                deletedStudentJob.put("Error",e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

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
        String description;
        String sql2= "select * from t_job_info where job_id=?";
        String sql="select * from t_student_job_map where student_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(student.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
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
                    description=rs.getString("description");
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
                    selectedStudentJob.put("description", description);
                    selectedJobs.put(selectedStudentJob);
                    selectedStudentJob=new JSONObject();
                }

            }

            pstmt.close();
            conn.close();
            rs.close();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedStudentJob.put("Error", e.toString());
                System.out.println(selectedStudentJob.toString());
                selectedJobs= new JSONArray();
                selectedJobs.put(selectedStudentJob);
                return selectedJobs;

            }catch(Exception f){
                f.printStackTrace();
            }

        }
        return selectedJobs;
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
            if(onCall.getJob_id()==0|| onCall.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
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
            try{
                insertedOncall.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

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
        String description;
        String sql2= "select * from t_job_info where job_id=?";
        String sql="select * from t_job_on_call where student_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(student.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
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
                    description= rs.getString("description");
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
                    selectedStudentJob.put("description",description);
                    selectedStudentJob.put("time", time);
                    selectedJobs.put(selectedStudentJob);
                    selectedStudentJob= new JSONObject();

                }

            }

            pstmt.close();
            conn.close();
            rs.close();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedJobs= new JSONArray();
                selectedStudentJob.put("Error", e.toString());
                selectedJobs.put(selectedStudentJob);

            }catch(Exception f){
                f.printStackTrace();
            }

        }
        return selectedJobs;
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

            if(student.getStudent_id()==0 ||student.getUniversity()==null){
                throw new Exception("Missing Parameter");
            }
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
            try{
                updateUniversity.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

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
            if(student.getStudent_id()==0|| student.getTotal_rating()==0){
                throw new Exception("Missing Parameter");
            }
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
            rs.close();
            updateUniversity.put("Student:"+student.getStudent_id(), "Rating updated");
            updateUniversity.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                updateUniversity.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

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
            if(student.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
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
            try {
                studentObj.put("Error", e.toString());
            }catch(Exception f){
                f.printStackTrace();
            }
        }

        return studentObj;

    }



    public JSONObject uploadStudentFile(MultipartFile file, int student_id){
        String resume_location= s3Operations.uploadStudentFile(student_id,file);
        JSONObject uploadeResume= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_student_work_history set resume_location=? where student_id=?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(file==null||student_id==0|| file.isEmpty()){
                throw new Exception("Missing Parameter");

            }
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
            try{
                uploadeResume.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();

            }

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
            if(student.getEmail()==null){
                throw new Exception("Missing Parameter");
            }
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
            try{
                studentObj.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }
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
            if(student.getStudent_id()==0|| student.getEmail()==null){
                throw new Exception("Missing Parameter");
            }
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
            try{
                studentObj.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }
        }

        return studentObj;

    }

    public JSONObject insertStudentAvailability(StudentAvailabilitySlot studentAvail){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;

        try{
            if(studentAvail.getStudent_id()==0||studentAvail.getDay()==null||studentAvail.getTime()==null){
                throw new Exception( "Missing Parameters");
            }
            String tableName= StringEscapeUtils.escapeJava("t_"+studentAvail.getDay().toLowerCase());
            String sql= "select * from "+tableName+" where student_id=?";
            studentAvail.setTime("\""+StringEscapeUtils.escapeJava(studentAvail.getTime())+"\"");
            String sql2="insert into " +tableName+"("+studentAvail.getTime()+",student_id) Values(?,?);";
            DbConn jdbcObj = new DbConn();
            int affectedRows=0;
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt= conn.prepareStatement(sql);
            pstmt.setInt(1,studentAvail.getStudent_id());
            rsObj=pstmt.executeQuery();
            if(rsObj.next()){
                insertedStudent.put(""+studentAvail.getStudent_id(),"student already inserted please use update_student_availability");
                return insertedStudent;
            }
            pstmt = conn.prepareStatement(sql2);
            pstmt.setBoolean(1,studentAvail.isAvailable());
            pstmt.setInt(2,studentAvail.getStudent_id());
            System.out.print(pstmt.toString());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(""+studentAvail.getStudent_id(),"Availability set for:"+studentAvail.getDay()
                    +" at "+studentAvail.getTime() );

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


    public JSONObject updateStudentAvailability(StudentAvailabilitySlot studentAvail){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try{
            if(studentAvail.getStudent_id()==0||studentAvail.getDay()==null||studentAvail.getTime()==null){
                throw new Exception( "Missing Parameters");
            }
            String tableName= StringEscapeUtils.escapeJava("t_"+studentAvail.getDay().toLowerCase());
            studentAvail.setTime("\""+StringEscapeUtils.escapeJava(studentAvail.getTime())+"\"");
            //﻿﻿update t_friday set "0000"=true where student_id =1;
            String sql2="update  " +tableName+" set "+studentAvail.getTime()+"=? where student_id=?";
            DbConn jdbcObj = new DbConn();
            int affectedRows=0;
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql2);
            pstmt.setBoolean(1,studentAvail.isAvailable());
            pstmt.setInt(2,studentAvail.getStudent_id());
            System.out.print(pstmt.toString());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(""+studentAvail.getStudent_id(),"Availability set for:"+studentAvail.getDay()
                    +" at "+studentAvail.getTime() );

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


    public JSONObject insertInterestedStudent(InterestedStudent interestedStudent){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_interested_students_jobs(student_id, job_id) Values(?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(interestedStudent.getJob_id()==0|| interestedStudent.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, interestedStudent.getStudent_id());
            pstmt.setInt(2, interestedStudent.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(interestedStudent.toString(),"Inserted");
            insertedStudent.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedStudent.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }
        return insertedStudent;
    }


    public JSONObject removeInterestedStudent(InterestedStudent interestedStudent){
        JSONObject deletedInterestedStudent= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="delete from t_interested_students_jobs where student_id =? and job_id =?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(interestedStudent.getStudent_id()==0|| interestedStudent.getJob_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, interestedStudent.getStudent_id());
            pstmt.setInt(2,interestedStudent.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            deletedInterestedStudent.put(interestedStudent.toString(),"deleted");
            deletedInterestedStudent.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                deletedInterestedStudent.put("Error",e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }
        return deletedInterestedStudent;

    }


    public JSONObject setStudentJobPreference(StudentJobPreference studentJobPreference){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_student_job_preferences(student_id, uzo_reason, lift_ability,mobility) Values(?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(studentJobPreference.getStudent_id()==0 || studentJobPreference.getMobility()==null |
                    studentJobPreference.getUzo_reason()==null){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentJobPreference.getStudent_id());
            pstmt.setString(2,studentJobPreference.getUzo_reason());
            pstmt.setBoolean(3,studentJobPreference.isLift_ability());
            pstmt.setString(4,studentJobPreference.getMobility().toLowerCase());

            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(studentJobPreference.toString(),"Inserted");
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

    public JSONObject getStudentJobPreference(StudentJobPreference student){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_job_preferences where student_id=?";
        DbConn jdbcObj = new DbConn();
        String uzo_reason=""; boolean lift_ability=false ;String mobility="";
        JSONObject studentObj= new JSONObject();
        try {
            if(student.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations heremobility
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,student.getStudent_id());
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                uzo_reason=rs.getString("uzo_reason");
                lift_ability=rs.getBoolean("lift_ability");
                mobility=rs.getString("mobility");

            }
            rs.close();
            pstmt.close();
            conn.close();
            studentObj.put("uzo_reason",uzo_reason);
            studentObj.put("lift_ability",lift_ability);
            studentObj.put("mobility", mobility);



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

    public JSONObject insertStudentWorkAbility(StudentWorkAbility studentWorkAbility) {
        JSONObject insertedStudent = new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into t_student_work_ability(student_id, bar, cashier,cleaning,data_entry,desk_assistant" +
                ",driving_delivery,event_security,setup_breakdown,food_service,moving) Values(?,?,?,?,?  ,?," +
                "?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows = 0;
        try {
            if (studentWorkAbility.getStudent_id() == 0) {
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentWorkAbility.getStudent_id());
            pstmt.setBoolean(2, studentWorkAbility.isBar());
            pstmt.setBoolean(3, studentWorkAbility.isCashier());
            pstmt.setBoolean(4, studentWorkAbility.isCleaning());
            pstmt.setBoolean(5, studentWorkAbility.isData_entry());
            pstmt.setBoolean(6, studentWorkAbility.isDesk_assistant());
            pstmt.setBoolean(7, studentWorkAbility.isDriving_delivery());
            pstmt.setBoolean(8, studentWorkAbility.isEvent_security());
            pstmt.setBoolean(9, studentWorkAbility.isSetup_breakdown());
            pstmt.setBoolean(10, studentWorkAbility.isFood_service());
            pstmt.setBoolean(11, studentWorkAbility.isMoving());


            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(studentWorkAbility.toString(), "Inserted");
            insertedStudent.put("affected Rows", affectedRows);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                insertedStudent.put("Error", e.toString());
            } catch (Exception f) {
                f.printStackTrace();
            }

        }
        return insertedStudent;
    }

    public JSONObject getStudentWorkAbility(StudentWorkAbility studentWorkAbility) {
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_work_ability where student_id=?";
        DbConn jdbcObj = new DbConn();
        boolean bar= false; boolean cashier=false; boolean cleaning=false;
        boolean data_entry=false; boolean desk_assistant=false; boolean driving_delivery=false;
        boolean event_security=false; boolean setup_breakdown=false; boolean food_service=false;
        boolean moving=false;
        JSONObject studentObj= new JSONObject();
        try {
            if(studentWorkAbility.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,studentWorkAbility.getStudent_id());
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                bar=rs.getBoolean("bar");
                cashier=rs.getBoolean("cashier");
                cleaning=rs.getBoolean("cleaning");
                data_entry=rs.getBoolean("data_entry");
                desk_assistant= rs.getBoolean("desk_assistant");
                driving_delivery=rs.getBoolean("driving_delivery");
                event_security=rs.getBoolean("event_security");
                setup_breakdown=rs.getBoolean("setup_breakdown");
                food_service= rs.getBoolean("food_service");
                food_service= rs.getBoolean("moving");
            }
            rs.close();
            pstmt.close();
            conn.close();
            studentObj.put("bar",bar);
            studentObj.put("cashier",cashier);
            studentObj.put("cleaning", cleaning);
            studentObj.put("data_entry",data_entry);
            studentObj.put("desk_assistant",desk_assistant);
            studentObj.put("driving_delivery",driving_delivery);
            studentObj.put("event_security", event_security);
            studentObj.put("setup_breakdown",setup_breakdown);
            studentObj.put("moving",moving);
            studentObj.put("food_service",food_service);


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

    public JSONObject insertStudentWorkHistory(StudentWorkHistory studentWorkHistory) {
        JSONObject insertedStudent = new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into t_student_work_history(student_id, work_reference_1, work_reference_2,work_reference_3,crime,hear_uzo) Values(?,?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows = 0;
        try {
            if (studentWorkHistory.getStudent_id() == 0 || studentWorkHistory.getWork_reference_1()==null ||
                    studentWorkHistory.getWork_reference_2()==null) {
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, studentWorkHistory.getStudent_id());
            pstmt.setString(2, studentWorkHistory.getWork_reference_1());
            pstmt.setString(3, studentWorkHistory.getWork_reference_2());
            pstmt.setString(4, studentWorkHistory.getWork_reference_3());
            pstmt.setBoolean(5, studentWorkHistory.isCrime());
            pstmt.setString(6,studentWorkHistory.getHear_uzo());


            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put(studentWorkHistory.toString(), "Inserted");
            insertedStudent.put("affected Rows", affectedRows);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                insertedStudent.put("Error", e.toString());
            } catch (Exception f) {
                f.printStackTrace();
            }

        }
        return insertedStudent;
    }



    public JSONObject getStudentWorkHistory(StudentWorkHistory studentWorkHistory) {
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_student_work_history where student_id=?";
        DbConn jdbcObj = new DbConn();
        String work_reference_1= ""; String work_reference_2=""; String work_reference_3= "";
        boolean crime=false; String hear_uzo=""; String resume_location="";

        JSONObject studentObj= new JSONObject();
        try {
            if(studentWorkHistory.getStudent_id()==0){
                throw new Exception("Missing Parameter");
            }
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            conn = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,studentWorkHistory.getStudent_id());
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                work_reference_1=rs.getString("work_reference_1");
                work_reference_2=rs.getString("work_reference_2");
                work_reference_3=rs.getString("work_reference_3");
                crime=rs.getBoolean("crime");
                hear_uzo=rs.getString("hear_uzo");
                resume_location=rs.getString("resume_location");
            }
            rs.close();
            pstmt.close();
            conn.close();
            studentObj.put("work_reference_1",work_reference_1);
            studentObj.put("work_reference_2",work_reference_2);
            studentObj.put("work_reference_3", work_reference_3);
            studentObj.put("crime",crime);
            studentObj.put("hear_uzo",hear_uzo);
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


}
