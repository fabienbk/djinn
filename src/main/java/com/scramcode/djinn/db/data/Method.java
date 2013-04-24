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

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

/** @author Hibernate CodeGenerator */
public class Method extends AbstractJavaItem {

    private int methodKey;
    private int access;
    private String name;
    private int classKey;
    
    public Method(String name, int access, int classKey) {        
        this.access = access;
        this.classKey = classKey;
        this.name = name;
    }
    
    @Override
    public String getMappedTable() {
        return "METHODS";
    }

    public Method(ResultSet rs) throws SQLException {
        this.access = rs.getInt("access");
        this.classKey = rs.getInt("class_key");
        this.methodKey = rs.getInt("method_key");
        this.name = rs.getString("name");
    }    

    public int getKey() {
        return this.methodKey;
    }

    public void setKey(int methodKey) {
        this.methodKey = methodKey;
    }

    public int getAccess() {
        return this.access;
    }

    public int getClassKey() {
        return this.classKey;
    }

    public String getName() {
        return this.name;
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
        return Color.red;
    }

    @Override
    public boolean isContainedBy(JavaItem destinationObject) {
        return false;
    }
}
