/*
 * Created on Feb 8, 2006
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
package com.scramcode.djinn.db.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.scramcode.djinn.db.mgmt.QueryHelper;
import com.scramcode.djinn.db.mgmt.RowConverter;


public class DataHelper {
        
    public static int putProject(Connection conn, Project project) {
        QueryHelper.executeUpdate(conn, 
                "INSERT INTO PROJECTS (project_name) " +
                "VALUES (?)",
                new Object[] {project.getProjectName()});
        int generatedKey = QueryHelper.callIdentity(conn); 
        project.setKey(generatedKey);        
        return generatedKey;
    }

    public static int putLocation(Connection conn, Location location) {                
        QueryHelper.executeUpdate(conn, 
                "INSERT INTO LOCATIONS (absolute_path, type, project_key) " +
                "VALUES (?,?,?)",
                new Object[] {
                location.getAbsolutePath(),
                location.getType(),
                location.getProjectKey()});
        int generatedKey = QueryHelper.callIdentity(conn); 
        location.setKey(generatedKey);        
        return generatedKey;        
    }
    
    public static int putClass(Connection conn, Class clazz) { 
        QueryHelper.executeUpdate(conn, 
                "INSERT INTO CLASSES " +
                "(name, access, package_key, cname, location_key) " +
                "VALUES (?,?,?,?,?)",
                new Object[] { 
                clazz.getName(), 
                clazz.getAccess(), 
                clazz.getPackageKey(),
                clazz.getCanonicalName(),
                clazz.getLocationKey()}
        );
        int k = QueryHelper.callIdentity(conn);
        clazz.setKey(k);
        return k;
    }
    
    public static void putField(Connection conn, Field field) {                
        QueryHelper.executeUpdate(conn, 
                "INSERT INTO FIELDS " +
                "(name, access, class_key) " +
                "VALUES (?,?,?)",
                new Object[] {
                field.getName(),
                field.getAccess(),
                field.getClassKey() }
        );
        int k = QueryHelper.callIdentity(conn);
        field.setKey(k);
    }
    
    
    public static void putMethod(Connection conn, Method method) {                
        QueryHelper.executeUpdate(conn, 
                "INSERT INTO METHODS " +
                "(name, access, class_key) " +
                "VALUES (?,?,?)",
                new Object[] { 
                method.getName(), 
                method.getAccess(),
                method.getClassKey() }
        );
        int k = QueryHelper.callIdentity(conn);
        method.setKey(k);
    }   
    
    public static int putPackage(Connection conn, Package packageObject) {                       
        QueryHelper.executeUpdate(conn, "INSERT INTO PACKAGES (qname, location_key) " +
                "VALUES (?, ?)", 
                new Object[] {
                packageObject.getQname(), 
                packageObject.getLocationKey()});
        int k = QueryHelper.callIdentity(conn);
        packageObject.setKey(k);
        return k;        
    }    

    public static void putClassReference(Connection conn, int classKey, String desc) {        
        QueryHelper.executeUpdate(conn, 
                "INSERT INTO CLASS_REFERENCES (class_key, cname) " +
                "VALUES (?, ?)", 
                new Object[] {classKey, desc});                    
    }

    public static List<Package> getPackages(Connection conn, final Location location) {
        QueryHelper<Package> qhelper = new QueryHelper<Package>();        
        return qhelper.executeQuery(conn,
                "SELECT package_key, qname FROM PACKAGES WHERE location_key=" + location.getKey(), 
                new RowConverter<Package>() {
                    public Package getRow(ResultSet rs) throws SQLException {                        
                        Package p = new Package( rs.getString(2), location.getKey() );
                        p.setKey(rs.getInt(1));
                        return p;
                    }            
                }
        );
    }
    
    public static List<Location> getLocations(Connection conn, final Project project) {
        QueryHelper<Location> qhelper = new QueryHelper<Location>();        
        return qhelper.executeQuery(conn,
                "SELECT location_key, absolute_path, type, project_key FROM LOCATIONS WHERE project_key = " + project.getKey(), 
                new RowConverter<Location>() {
                    public Location getRow(ResultSet rs) throws SQLException {                        
                        Location l = new Location( rs.getString(2), rs.getInt(3), project.getKey() );
                        l.setKey(rs.getInt(1));
                        return l;
                    }            
                }
        );
    }    
}
