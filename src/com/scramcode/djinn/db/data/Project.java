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

public class Project extends JavaItem {

    private int projectKey;
    private String projectName;
    
    /**
     * @param key
     * @param name
     */
    public Project(String name) {        
        this.projectName = name;
    }
    
    public Project(ResultSet rs) throws SQLException {
        this.projectKey = rs.getInt("project_key");
        this.projectName = rs.getString("project_name");        
    }    
    
    public String getMappedTable() {
        return "PROJECTS";
    }

    public int getKey() {
        return this.projectKey;
    }
    public void setKey(int projectKey) {
        this.projectKey = projectKey;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    @Override
    public String getLabel() {
        return projectName;
    }
    
    @Override
    public ImageIcon getImage() {
        return null;
    }
    
    @Override
    public Color getColor() {
        return Color.BLUE;
    }

    @Override
    public boolean isContainedBy(JavaItem destinationObject) {
        return false;
    }
       
}
