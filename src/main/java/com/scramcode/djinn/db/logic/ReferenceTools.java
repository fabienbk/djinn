/*
 * Created on Mar 3, 2006
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
package com.scramcode.djinn.db.logic;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.scramcode.djinn.db.data.Class;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Package;
import com.scramcode.djinn.db.data.Project;
import com.scramcode.djinn.db.meta.DatabaseModel;
import com.scramcode.djinn.db.meta.Table;
import com.scramcode.djinn.db.mgmt.ConnectionManager;
import com.scramcode.djinn.db.mgmt.QueryHelper;
import com.scramcode.djinn.db.mgmt.RowConverter;
import com.scramcode.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;


/**
 * Queries for computing references aggregations.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class ReferenceTools {             

    public static List<JavaItem> getReferences(
            JavaItem sourceObject,
            JavaItem destinationObject,
            GranularityLevel granularityLevel) {

        // Get all references of the source object, at the requested granularity
        List<? extends JavaItem> references = null;        
        switch(granularityLevel) {
            case CLASS: {
                references = getAllReferencesGroupByClass(sourceObject);
                break;
            }
            case JAR: {
                references = getAllReferencesGroupByLocation(sourceObject);
                break;
            }    
            case PACKAGE: {
                references = getAllReferencesGroupByPackage(sourceObject);
            }
        }
        // Filter references not contained by destination object
        List<JavaItem> result = new ArrayList<JavaItem>();
        for(JavaItem dbo : references) {
            if (dbo.isContainedBy(destinationObject)) {
                result.add(dbo);
            }
        }
        
        return result;
    }

    public static List<JavaItem> getTopLevelItems() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        ArrayList<JavaItem> topLevelItemList = new ArrayList<JavaItem>();
        QueryHelper<Project> queryHelper = new QueryHelper<Project>();
        List<Project> projectNodeList = queryHelper.executeQuery(conn,
                "SELECT * FROM PROJECTS", 
                new RowConverter<Project>(){
                    public Project getRow(ResultSet rs) throws SQLException {       
                        return new Project(rs);                        
                    }            
        });
        topLevelItemList.addAll(projectNodeList);
        
        QueryHelper<Location> queryHelper2 = new QueryHelper<Location>();
        List<Location> topLevelLocationNodeList = queryHelper2.executeQuery(conn,
                "SELECT * FROM LOCATIONS WHERE PROJECT_KEY IS NULL", 
                new RowConverter<Location>(){
                    public Location getRow(ResultSet rs) throws SQLException {                    	
                    	return new Location(rs);                     
                    }            
        });
        topLevelItemList.addAll(topLevelLocationNodeList);
        return topLevelItemList;
    }
    
    public static List<Class> getAllReferencesGroupByClass(JavaItem dbObject) {
        
        DatabaseModel dbInstance = DatabaseModel.getInstance();
        Table dbObjectTable = dbInstance.getTable(dbObject.getMappedTable());
        Table classRefTable = dbInstance.getTable("CLASS_REFERENCES");        

        String getRefsJoinClause = 
            dbInstance.getJoinClause(dbObjectTable, classRefTable);

        // Methods and Field level are not supported

        QueryHelper<Class> queryHelper = new QueryHelper<Class>();
        List<Class> referencesList = queryHelper.executeQuery(
                ConnectionManager.getInstance().getConnection(),                
                "SELECT DISTINCT CLASSES.* FROM " 
                + "(SELECT DISTINCT cname "
                + " FROM " + getRefsJoinClause
                + " WHERE " + dbObjectTable.getPrimaryKeyFieldName() + " = " + dbObject.getKey() + ") T1"
                + " JOIN CLASSES ON CLASSES.cname = T1.cname ",                
                new RowConverter<Class>() {
                    public Class getRow(ResultSet rs) throws SQLException {
                        Class p = new Class(rs);
                        return p;
                    }            
                });

        return referencesList;

    }

    public static List<Location> getAllReferencesGroupByLocation(JavaItem sourceItem) {
        
        DatabaseModel dbInstance = DatabaseModel.getInstance();
        Table dbObjectTable = dbInstance.getTable(sourceItem.getMappedTable());
        Table classRefTable = dbInstance.getTable("CLASS_REFERENCES");

        String getRefsJoinClause = 
            dbInstance.getJoinClause(dbObjectTable, classRefTable);

        // Methods and Field level are not supported

        QueryHelper<Location> queryHelper = new QueryHelper<Location>();
        List<Location> referencesList = queryHelper.executeQuery(
                ConnectionManager.getInstance().getConnection(),                
                "SELECT DISTINCT LOCATIONS.* FROM " 
                + "(SELECT DISTINCT cname "
                + " FROM " + getRefsJoinClause
                + " WHERE " + dbObjectTable.getPrimaryKeyFieldName() + " = " + sourceItem.getKey() + ") T1"
                + " JOIN CLASSES ON CLASSES.cname = T1.cname " 
                + " JOIN PACKAGES ON CLASSES.package_key = PACKAGES.package_key " 
                + " JOIN LOCATIONS ON PACKAGES.location_key = LOCATIONS.location_key",                
                new RowConverter<Location>() {
                    public Location getRow(ResultSet rs) throws SQLException {
                        Location loc = new Location(rs);
                        return loc;
                    }            
                });

        return referencesList;        
    }

    public static List<Package> getAllReferencesGroupByPackage(JavaItem dbObject) {

        DatabaseModel dbInstance = DatabaseModel.getInstance();
        Table dbObjectTable = dbInstance.getTable(dbObject.getMappedTable());
        Table classRefTable = dbInstance.getTable("CLASS_REFERENCES");        

        String getRefsJoinClause = 
            dbInstance.getJoinClause(dbObjectTable, classRefTable);

        // Methods and Field level are not supported

        QueryHelper<Package> queryHelper = new QueryHelper<Package>();
        List<Package> referencesList = queryHelper.executeQuery(
                ConnectionManager.getInstance().getConnection(),                
                "SELECT DISTINCT PACKAGES.* FROM " 
                + "(SELECT DISTINCT cname "
                + " FROM " + getRefsJoinClause
                + " WHERE " + dbObjectTable.getPrimaryKeyFieldName() + " = " + dbObject.getKey() + ") T1"
                + " JOIN CLASSES ON CLASSES.cname = T1.cname " 
                + " JOIN PACKAGES ON CLASSES.package_key = PACKAGES.package_key ",                
                new RowConverter<Package>() {
                    public Package getRow(ResultSet rs) throws SQLException {
                        Package p = new Package(rs);
                        return p;
                    }            
                });

        return referencesList;

    }

}    
