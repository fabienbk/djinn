/*
 * Created on Feb 5, 2006
 * By Fabien Benoit - http://www.scramcode.com
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.scramcode.djinn.db.mgmt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;

	
/**
 * Utility methods for building databases schemas from various sources.
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class DBConstructor {
	
	public void buildSchema(Connection conn, BufferedReader reader) throws IOException {
		String line = reader.readLine();
        StringBuffer script = new StringBuffer();
        while (line != null) {
            script.append(line);
            line = reader.readLine();
        }        
        // split in several statements
        String[] statements = script.toString().split("\\"+DatabaseConstants.STATEMENT_SEPARATOR_CHAR);
        
        buildSchema(conn, statements);
        reader.close();
	}
	
	public void buildSchema(Connection conn, InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		buildSchema(conn, reader);
	}
    
    /**
     * Execute a set of update statement contained in a specified file.
     * @param conn An active JDBC connection.
     * @param scriptFile The file containing the SQL script.
     * @throws IOException If the file can't be read.
     * @throws SQLException If one of the queries fails.
     */    
    public void buildSchema(Connection conn, File scriptFile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(scriptFile));
        buildSchema(conn, reader);
    }

    /**
     * Execute a set of update statements. Commit or rollback are left at client discretion.
     * @param conn An active JDBC connection.
     * @param statements An array of SQL update queries.
     * @throws SQLException If one of the queries fails.
     */  
    public void buildSchema(Connection conn, String[] statements) {        
        for (int i = 0; i < statements.length; i++) {            
            QueryHelper.executeUpdate(conn, statements[i]);
        }        
    }    

    public static void main(String[] args) throws IOException {
        
        DBConstructor dbc = new DBConstructor();
        Connection conn = ConnectionManager.getInstance().getConnection();
        dbc.buildSchema(conn, new File("ddl/createSchema.sql"));
        
        System.out.println(QueryHelper.callIdentity(conn));
        QueryHelper.executeUpdate(conn, "INSERT INTO LOCATIONS (absolute_path, type) VALUES ('c://',0)");
        System.out.println(QueryHelper.callIdentity(conn));
        QueryHelper.executeUpdate(conn, "INSERT INTO LOCATIONS (absolute_path, type) VALUES ('c://',0)");
        System.out.println(QueryHelper.callIdentity(conn));
        QueryHelper.executeUpdate(conn, "INSERT INTO LOCATIONS (absolute_path, type) VALUES ('c://',0)");
        System.out.println(QueryHelper.callIdentity(conn));
        QueryHelper.executeUpdate(conn, "INSERT INTO LOCATIONS (absolute_path, type) VALUES ('c://',0)");
        System.out.println(QueryHelper.callIdentity(conn));
        
    }
    
}
