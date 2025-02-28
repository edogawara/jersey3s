package api.resource;

import java.sql.Connection;
import java.util.LinkedHashMap;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import api.entity.DeleteRecord;
import api.entity.SelectRecord;
import api.entity.UpdateRecord;
import api.exception.ExtendedWebApplicationException;
import api.util.JdbcUtil;

/*
 *  単一レコード操作サブリソース
 */
public class RecordResource {

	String tableid = null;
	String recordid = null;
	
	RecordResource(String tableid, String recordid) {
		this.tableid = tableid;
		this.recordid = recordid;
	}
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public SelectRecord getRecord() {
		SelectRecord oRecord = null;
		try (Connection conn = JdbcUtil.getConnection() ) {
			oRecord = new SelectRecord( conn, tableid, recordid );
		} catch (Exception e) {
   			throw new ExtendedWebApplicationException("select record error!");
		}
        return  oRecord;
    }
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateRecord( JsonNode jsonObject) {
		ObjectMapper mapper = new ObjectMapper();
		try (Connection conn = JdbcUtil.getConnection()) {
			//  {"record":{{col1:"value1"},{col2:"value2"}...}}
			JsonNode node = jsonObject.get("record");
			LinkedHashMap<String, String> data = mapper.convertValue(node, LinkedHashMap.class);
			new UpdateRecord( conn, tableid, recordid,  data );
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("update error!");
		}
    }
    @DELETE
    public void deleteRecord() {
		try (Connection	conn = JdbcUtil.getConnection() ) {
			new DeleteRecord( conn, tableid, recordid );
		} catch (Exception e) {
   			throw new  ExtendedWebApplicationException("delete error!");
		}
    }
}
