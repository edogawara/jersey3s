package api.entity;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tables {
	
	public ArrayList<String> tables = new ArrayList<>();
	
	public Tables(Connection conn ) throws SQLException  {
		
		DatabaseMetaData meta= conn.getMetaData();
		ResultSet rs = meta.getTables(null, "public", null, null);
			
		while( rs.next() ) {
			tables.add(rs.getString("TABLE_NAME"));
		}
		rs.close();
	}
}
