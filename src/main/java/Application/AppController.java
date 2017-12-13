package Application;

import Model.DbConn;
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

    @RequestMapping("/")
    public String index() {
        return("Welcome to the UZO-API");
    }

    @RequestMapping(value="user", method = RequestMethod.GET)
    public @ResponseBody int getitem(@RequestParam("data") int  itemid){
        System.out.println(itemid);
        return itemid;
    }
}
