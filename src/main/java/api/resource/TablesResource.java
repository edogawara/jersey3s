package api.resource;

import java.sql.Connection;
import java.sql.SQLException;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import api.entity.Tables;
import api.exception.ExtendedWebApplicationException;
import api.util.JdbcUtil;


/**
 * Root Resource Class 
 */
@Path("tables")
public class TablesResource {

    @Path("{tableid}/")
    public TableResource getTable(@PathParam("tableid") String tableid
    		                     , @HeaderParam("Authorization") String bearer) {
    	/*
    	ApiAuthorization auth = new ApiAuthorization(bearer);
    	if( !auth.isInRole("user") ) {
   			throw new  ExtendedNotAuthorizedException("no permission!");
    	}
    	*/
    	// サブリソースをインスタンス化する
    	//   メソッドの呼び出しは jersey が行う
        return new TableResource(tableid);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Tables getTables() {
    	Connection conn = null;
		Tables oTables = null;
		try {
			conn = JdbcUtil.getConnection();
			oTables = new Tables( conn );
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("tables error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
        return  oTables;
    }  
}
