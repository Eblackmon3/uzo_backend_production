package Application;

import Model.DbConn;
import Model.DbManager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
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
     * api call example https://uzo-web-app.herokuapp.com/insert_student_by_id?studentid=2
     */
    @RequestMapping(value = "insert_student_by_id")
    public @ResponseBody String updateStudent(@RequestParam("studentEmail") int  studentEmail,
                                           @RequestParam("studentPassword") int  studentPassword,
                                           @RequestParam("onCall") boolean onCall,
                                           @RequestParam("studentFirstName") int  studentFirstName,
                                           @RequestParam("studentLastName") int  studentLastName){
        System.out.println(studentEmail+" "+ studentPassword+ " "+ onCall+ " "+studentFirstName+" "+ studentLastName);
        return studentEmail+" "+ studentPassword+ " "+ onCall+ " "+studentFirstName+" "+ studentLastName;
    }
}
