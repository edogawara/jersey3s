package api.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;

public class InfoTable {
	
	public LinkedHashMap<String,String> info = new LinkedHashMap<>();
	
	public InfoTable(Connection conn, String tableName ) throws SQLException  {
		String sql = "select * from " + tableName + " where 1<0" ;
		// DatabaseMetaData meta = conn.getMetaData(): // 製品依存
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData meta = rs.getMetaData();
		for( int i = 0; i < meta.getColumnCount(); i++ ) {
			String name = meta.getColumnName(i+1);
			String type = meta.getColumnTypeName(i+1);
			info.put(name, type);
		}
		rs.close();
	}
}
