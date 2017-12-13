package Model;


import org.json.JSONObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbManager {

    public JSONObject getStudentById(int id){
        ResultSet rsObj = null;
        Connection connObj = null;
        PreparedStatement pstmtObj = null;
        DbConn jdbcObj = new DbConn();
        JSONObject studentObj= new JSONObject();
        try {
            //Connect to the database
            DataSource dataSource = jdbcObj.setUpPool();
            System.out.println(jdbcObj.printDbStatus());
            connObj = dataSource.getConnection();
            //check how many connections we have
            System.out.println(jdbcObj.printDbStatus());
            //can do normal DB operations here

        } catch (Exception e) {
            e.printStackTrace();
        }

        return studentObj;
    }
}
