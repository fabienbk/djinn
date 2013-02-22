package net.jnovation.djinn.db.logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import net.jnovation.djinn.db.data.DBObject;
import net.jnovation.djinn.db.mgmt.QueryHelper;
import net.jnovation.djinn.db.mgmt.RowConverter;

public class DBOQueryHelper extends QueryHelper<DBObject> {
    
    public List<DBObject> fetch(Connection conn, String sql, final Class clazz) {
        return super.executeQuery(conn, sql, new RowConverter<DBObject>() {
            public DBObject getRow(ResultSet rs) {                 
                try {
                    Object dbo = 
                        clazz.getConstructor(
                                new Class[] {ResultSet.class}).newInstance(new Object[] {rs});
                    
                    return (DBObject)dbo;
                } 
                catch (Exception e) {
                    throw new RuntimeException();
                }                
            }
        });
    }

}
