package api.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


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
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return "No Support URI!";
    }  
}
