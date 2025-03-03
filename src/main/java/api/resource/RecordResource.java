package api.resource;

import java.sql.Connection;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
    public void updateRecord( UpdateRecord update) {
		try (Connection conn = JdbcUtil.getConnection()) {
			update.execute( conn, tableid, recordid );
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
