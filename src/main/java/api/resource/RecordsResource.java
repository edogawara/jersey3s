package api.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.entity.DeleteRecord;
import api.entity.InsertRecord;
import api.entity.SearchRecords;
import api.entity.SelectRecord;
import api.entity.SelectRecords;
import api.entity.UpdateRecord;
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

    @Path("{recordid}") @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SelectRecord getRecord(@PathParam("recordid") String recordid) {
    	Connection conn = null;
		SelectRecord oRecord = null;
		try {
			conn = JdbcUtil.getConnection();
			oRecord = new SelectRecord( conn, tableid, recordid );
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("select table error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
        return  oRecord;
    }
    
    @Path("{recordid}") @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRecord( @PathParam("recordid") String recordid , String jsonString) {
    	Connection conn = null;
		UpdateRecord oRecord = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonObject = mapper.readTree(jsonString);
		//  {"record":{{col1:"value1"},{col2:"value2"}...}}
			JsonNode node = jsonObject.get("record");
			LinkedHashMap<String, String> data = mapper.convertValue(node, LinkedHashMap.class);
			conn = JdbcUtil.getConnection();
			oRecord = new UpdateRecord( conn, tableid, recordid,  data );
			
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("insert error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
    }
    @Path("{recordid}") @DELETE
    public void deleteRecord( @PathParam("recordid") String recordid ) {
    	Connection conn = null;
		try {
			conn = JdbcUtil.getConnection();
			new DeleteRecord( conn, tableid, recordid );
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("insert error!");
		} finally {
			if( conn != null )
				try { conn.close();} catch (SQLException e) {}
		}
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
   			throw new  ExtendedWebApplicationException("insert error!");
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
   			throw new ExtendedWebApplicationException("select table error!");
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
		InsertRecord oRecord = null;

		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode jsonObject = mapper.readTree(jsonString);
			//  {"record":{{col1:"value1"},{col2:"value2"}...}}
			JsonNode node = jsonObject.get("record");
			LinkedHashMap<String,String> data = mapper.convertValue(node, LinkedHashMap.class);
			conn = JdbcUtil.getConnection();
			oRecord = new InsertRecord( conn, tableid, data );
			
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
