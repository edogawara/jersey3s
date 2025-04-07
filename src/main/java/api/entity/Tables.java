package api.entity;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Tables {
	
	public ArrayList<LinkedHashMap<String,String>> tables = new ArrayList<>();

	public Tables(Connection conn ) throws SQLException  {
		DatabaseMetaData meta= conn.getMetaData();
		ResultSet rs = meta.getTables(null, "public", null, null);
		while( rs.next() ) {
			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			String val = rs.getString("TABLE_NAME");
			map.put("name", val);
			tables.add(map);
		}
		rs.close();
	}
}