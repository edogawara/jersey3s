package api.resource;

import java.sql.Connection;
import java.util.HashMap;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.entity.InsertRecord;
import api.entity.SearchRecords;
import api.entity.SelectRecords;
import api.exception.ExtendedWebApplicationException;
import api.util.JdbcUtil;

/*
 *  レコード操作サブリソース
 */
public class RecordsResource {

	String tableid = null;
	
	RecordsResource(String tableid) {
		this.tableid = tableid;
	}
    @Path("{recordid}") 
    public RecordResource getRecord(@PathParam("recordid") String recordid) {
        return  new RecordResource(tableid, recordid );
    }
    @Path("search") @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public SearchRecords searchRecords(String jsonString ) {
       	SearchRecords oRecords = null;
		ObjectMapper mapper = new ObjectMapper();
		try (Connection conn = JdbcUtil.getConnection()) {
			JsonNode node = mapper.readTree(jsonString);
			//  {"query":""}
			HashMap<String,String> cond = mapper.convertValue(node, HashMap.class);
			oRecords = new SearchRecords( conn, tableid, cond );
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("search error!");
		}
		return oRecords;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SelectRecords getRecords()  {
		SelectRecords oRecords = null;
		try (Connection	conn = JdbcUtil.getConnection() ) {
			oRecords = new SelectRecords(conn, tableid);
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("select records error!");
		}
        return  oRecords;
    }
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertTable( InsertRecord insert, @Context UriInfo uriInfo ) {
		try (Connection	conn = JdbcUtil.getConnection() ) {
			insert.execute(conn, tableid);
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("insert error!");
		}
		Response response = Response.created(uriInfo.getAbsolutePath()).build();
        return  response;
    }
}
