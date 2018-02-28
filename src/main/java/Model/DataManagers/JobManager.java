package Model.DataManagers;

import AmazonController.s3Operations;
import Model.DataObjects.InterestedStudent;
import Model.DataObjects.Job;
import Model.DataObjects.JobInsert;
import Model.DataObjects.StudentJob;
import Model.DbConn;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;

public class JobManager {

    public JSONObject insertJob(JobInsert jobInsert){
        JSONObject insertedJob= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_job_info(completed, date,rate,dress_code,duration,open,job_title, time, company_id, description) " +
                "Values(?,?, ?, ?,?,?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(jobInsert.getDate()==null || jobInsert.getRate()==null || jobInsert.getDress_code()==null
                    ||  jobInsert.getJob_title()==null|| jobInsert.getCompany_id()==0 || jobInsert.getDescription()==null){
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
            pstmt.setBoolean(1, jobInsert.isCompleted());
            pstmt.setDate(2, Date.valueOf(jobInsert.getDate()));
            pstmt.setString(3,jobInsert.getRate().toLowerCase());
            pstmt.setString(4,jobInsert.getDress_code().toLowerCase());
            pstmt.setDouble(5,jobInsert.getDuration());
            pstmt.setBoolean(6, jobInsert.isOpen());
            pstmt.setString(7,jobInsert.getJob_title().toLowerCase());
            pstmt.setInt(8,jobInsert.getTime());
            pstmt.setInt(9,jobInsert.getCompany_id());
            pstmt.setString(10,jobInsert.getDescription());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedJob.put(jobInsert.toString(),"Inserted");
            insertedJob.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedJob.put("Error", e.toString());
            }catch (Exception f){
                f.printStackTrace();
            }

        }finally{
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return insertedJob;

    }

    public JSONArray getJobStudentList(Job job){
        //One of the students that are associated with a job
        JSONObject selectedJobsStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String phone_number=""; String state="";  String street="";  String city ="";  String apt="";
        String date_of_birth= ""; String major=""; int year=0;
        String description=""; String zipcode="";
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_student_job_map where job_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(job.getJob_id()==0){
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
                    phone_number= rs.getString("phone_number");
                    state=rs.getString("state");
                    street=rs.getString("street");
                    city=rs.getString("city");
                    apt=rs.getString("apt");
                    date_of_birth=rs.getString("date_of_birth");
                    major=rs.getString("major");
                    year= rs.getInt("year");
                    zipcode= rs.getString("zipcode");
                    description=rs.getString("description");
                    selectedJobsStudent.put("student_id",student_id);
                    selectedJobsStudent.put("email",email);
                    selectedJobsStudent.put("first_name",first_name);
                    selectedJobsStudent.put("last_name",last_name);
                    selectedJobsStudent.put("university",university);
                    selectedJobsStudent.put("phone_number",phone_number);
                    selectedJobsStudent.put("street",street);
                    selectedJobsStudent.put("state",state);
                    selectedJobsStudent.put("city",city);
                    selectedJobsStudent.put("apt",apt);
                    selectedJobsStudent.put("date_of_birth",date_of_birth);
                    selectedJobsStudent.put("major",major);
                    selectedJobsStudent.put("year",year);
                    selectedJobsStudent.put("description",description);
                    selectedJobsStudent.put("zipcode",zipcode);
                    selectedStudents.put(selectedJobsStudent);
                    selectedJobsStudent=new JSONObject();

                }

            }

            pstmt.close();
            conn.close();
            rs.close();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedJobsStudent.put("Error", e.toString());
                selectedStudents=new JSONArray();
                selectedStudents.put(selectedJobsStudent);

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return selectedStudents;
    }


    public JSONArray getJobsOnCallStudents(Job job){
        //One of the students that are associated with a job
        JSONObject selectedJobsStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String phone_number=""; String street="";String state=""; String city=""; String apt="";
        String date_of_birth= ""; String major=""; int year=0;
        String description=""; String zipcode="";
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_job_on_call where job_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(job.getJob_id()==0){
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
                    phone_number= rs.getString("phone_number");
                    state=rs.getString("state");
                    street=rs.getString("street");
                    city=rs.getString("city");
                    apt=rs.getString("apt");
                    date_of_birth=rs.getString("date_of_birth");
                    major=rs.getString("major");
                    year= rs.getInt("year");
                    description= rs.getString("description");
                    zipcode= rs.getString("zipcode");

                    selectedJobsStudent.put("student_id",student_id);
                    selectedJobsStudent.put("email",email);
                    selectedJobsStudent.put("first_name",first_name);
                    selectedJobsStudent.put("last_name",last_name);
                    selectedJobsStudent.put("university",university);
                    selectedJobsStudent.put("phone_number",phone_number);
                    selectedJobsStudent.put("street",street);
                    selectedJobsStudent.put("state",state);
                    selectedJobsStudent.put("city",city);
                    selectedJobsStudent.put("apt",apt);
                    selectedJobsStudent.put("date_of_birth",date_of_birth);
                    selectedJobsStudent.put("major",major);
                    selectedJobsStudent.put("year",year);
                    selectedJobsStudent.put("description",description);
                    selectedJobsStudent.put("zipcode",zipcode);

                    selectedStudents.put(selectedJobsStudent);
                    selectedJobsStudent= new JSONObject();

                }

            }

            pstmt.close();
            conn.close();
            rs.close();

        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedStudents= new JSONArray();
                selectedJobsStudent.put("Error", e.toString());
                selectedStudents.put(selectedJobsStudent);

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return selectedStudents;

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
            if(studentJob.getJob_id()==0||studentJob.getStudent_id()==0){
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
            pstmt.setInt(1, studentJob.getStudent_id());
            pstmt.setInt(2, studentJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedCaptain.put("Student Captain:"+studentJob.getStudent_id(),"Inserted");
            insertedCaptain.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedCaptain.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

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
            if(studentJob.getJob_id()==0||studentJob.getStudent_id()==0){
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
            pstmt.setInt(1, studentJob.getStudent_id());
            pstmt.setInt(2, studentJob.getJob_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedCaptain.put("Student CoCaptain:"+studentJob.getStudent_id(),"Inserted");
            insertedCaptain.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                insertedCaptain.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();
            }

        }return insertedCaptain;

    }

    public JSONObject getJobById(Job job){
        JSONObject selectedStudentJob= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
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
        String description;
        DbConn jdbcObj = new DbConn();
        String sql= "select * from t_job_info where job_id=?";
        try {
            if(job.getJob_id()==0){
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
                selectedStudentJob.put("time", time);
                selectedStudentJob.put("captain", captain);
                selectedStudentJob.put("co_captain",co_captain);
                selectedStudentJob.put("description",description);
            }
            pstmt.close();
            conn.close();
            rs.close();

        }catch( Exception e){
            e.printStackTrace();
            try {
                selectedStudentJob.put("Error", e.toString());
            }catch( Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }


        return  selectedStudentJob;
    }

    public JSONObject removeJobsInterestedStudent(InterestedStudent interestedStudent){
        JSONObject deletedInterestedStudent= new JSONObject();
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="delete from t_interested_students_jobs where job_id =?";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(interestedStudent.getJob_id()==0){
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
            pstmt.setInt(1,interestedStudent.getJob_id());
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

        }finally{
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return deletedInterestedStudent;

    }

    public JSONArray getJobInterestedList(Job job){
        //One of the students that are associated with a job
        JSONObject selectedJobsStudent= new JSONObject();
        //the list of selected students
        JSONArray selectedStudents= new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        //the student IDs for th associated job
        ArrayList<Integer> jobsStudents= new ArrayList<>();
        //holder for the student IDs that are associated witht he job
        int jobStudentID;
        int student_id;
        String email;
        String first_name;
        String last_name;
        String university;
        String phone_number=""; String state=""; String street=""; String city=""; String apt="";
        String date_of_birth= ""; String major=""; int year=0; String zipcode="";
        String description="";
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_interested_students_jobs where job_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(job.getJob_id()==0){
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
                    phone_number= rs.getString("phone_number");
                    street=rs.getString("street");
                    state=rs.getString("state");
                    city=rs.getString("city");
                    apt=rs.getString("apt");
                    date_of_birth=rs.getString("date_of_birth");
                    major=rs.getString("major");
                    year= rs.getInt("year");
                    zipcode=rs.getString("zipcode");
                    description=rs.getString("description");
                    selectedJobsStudent.put("student_id",student_id);
                    selectedJobsStudent.put("email",email);
                    selectedJobsStudent.put("first_name",first_name);
                    selectedJobsStudent.put("last_name",last_name);
                    selectedJobsStudent.put("university",university);
                    selectedJobsStudent.put("phone_number",phone_number);
                    selectedJobsStudent.put("state",state);
                    selectedJobsStudent.put("street",street);
                    selectedJobsStudent.put("city",city);
                    selectedJobsStudent.put("apt",apt);
                    selectedJobsStudent.put("date_of_birth",date_of_birth);
                    selectedJobsStudent.put("major",major);
                    selectedJobsStudent.put("year",year);
                    selectedJobsStudent.put("description",description);
                    selectedJobsStudent.put("zipcode",zipcode);
                    selectedStudents.put(selectedJobsStudent);
                    selectedJobsStudent=new JSONObject();

                }

            }

            pstmt.close();
            conn.close();
            rs.close();



        }catch(Exception e){
            e.printStackTrace();
            try{
                selectedJobsStudent.put("Error", e.toString());
                selectedStudents=new JSONArray();
                selectedStudents.put(selectedJobsStudent);

            }catch(Exception f){
                f.printStackTrace();
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return selectedStudents;
    }


    public JSONObject insertJobResource(MultipartFile file, int job_id){
        String reesource_location= s3Operations.uploadJobFile(job_id,file);
        JSONObject uploadeResource= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_job_resources(resource_location,job_id, file_name) Values(?,?,?)";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(file==null||job_id==0|| file.isEmpty()){
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
            pstmt.setString(1, reesource_location);
            pstmt.setInt(2,job_id);
            pstmt.setString(3, file.getOriginalFilename());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            uploadeResource.put("Job:"+job_id +" resource updated", "resource location:"+reesource_location);
            uploadeResource.put("affected Rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                uploadeResource.put("Error", e.toString());

            }catch(Exception f){
                f.printStackTrace();

            }

        }finally{
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return uploadeResource;

    }

    public JSONArray getJobsResources(Job job) {
        //One of the students that are associated with a job
        JSONObject resource = new JSONObject();
        //the list of selected students
        JSONArray company_resources = new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;

        //holder for the student IDs that are associated witht he job
        String resource_location = "";
        String sql = "select * from t_job_resources where job_id =?";
        DbConn jdbcObj = new DbConn();
        try {
            if (job.getJob_id() == 0) {
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
            pstmt.setInt(1, job.getJob_id());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                resource=new JSONObject();
                resource_location = rs.getString("resource_location");
                resource.put(rs.getString("file_name"), resource_location);
                company_resources.put(resource);
            }
            pstmt.close();
            conn.close();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                company_resources = new JSONArray();
                resource.put("Error", e.toString());
                company_resources.put(resource);

            } catch (Exception f) {
            }

        }finally{
            if(rs!=null){
                try {
                    rs.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            if(pstmt!=null){
                try {
                    pstmt.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if(conn!=null){
                try{
                    conn.close();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }try {
                jdbcObj.closePool();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return company_resources;
    }



}
