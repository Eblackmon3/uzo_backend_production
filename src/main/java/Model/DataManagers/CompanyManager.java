package Model.DataManagers;

import AmazonController.s3Operations;
import Model.DataObjects.Company;
import Model.DataObjects.CompanyRep;
import Model.DataObjects.Student;
import Model.DbConn;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class CompanyManager {

    public JSONObject getCompanyById(Company company){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_company_info where company_id=?";
        DbConn jdbcObj = new DbConn();
        String email="";String state="";String website_link=""; String description="";
        String company_name=""; String street=""; String city=""; String zip_code="";
        JSONObject companyObj= new JSONObject();
        try {
            if(company.getCompany_id()==0){
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
            pstmt.setInt(1, company.getCompany_id());
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                email=rs.getString("email");
                state=rs.getString("state");
                street=rs.getString("street");
                city=rs.getString("city");
                zip_code=rs.getString("zip_code");
                website_link=rs.getString("website_link");
                company_name=rs.getString("company_name");
                description=rs.getString("description");
            }
            rs.close();
            pstmt.close();
            conn.close();
            companyObj.put("email",email);
            companyObj.put("company_name",company_name);
            companyObj.put("website_link", website_link);
            companyObj.put("state",state);
            companyObj.put("street",street);
            companyObj.put("city",city);
            companyObj.put("zip_code",zip_code);
            companyObj.put("description",description);


        } catch (Exception e) {
            try {
                companyObj.put("error", e.toString());
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

        return companyObj;
    }

    public JSONObject insertCompany(Company company){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rs=null;
        ResultSet rsObj = null;
        Connection conn = null;
        int company_id=0;
        PreparedStatement pstmt = null;
        String sql="insert into t_company_info(email,website_link,company_name, password, description, state, street, city, zip_code) Values(?,?,?,  ?, ?,?,  ?,?,?)" +
                "RETURNING company_id;";
        DbConn jdbcObj = new DbConn();
        boolean did_it_work=false;
        try{

            if(company.getEmail()==null|| company.getCity()==null||company.getWebsite_link()==null
                    || company.getCompany_name()==null||company.getPassword()==null ||company.getDescription()==null
                    ||company.getState()==null || company.getStreet()==null || company.getZip_code()==null){
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
            pstmt.setString(1, company.getEmail());
            pstmt.setString(2,company.getWebsite_link());
            pstmt.setString(3,company.getCompany_name());
            pstmt.setString(4,company.getPassword());
            pstmt.setString(5, company.getDescription());
            pstmt.setString(6,company.getState());
            pstmt.setString(7,company.getStreet());
            pstmt.setString(8,company.getCity());
            pstmt.setString(9, company.getZip_code());
            did_it_work = pstmt.execute();
            rs=pstmt.getResultSet();
            while(rs.next()){
                company_id= rs.getInt("company_id");
            }
            rs.close();
            pstmt.close();
            conn.close();
            insertedStudent.put("company_id",company_id);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
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
        return insertedStudent;
    }

    public JSONObject getCompanyRep(CompanyRep company){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="select * from t_company_reps where company_id=?";
        DbConn jdbcObj = new DbConn();
        String position="";
        String position_details="";
        String found_uzo="";
        String uzo_help="";
        String first_name="";
        String last_name="";
        JSONObject companyObj= new JSONObject();
        try {
            if(company.getCompany_id()==0){
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
            pstmt.setInt(1, company.getCompany_id());
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                position=rs.getString("position");
                position_details=rs.getString("position_details");
                found_uzo=rs.getString("found_uzo");
                uzo_help=rs.getString("uzo_help");
                first_name=rs.getString("first_name");
                last_name=rs.getString("last_name");
            }
            rs.close();
            pstmt.close();
            conn.close();
            companyObj.put("position",position);
            companyObj.put("position_details",position_details);
            companyObj.put("found_uzo", found_uzo);
            companyObj.put("uzo_help",uzo_help);
            companyObj.put("first_name",first_name);
            companyObj.put("last_name",last_name);



        } catch (Exception e) {
            try {
                companyObj.put("Error", e.toString());
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

        return companyObj;
    }

    public JSONObject insertCompanyRep(CompanyRep rep){
        JSONObject insertedStudent= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_company_reps(company_id,\"position\", position_details,found_uzo,uzo_help, first_name" +
                ",last_name) Values(?,?,?,?,?,?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{

            if(rep.getCompany_id() ==0|| rep.getFound_uzo()==null||rep.getPosition()==null
                    || rep.getUzo_help()==null|| rep.getFirst_name()==null || rep.getLast_name()==null){
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
            pstmt.setInt(1, rep.getCompany_id());
            pstmt.setString(2,rep.getPosition());
            pstmt.setString(3,rep.getPosition_details());
            pstmt.setString(4,rep.getFound_uzo());
            pstmt.setString(5,rep.getUzo_help());
            pstmt.setString(6,rep.getFirst_name());
            pstmt.setString(7,rep.getLast_name());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedStudent.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedStudent.put("error", e.toString());
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
        return insertedStudent;
    }




    public JSONArray getStudentsByCompany(Company company ){
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
        String phone_number=""; String state=""; String city=""; String street=""; String apt="";
        String date_of_birth= ""; String major=""; int year=0; String zipcode= "";
        String description="";
        String sql2= "select * from t_student_info where student_id=?";
        String sql="select * from t_student_job_map where company_id =?";
        DbConn jdbcObj = new DbConn();
        try{
            if(company.getCompany_id()==0){
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
                    phone_number= rs.getString("phone_number");
                    state=rs.getString("state");
                    street=rs.getString("street");
                    city=rs.getString("city");
                    apt=rs.getString("apt");
                    date_of_birth=rs.getString("date_of_birth");
                    major=rs.getString("major");
                    year= rs.getInt("year");
                    description=rs.getString("description");
                    zipcode= rs.getString("zipcode");

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
                selectedStudents= new JSONArray();
                selectedJobsStudent.put("error", e.toString());
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


    public JSONObject updateCompanyRep(CompanyRep rep){
        JSONObject updateUniversity= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="update t_company_reps set position=?, position_details=?, found_uzo=?, " +
                "uzo_help=?, first_name=?, last_name=? where company_id=?;";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(rep.getCompany_id() ==0|| rep.getFound_uzo()==null||rep.getPosition()==null
                    || rep.getUzo_help()==null|| rep.getFirst_name()==null || rep.getLast_name()==null){
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
            pstmt.setString(1, rep.getPosition());
            pstmt.setString(2,rep.getPosition_details());
            pstmt.setString(3,rep.getFound_uzo());
            pstmt.setString(4, rep.getUzo_help());
            pstmt.setString(5,rep.getFirst_name());
            pstmt.setString(6, rep.getLast_name());
            pstmt.setInt(7,rep.getCompany_id());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            updateUniversity.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                updateUniversity.put("error", e.toString());

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
        return updateUniversity;
    }


    public JSONObject insertCompanyResource(MultipartFile file, int company_id){
        String reesource_location= s3Operations.uploadCompanyFile(company_id,file);
        JSONObject uploadeResource= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_company_resources(resource_location,company_id, file_name) Values(?,?,?)";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(file==null||company_id==0|| file.isEmpty()){
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
            pstmt.setInt(2,company_id);
            pstmt.setString(3, file.getOriginalFilename());
            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            uploadeResource.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try{
                uploadeResource.put("error", e.toString());

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


    public JSONArray getCompanysResources(Company company) {
        //One of the students that are associated with a job
        JSONObject resource = new JSONObject();
        //the list of selected students
        JSONArray company_resources = new JSONArray();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;

        //holder for the student IDs that are associated witht he job
        String resource_location = "";
        String sql = "select * from t_company_resources where company_id =?";
        DbConn jdbcObj = new DbConn();
        try {
            if (company.getCompany_id() == 0) {
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
            pstmt.setInt(1, company.getCompany_id());
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
                resource.put("error", e.toString());
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

    public JSONObject checkCompanyLogin( Company company){
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs=null;
        String sql="select * from t_company_info where email=? and password=?;";
        DbConn jdbcObj = new DbConn();
        String email="";String state="";String website_link=""; String description="";
        String company_name=""; String street=""; String city=""; String zip_code="";
        JSONObject companyObj= new JSONObject();
        try {
            if(company.getPassword()==null|| company.getEmail()==null){
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
            pstmt.setString(1,company.getEmail());
            pstmt.setString(2,company.getPassword());
            rs= pstmt.executeQuery();
            if(rs.next()){
                email=rs.getString("email");
                state=rs.getString("state");
                street=rs.getString("street");
                city=rs.getString("city");
                zip_code=rs.getString("zip_code");
                website_link=rs.getString("website_link");
                company_name=rs.getString("company_name");
                description=rs.getString("description");
                companyObj.put("email",email);
                companyObj.put("company_name",company_name);
                companyObj.put("website_link", website_link);
                companyObj.put("state",state);
                companyObj.put("street",street);
                companyObj.put("city",city);
                companyObj.put("zip_code",zip_code);
                companyObj.put("description",description);



            }else{
                companyObj.put("company_login","Does not exist");

            }
            rs.close();
            pstmt.close();
            conn.close();
            jdbcObj.closePool();



        } catch (Exception e) {
            e.printStackTrace();
            try{
                companyObj.put("error", e.toString());

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

        return companyObj;

    }




}
