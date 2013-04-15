package net.jnovation.djinn.db.logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import net.jnovation.djinn.db.data.JavaItem;
import net.jnovation.djinn.db.mgmt.QueryHelper;
import net.jnovation.djinn.db.mgmt.RowConverter;

public class DBOQueryHelper extends QueryHelper<JavaItem> {
    
    public List<JavaItem> fetch(Connection conn, String sql, final Class clazz) {
        return super.executeQuery(conn, sql, new RowConverter<JavaItem>() {
            public JavaItem getRow(ResultSet rs) {                 
                try {
                    Object dbo = 
                        clazz.getConstructor(
                                new Class[] {ResultSet.class}).newInstance(new Object[] {rs});
                    
                    return (JavaItem)dbo;
                } 
                catch (Exception e) {
                    throw new RuntimeException();
                }                
            }
        });
    }

}
