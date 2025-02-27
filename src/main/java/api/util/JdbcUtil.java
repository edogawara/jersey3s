package api.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class JdbcUtil {

	// Connection の取得 (必ず close すること)
	public static Connection getConnection() throws NamingException, SQLException {

		  InitialContext initContext = new InitialContext();
		  Context envContext = (Context)initContext.lookup("java:/comp/env");
		  DataSource ds = (DataSource)envContext.lookup( "jdbc/testdb" );
		  Connection conn = ds.getConnection();
		  return conn;
		
	}
	
	
	
	
}
