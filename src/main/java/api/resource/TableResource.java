package api.resource;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import api.entity.InfoTable;
import api.exception.ExtendedWebApplicationException;
import api.util.JdbcUtil;

/*
 *  Sub Resource Class
 */
public class TableResource {

	String tableid = null;
	
	TableResource(String tableid ) {
		this.tableid = tableid;
	}

    @Path("records")
    public RecordsResource getRecords() {
        return  new RecordsResource(tableid);
    }
      
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public InfoTable getInfoTable() {
    	Connection conn = null;
		InfoTable oInfo = null;
		try {
			conn = JdbcUtil.getConnection();
			oInfo = new InfoTable( conn, tableid );
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("info table error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
        return  oInfo;
    }
    

}
