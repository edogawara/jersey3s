package api.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class SelectRecords {
	
	public ArrayList<LinkedHashMap<String,String>> records = new ArrayList<>();
	
	public SelectRecords(Connection conn, String tableName ) throws SQLException  {
		
		String sql = "select * from " + tableName ;
		
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData meta = rs.getMetaData();
			
		while( rs.next() ) {
				
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			for( int i = 0; i < meta.getColumnCount(); i++ ) {
				String name = meta.getColumnName(i+1);
				String val = rs.getString(name);
				map.put(name, val);
			}
			records.add(map);
		}
		rs.close();
	}
}
