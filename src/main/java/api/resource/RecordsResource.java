package api.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
 *  レコード操作リソースサブクラス
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
       	Connection conn = null;
       	SearchRecords oRecords = null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(jsonString);
			//  {"query":""}
			HashMap<String,String> cond = mapper.convertValue(node, HashMap.class);
			conn = JdbcUtil.getConnection();
			oRecords = new SearchRecords( conn, tableid, cond );
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("search error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
		return oRecords;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SelectRecords getRecords()  {
    	Connection conn = null;
		SelectRecords oRecords = null;
		try {
			conn = JdbcUtil.getConnection();
			oRecords = new SelectRecords(conn, tableid);
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("select records error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
        return  oRecords;
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertTable( String jsonString, @Context UriInfo uriInfo ) {
    	Connection conn = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonObject = mapper.readTree(jsonString);
			//  {"record":{{col1:"value1"},{col2:"value2"}...}}
			JsonNode node = jsonObject.get("record");
			LinkedHashMap<String,String> data = mapper.convertValue(node, LinkedHashMap.class);
			conn = JdbcUtil.getConnection();
			new InsertRecord( conn, tableid, data );
			
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("insert error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
		Response response = Response.created(uriInfo.getAbsolutePath()).build();
        return  response;
    }
          
}
