package api.resource;

import java.sql.Connection;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import api.entity.Tables;
import api.exception.ExtendedWebApplicationException;
import api.util.JdbcUtil;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


/**
 * Root Resource Class 
 */

@Path("tables")
@SecurityRequirement(name = "myBearerSecurity") // for Swagger
public class TablesResource {

	String bearer = null;

	public TablesResource(@HeaderParam("Authorization") String bearer) {
		this.bearer = bearer;
	}
    @Path("{tableid}/")
    public TableResource getTable(@PathParam("tableid") String tableid ) {
    	// サブリソースはインスタンス化してjerseyエンジンに戻す
    	//   メソッドの呼び出しは jersey が行う
        return new TableResource(tableid);
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Tables getTables() {
    	
		Tables oTables = null;
		try ( Connection conn = JdbcUtil.getConnection() ) {
			oTables = new Tables( conn );
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("tables error!");
		}
        return  oTables;
    }  
}
