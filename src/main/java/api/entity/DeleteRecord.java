package api.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import api.exception.ExtendedNotFoundException;

public class DeleteRecord {

	public DeleteRecord(Connection conn, String tableid, String recordid)
				throws SQLException {
	
		String sql = "delete from " + tableid + " where id = ? " ;
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, recordid);
		int rows = pstmt.executeUpdate();
		if( rows == 0 ) {
			throw new  ExtendedNotFoundException("delete data not found!");
		}
		pstmt.close();
	}
}
