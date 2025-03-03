package api.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import api.exception.ExtendedBadRequestException;

public class InsertRecord {
	
	public LinkedHashMap<String,String> record = null;

	public void execute(Connection conn, String tableid) throws SQLException {
		{
			int rows = 0;
			String recordid = record.get("id");
			String sql = "select * from " + tableid + " where id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, recordid);
			ResultSet rs = pstmt.executeQuery();
			if( rs.next() ) rows = 1;
			rs.close();
			pstmt.close();
			if( rows > 0 ) {
				throw new ExtendedBadRequestException("Duplicate key error!");
			}
		}
		StringBuffer sql = new StringBuffer("insert into " + tableid) ;
		StringBuffer colList = new StringBuffer();
		StringBuffer valList = new StringBuffer();
		ArrayList<String> cols = new ArrayList<>(record.keySet());
		for ( int i = 0; i < cols.size(); i++){
			if( i > 0 ) {
				colList.append(",");
				valList.append(",");
			}	
		    colList.append(cols.get(i));
		    valList.append("?");
		}
		sql.append("(" + colList.toString() + ")");
		sql.append(" values(" + valList.toString() + ")");
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		for ( int i = 0; i < cols.size(); i++){
		    pstmt.setString(i+1, record.get(cols.get(i)));
		}
		pstmt.executeUpdate();
		pstmt.close();
	}
}
