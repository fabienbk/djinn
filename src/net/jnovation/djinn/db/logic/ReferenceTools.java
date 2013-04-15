/*
 * Created on Mar 3, 2006
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
package net.jnovation.djinn.db.logic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.jnovation.djinn.db.data.Class;
import net.jnovation.djinn.db.data.JavaItem;
import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.data.Package;
import net.jnovation.djinn.db.meta.DatabaseModel;
import net.jnovation.djinn.db.meta.Table;
import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.db.mgmt.QueryHelper;
import net.jnovation.djinn.db.mgmt.RowConverter;
import net.jnovation.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;

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
        List<JavaItem> references = null;
        switch(granularityLevel) {
            case CLASS: {
                references = getAllClassReferences(sourceObject);
                break;
            }
            case JAR: {
                references = getAllLocationsReferences(sourceObject);
                break;
            }    
            case PACKAGE: {
                references = getAllPackagesReferences(sourceObject);
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

    public static List<JavaItem> getAllClassReferences(JavaItem dbObject) {
        
        DatabaseModel dbInstance = DatabaseModel.getInstance();
        Table dbObjectTable = dbInstance.getTable(dbObject.getMappedTable());
        Table classRefTable = dbInstance.getTable("CLASS_REFERENCES");        

        String getRefsJoinClause = 
            dbInstance.getJoinClause(dbObjectTable, classRefTable);

        // Methods and Field level are not supported

        QueryHelper<JavaItem> queryHelper = new QueryHelper<JavaItem>();
        List<JavaItem> referencesList = queryHelper.executeQuery(
                ConnectionManager.getInstance().getConnection(),                
                "SELECT DISTINCT CLASSES.* FROM " 
                + "(SELECT DISTINCT cname "
                + " FROM " + getRefsJoinClause
                + " WHERE " + dbObjectTable.getPrimaryKeyFieldName() + " = " + dbObject.getKey() + ") T1"
                + " JOIN CLASSES ON CLASSES.cname = T1.cname ",                
                new RowConverter<JavaItem>() {
                    public Class getRow(ResultSet rs) throws SQLException {
                        Class p = new Class(rs);
                        return p;
                    }            
                });

        return referencesList;

    }

    public static List<JavaItem> getAllLocationsReferences(JavaItem dbObject) {
        
        DatabaseModel dbInstance = DatabaseModel.getInstance();
        Table dbObjectTable = dbInstance.getTable(dbObject.getMappedTable());
        Table classRefTable = dbInstance.getTable("CLASS_REFERENCES");

        String getRefsJoinClause = 
            dbInstance.getJoinClause(dbObjectTable, classRefTable);

        // Methods and Field level are not supported

        QueryHelper<JavaItem> queryHelper = new QueryHelper<JavaItem>();
        List<JavaItem> referencesList = queryHelper.executeQuery(
                ConnectionManager.getInstance().getConnection(),                
                "SELECT DISTINCT LOCATIONS.* FROM " 
                + "(SELECT DISTINCT cname "
                + " FROM " + getRefsJoinClause
                + " WHERE " + dbObjectTable.getPrimaryKeyFieldName() + " = " + dbObject.getKey() + ") T1"
                + " JOIN CLASSES ON CLASSES.cname = T1.cname " 
                + " JOIN PACKAGES ON CLASSES.package_key = PACKAGES.package_key " 
                + " JOIN LOCATIONS ON PACKAGES.location_key = LOCATIONS.location_key",                
                new RowConverter<JavaItem>() {
                    public Location getRow(ResultSet rs) throws SQLException {
                        Location loc = new Location(rs);
                        return loc;
                    }            
                });

        return referencesList;        
    }

    public static List<JavaItem> getAllPackagesReferences(JavaItem dbObject) {

        DatabaseModel dbInstance = DatabaseModel.getInstance();
        Table dbObjectTable = dbInstance.getTable(dbObject.getMappedTable());
        Table classRefTable = dbInstance.getTable("CLASS_REFERENCES");        

        String getRefsJoinClause = 
            dbInstance.getJoinClause(dbObjectTable, classRefTable);

        // Methods and Field level are not supported

        QueryHelper<JavaItem> queryHelper = new QueryHelper<JavaItem>();
        List<JavaItem> referencesList = queryHelper.executeQuery(
                ConnectionManager.getInstance().getConnection(),                
                "SELECT DISTINCT PACKAGES.* FROM " 
                + "(SELECT DISTINCT cname "
                + " FROM " + getRefsJoinClause
                + " WHERE " + dbObjectTable.getPrimaryKeyFieldName() + " = " + dbObject.getKey() + ") T1"
                + " JOIN CLASSES ON CLASSES.cname = T1.cname " 
                + " JOIN PACKAGES ON CLASSES.package_key = PACKAGES.package_key ",                
                new RowConverter<JavaItem>() {
                    public Package getRow(ResultSet rs) throws SQLException {
                        Package p = new Package(rs);
                        return p;
                    }            
                });

        return referencesList;

    }

}    
