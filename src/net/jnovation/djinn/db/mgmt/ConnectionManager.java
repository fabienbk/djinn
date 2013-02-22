/*
 * Created on Feb 5, 2006
 * By Fabien Benoit - http://www.jnovation.net
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
package net.jnovation.djinn.db.mgmt;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;


public class ConnectionManager {

    private static ConnectionManager instance = new ConnectionManager();
    
    public static ConnectionManager getInstance() {        
        return instance;
    }
    
    private Connection connection = null;

    private ConnectionManager() {        
        try {
            Class.forName ("org.hsqldb.jdbcDriver");
            DriverManager.registerDriver(new org.hsqldb.jdbcDriver());
            connection = DriverManager.getConnection("jdbc:hsqldb:mem:djinn","sa","");
            
            DBConstructor dbConstructor = new DBConstructor();            
            dbConstructor.buildSchema(connection, new File("ddl/createSchema.sql"));
            connection.commit();
                        
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }                
    }

    public Connection getConnection() {
        return this.connection;
    }
    
}
