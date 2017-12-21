package Application;

import Model.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.apache.commons.dbcp.ConnectionFactory;
import org.apache.commons.dbcp.DriverManagerConnectionFactory;
import org.apache.commons.dbcp.PoolableConnectionFactory;
import org.apache.commons.dbcp.PoolingDataSource;
import org.apache.commons.pool.impl.GenericObjectPool;

import javax.sql.DataSource;

@RestController
public class AppController {
    DbManager manager= new DbManager();

    @RequestMapping("/")
    public String index() {
        return("Welcome to the UZO-API");
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/get_student_by_id?studentid=002
     */
    @RequestMapping(value="get_student_by_id")
    public @ResponseBody String getStudentById(@RequestParam("studentid") int  studentid){
        JSONObject student= manager.getStudentById(studentid);
        System.out.println(student.toString());
        return student.toString();
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/insert_student
     * headers
     * {
         "email": "stephenoakala@gmail.com",
         "password": "281330800fB",
         "first_name": "Stephen",
         "last_name":"Okala"
        }
     * used a string as to not process the JSONOBJECT on response
     */
    @PostMapping(value = "/insert_student")
    public String insertStudent(@RequestBody Student insertStudent){
        return manager.insertStudent(insertStudent).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }




    /*
     * api call example https://uzo-web-app.herokuapp.com/get_company_by_id?companyid=001
     * used a string as to not process the JSONOBJECT on response
     */
    @RequestMapping(value="get_company_by_id")
    public @ResponseBody String getCompanyById(@RequestParam("companyid") int  companyid){
        JSONObject company= manager.getCompanyById(companyid);
        System.out.println(company.toString());
        return company.toString();
    }


      /*
     * api call example https://uzo-web-app.herokuapp.com/insert_company
     * headers
     * {
         "email": "eric.blackmon@uzo.com",
         "address": "2700 gray valley court houston tx",
         "website_link": "uzo.com",
         "company_name": "UZO",
         "password":"281330800fB"
        }
     * used a string as to not process the JSONOBJECT on response
     */

    @PostMapping(value = "/insert_company")
    public String insertCompany(@RequestBody Company insertCompany){
        return manager.insertCompany(insertCompany).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * api call example https://uzo-web-app.herokuapp.com/insert_job
     * header
     * {
         "completed": false,
         "date": "2018-06-15",
         "rate": "40/hr",
         "dress_code": "Formal",
         "duration":2.5,
         "open": true,
         "job_title": "Janitor",
         "time":2300,
         "company_id":1
}
     */
    @PostMapping(value = "/insert_job")
    public String insertJob(@RequestBody JobInsert insertJob){
        return manager.insertJob(insertJob).toString();
    }

    /*
     * Example url: https://uzo-web-app.herokuapp.com/assign_student_job
     * example json
     * {
         "student_id": 1,
         "company_id": 19,
         "job_id":1
        }
     */

    @PostMapping(value = "/assign_student_job")
    public String assignStudentJob(@RequestBody StudentJob studentJob){
        return manager.assignStudentJob(studentJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /* example url: https://uzo-web-app.herokuapp.com/delete_student_job
     * example json:
     * {
         "student_id": 1,
         "job_id":1
        }
     */
    @PostMapping(value = "/delete_student_job")
    public String deleteStudentJob(@RequestBody StudentJob studentJob){
        return manager.removeStudentJob(studentJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /*
     * example url:https://uzo-web-app.herokuapp.com/get_students_jobs_by_id
     * example header:
     * {
         "student_id": 1
        }
     *
     */

    @PostMapping(value = "/get_students_jobs_by_id")
    public String getStudentJobList(@RequestBody Student student){
        return manager.getStudentJobList(student).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    /*
     *example url:https://uzo-web-app.herokuapp.com/get_jobs_students_by_id
     * example header:
     * {
         "job_id": 1
        }
     */
    @PostMapping(value = "/get_jobs_students_by_id")
    public String getJobStudentList(@RequestBody Job job) {
        return manager.getJobStudentList(job).toString();
    }

    @PostMapping(value = "/on_call_student")
    public String insertOnCallStudent(@RequestBody JobOnCall onCall){
        return manager.insertStudentOnCall(onCall).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }



    }
