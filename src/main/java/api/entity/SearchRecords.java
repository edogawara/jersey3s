package api.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SearchRecords {
	
	public ArrayList<LinkedHashMap<String,String>> records = new ArrayList<>();
	
	public SearchRecords(Connection conn, String tableName, HashMap<String,String> cond ) throws SQLException  {
		String query = cond.get("query");
		String where = "";
		if( query != null && !query.trim().equals("")) {
			where = " where (" + query + ") limit 100";
		}
		String sql = "select * from " + tableName + where ;
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
