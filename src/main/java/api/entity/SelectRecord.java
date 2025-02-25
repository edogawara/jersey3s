package api.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;

public class SelectRecord {
	
	public LinkedHashMap<String,String> record = new LinkedHashMap<>();
	
	public SelectRecord(Connection conn, String tableName, String recordid  ) throws SQLException  {
		
		String sql = "select * from " + tableName + " where id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, recordid);
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData meta = rs.getMetaData();
       
		while( rs.next() ) {
			for( int i = 0; i < meta.getColumnCount(); i++ ) {
				String name = meta.getColumnName(i+1);
				String val = rs.getString(name);
				record.put(name, val);
			}
		}
		rs.close();
	}
}
