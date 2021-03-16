/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tailm.ulties;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author tai.lee
 */
public class DBHelpers implements Serializable{
    public static Connection makeConnection() throws NamingException, SQLException{
        Context context = new InitialContext();
        Context serverContext = (Context) context.lookup("java:comp/env");
        DataSource dataSource = (DataSource) serverContext.lookup("J3.L.P0015");
        return dataSource.getConnection();
    }
}
