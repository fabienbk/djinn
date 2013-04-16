/*
 * Created on Dec 21, 2005
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
package net.jnovation.djinn.model;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.db.mgmt.QueryHelper;

public class SQLResultSetTableModel extends AbstractTableModel {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1309080597486108977L;
    
    private int rowCount = 0;
    private int columnCount = 0;
    private List<String> columnNameList = new ArrayList<String>();
    private ArrayList<ArrayList<Object>> data = new ArrayList<ArrayList<Object>>();
    
    public void update(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = ConnectionManager.getInstance().getConnection().createStatement();
            rs = stmt.executeQuery(query);                    
            update(rs);                    
        }
        finally {
            QueryHelper.close(stmt, rs);
        }  
    }
    
    @Override
    public String getColumnName(int column) {
    	return columnNameList.get(column);
    }
    
    public void update(ResultSet rs) throws SQLException {
                        
        data.clear();
        
        ResultSetMetaData metas = rs.getMetaData();
        this.columnCount = metas.getColumnCount();        
        
        // update columns names
        columnNameList.clear();
        for (int i = 0; i < columnCount; i++) {
        	columnNameList.add(rs.getMetaData().getColumnName(i+1));	
		}
                
        while(rs.next()) {
            ArrayList<Object> currentRecord = new ArrayList<Object>();
            for (int i = 0; i < columnCount; i++) {
                currentRecord.add( rs.getObject(i+1) );
            }
            data.add(currentRecord);
        }        
        
        this.rowCount = data.size();
        
        fireTableStructureChanged();
        fireTableDataChanged();
    }
    
    public int getRowCount() {
        return rowCount;
    }
    
    public int getColumnCount() {
        return columnCount;
    }
    
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }
    
}
