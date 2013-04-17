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

import com.scramcode.djinn.i18n.Images;


public class Package extends JavaItem {
    
    public static final ImageIcon icon = Images.getIcon("Package.graph.icon");
    
    private int packageKey;
    private int locationKey;
    private String qname;  
    
    public Package(String qname, int locationKey) {
        this.qname = qname;
        this.locationKey = locationKey;
    }
    
    @Override
    public String getMappedTable() {
        return "PACKAGES";
    }

    public Package(ResultSet rs) throws SQLException {
        this.packageKey = rs.getInt("package_key");
        this.qname = rs.getString("qname");
        this.locationKey = rs.getInt("location_key");  
    }
    
    
    public int getKey() {
        return this.packageKey;
    }

    public void setKey(int packageKey) {
        this.packageKey = packageKey;
    }

    public String getQname() {
        return this.qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }

    public int getLocationKey() {
        return this.locationKey;
    }

    public void setLocationKey(int locationKey) {
        this.locationKey = locationKey;
    }

    @Override
    public String getLabel() {
        return qname;
    }

    @Override
    public ImageIcon getImage() {
        return icon;
    }
    
    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
    
    @Override
    public boolean isContainedBy(JavaItem destinationObject) {        
        if (destinationObject instanceof Location) {           
            return (getLocationKey() == destinationObject.getKey());
        }
        return false;
    }
    

}
