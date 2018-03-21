package Model.DataManagers;

import Model.DataObjects.Event;
import Model.DataObjects.JobInsert;
import Model.DbConn;
import org.json.JSONObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EventManager {

    public JSONObject insertEvent(Event event){
        JSONObject insertedJob= new JSONObject();
        ResultSet rsObj = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql="insert into t_event_info(company_id, dress_code, duration, open, event_title, date, time , description) " +
                "Values(?,?, ?,  ?,?,?, ?,?);";
        DbConn jdbcObj = new DbConn();
        int affectedRows=0;
        try{
            if(event.getDate()==null  || event.getDress_code()==null
                    ||  event.getEvent_title()==null|| event.getCompany_id()==0 || event.getDescription()==null){
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
            pstmt.setInt(1, event.getCompany_id());
            pstmt.setString(2, event.getDress_code());
            pstmt.setDouble(3, event.getDuration());
            pstmt.setBoolean(4, event.isOpen());
            pstmt.setString(5, event.getEvent_title());
            pstmt.setDate(6, Date.valueOf(event.getDate()));
            pstmt.setInt(7, event.getTime());
            pstmt.setString(8, event.getDescription());


            affectedRows = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
            insertedJob.put("affected_rows",affectedRows);

        }catch(Exception e){
            e.printStackTrace();
            try {
                insertedJob.put("error", e.toString());
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

}
