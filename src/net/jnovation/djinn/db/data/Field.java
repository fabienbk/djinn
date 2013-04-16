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
package net.jnovation.djinn.db.data;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

public class Field extends JavaItem {

    private int fieldKey;
    private int classKey;
    private String name;
    private int access;

    /** full constructor */
    public Field(String name, int access, int classKey) {
        this.name = name;
        this.classKey = classKey;
        this.access = access;
    }
    
    public Field(ResultSet rs) throws SQLException {
        this.name = rs.getString("field_key");
        this.name = rs.getString("name");
        this.classKey = rs.getInt("class_key");
        this.access = rs.getInt("access");
    }    

    public int getKey() {
        return this.fieldKey;
    }

    public void setKey(int fieldKey) {
        this.fieldKey = fieldKey;
    }
    
    public int getAccess() {
        return this.access;
    }

    public void setAccess(int access) {
        this.access = access;
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getClassKey() {
        return this.classKey;
    }

    public void setClassKey(int classKey) {
        this.classKey = classKey;
    }
    
    public String getMappedTable() {
        return "FIELDS";
    }

    @Override
    public String getLabel() {
        return name;
    }
    
    @Override
    public ImageIcon getImage() {
        return null;
    }
    
    @Override
    public Color getColor() {
        return Color.RED;
    }

    @Override
    public boolean isContainedBy(JavaItem destinationObject) {
        return false;
    }
    
}
