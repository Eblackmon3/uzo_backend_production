package Application;

import Model.DbConn;
import Model.DbManager;
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

    @RequestMapping(value="get_student_by_id", method = RequestMethod.GET)
    public @ResponseBody int getStudentById(@RequestParam("studentid") int  studentid){
        //manager.getStudentById(studentid);
        System.out.println(studentid);
        return studentid;
    }

    @RequestMapping(value = "/insert_student_by_id/", method = RequestMethod.PUT)
    public @ResponseBody int updateStudent(@RequestParam("studentid") int  studentid){
        System.out.println(studentid);
        return studentid;
    }
}
