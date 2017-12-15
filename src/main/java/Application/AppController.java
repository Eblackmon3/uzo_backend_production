package Application;

import Model.*;
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
     * used a string as to not process the JSONOBJECT on response
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
         "student_id": 3,
         "email": "stephenoakala@gmail.com",
         "password": "281330800fB",
         "on_call": true,
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
     * api call example https://uzo-web-app.herokuapp.com/get_student_by_id?studentid=002
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
         "company_id": 3,
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

    @PostMapping(value = "/insert_job")
    public String insertJob(@RequestBody JobInsert insertJob){
        return manager.insertJob(insertJob).toString();
        //return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
