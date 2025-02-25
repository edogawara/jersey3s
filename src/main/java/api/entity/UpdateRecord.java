package api.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import api.exception.ExtendedNotFoundException;

public class UpdateRecord {

	public UpdateRecord(Connection conn, String tableid, String recordid
			, LinkedHashMap<String,String> record)	throws SQLException {
	
		StringBuffer sql = new StringBuffer("update " + tableid + " set ") ;
		StringBuffer colList = new StringBuffer();

		ArrayList<String> cols = new ArrayList<>(record.keySet());
		for ( int i = 0; i < cols.size(); i++){
			if( i > 0 ) {
				colList.append(",");
			}	
		    colList.append(cols.get(i) + "= ?");
		}
		sql.append( colList.toString() + " where id = ?");
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		for ( int i = 0; i < cols.size(); i++){
		    pstmt.setString( cols.size() , record.get(cols.get(i)) );
		}
		pstmt.setString(cols.size(), recordid);
		int rows = pstmt.executeUpdate();
		if( rows == 0 ) {
			throw new  ExtendedNotFoundException("update data not found!");
		}
		pstmt.close();
		
	}
}
