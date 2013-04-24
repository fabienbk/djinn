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
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;

import com.scramcode.djinn.ui.i18n.Images;


public class Location extends AbstractJavaItem {

	public static final int NO_PROJECT_ROOT = -1;
    public static final int JAR_LOCATION_TYPE = 0;
    public static final int DIR_LOCATION_TYPE = 1;    
   
    public static final ImageIcon ICON = Images.getIcon("Jar.graph.icon");
    
    private int locationKey;
    private String absolutePath;
    private int type;    
    private File pathFile;
    private Integer projectKey;

    /** full constructor */
    public Location(String absolutePath, int type, int projectKey) {
        this.absolutePath = absolutePath;        
        this.type = type;
        this.projectKey = projectKey;
        this.pathFile = new File(absolutePath);
    }

    public Location(ResultSet rs) throws SQLException {
        this.locationKey = rs.getInt("location_key");
        this.absolutePath = rs.getString("absolute_path");
        this.type = rs.getInt("type");
        this.projectKey = rs.getInt("project_key");
        this.pathFile = new File(absolutePath);        
    }

    public Location(String absolutePath, int type) {
    	 this.absolutePath = absolutePath;        
         this.type = type;
         this.projectKey = null;
         this.pathFile = new File(absolutePath);
	}

	public int getKey() {
        return this.locationKey;
    }

    public void setKey(int locationKey) {
        this.locationKey = locationKey;
    }

    public String getAbsolutePath() {
        return this.absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public File getPathFile() {
        return this.pathFile;
    }

    public Integer getProjectKey() {
        return this.projectKey;
    }

    public void setProjectKey(int projectKey) {
        this.projectKey = projectKey;
    }
    
    public String getMappedTable() {
        return "LOCATIONS";
    }
    
    @Override
    public String getLabel() {
        return pathFile.getName();
    }
    
    @Override
    public ImageIcon getImage() {
        return ICON;
    }
    
    @Override
    public Color getColor() {
        return Color.yellow;
    }

    @Override
    public boolean isContainedBy(AbstractJavaItem destinationObject) {
        return false;
    }
    
}
