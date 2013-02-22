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
package net.jnovation.djinn.db.meta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseModel {
    
    private static DatabaseModel instance = new DatabaseModel();
    
    public static DatabaseModel getInstance() {
        return instance;
    }
    
    private HashMap<String, Table> tables = new HashMap<String, Table>();

    private DatabaseModel() {
        Table projectsTable = new Table("PROJECTS", 1, "project_key");
        Table locationsTable = new Table("LOCATIONS", projectsTable, 2, "location_key", "project_key");
        Table packagesTable = new Table("PACKAGES", locationsTable, 3, "package_key", "location_key");
        Table classesTable = new Table("CLASSES", packagesTable, 4, "class_key", "package_key");
        Table methodsTable = new Table("METHODS", classesTable, 5, "method_key", "class_key");
        Table fieldsTable = new Table("FIELDS", classesTable, 5, "field_key", "class_key");
        Table referencesTable = new Table("CLASS_REFERENCES", classesTable, 5, "class_reference_key", "class_key");

        tables.put("PROJECTS", projectsTable);
        tables.put("LOCATIONS", locationsTable);
        tables.put("PACKAGES", packagesTable);
        tables.put("CLASSES", classesTable);
        tables.put("METHODS", methodsTable);
        tables.put("FIELDS", fieldsTable);
        tables.put("CLASS_REFERENCES", referencesTable);
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }
    
    public List<Table> getTablePath(Table table1, Table table2) {
        List<Table> result = new ArrayList<Table>();
        if (table1.getIndex() == table2.getIndex()) {
            throw new IllegalArgumentException("No path between tables : " + table1.getName() + " and " + table2.getName());
        }
        
        Table startTable = null;
        Table endTable = null;
        if(table1.getIndex() < table2.getIndex()) {
            startTable = table2;
            endTable = table1;   
        }
        else {            
            startTable = table1;
            endTable = table2;   
        }        
        while(startTable != endTable) {
            result.add(startTable);
            startTable = startTable.getParent();
        }
        result.add(endTable);
        return result;
    }
    
    public String getJoinClause(Table table1, Table table2) {
        return getJoinClause(getTablePath(table1, table2));
    }
    
    public String getJoinClause(List<Table> path) {
        
        if (path.size() < 2) {
            throw new IllegalArgumentException("Path is too short : " + path.size());
        }
        
        Table[] pathArray = path.toArray(new Table[path.size()]);
        
        StringBuffer buffer = new StringBuffer();
        buffer.append(pathArray[0].getName());
        for (int i = 1; i < pathArray.length; i++) {                                                    
                Table leftTable = pathArray[i-1];
                Table rightTable = pathArray[i];                
                buffer.append(" JOIN " + rightTable.getName() + " ON " 
                        + leftTable.getName() + "." + leftTable.getForeignKeyFieldName() + " = "
                        + rightTable.getName() + "." + rightTable.getPrimaryKeyFieldName());                            
        }
                
        return buffer.toString();        
    }
    
    public static void main(String[] args) {        
        List<Table> path = DatabaseModel.getInstance().getTablePath(
                DatabaseModel.getInstance().getTable("CLASSES"), 
                DatabaseModel.getInstance().getTable("PROJECTS"));
        
        for (int i = 0; i < path.size(); i++) {
            System.out.println(path.get(i).getName());
        }        
        
        System.out.println(DatabaseModel.getInstance().getJoinClause(path));
    }
        
}
