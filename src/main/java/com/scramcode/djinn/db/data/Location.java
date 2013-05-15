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
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.scramcode.djinn.db.logic.JavaItemVistor;
import com.scramcode.djinn.ui.i18n.Images;


public class Location extends AbstractJavaItem {

	public static final int NO_PROJECT_ROOT = -1;
    public static final int JAR_LOCATION_TYPE = 0;
    public static final int DIR_LOCATION_TYPE = 1;    
   
    public static final ImageIcon ICON = Images.getIcon("Jar.graph.icon");
    public static final ImageIcon ICON_SMALL = Images.getIcon("Jar.icon");
    
    private String absolutePath;
    private int type;    
    private File pathFile;
	private Project project;
	private List<Package> packages = new ArrayList<Package>();
    
    /** full constructor */
    public Location(String absolutePath, int type, Project project) {
        this.absolutePath = absolutePath;        
        this.type = type;
        this.project = project;
        this.pathFile = new File(absolutePath);
        
        if (project != null) {
        	project.getLocations().add(this);
        }
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

    public Project getProject() {
		return project;
	}

    
    @Override
    public String getLabel() {
        return pathFile.getName();
    }
    
    @Override
    public ImageIcon getIcon() {
        return ICON;
    }
    
    @Override
    public ImageIcon getSmallIcon() {    
    	return ICON_SMALL;
    }
    
    @Override
    public Color getColor() {
        return Color.yellow;
    }

	public List<Package> getPackages() {
		return packages;
	}
	
	@Override
	public void accept(JavaItemVistor javaItemVistor) {
		javaItemVistor.visitLocation(this);
		for (Package packageObj : packages) {
			packageObj.accept(javaItemVistor);
		}		
	}
	
	@Override
	public boolean isContainedBy(JavaItem destinationObject) {		
		if (project != null) {			
			return destinationObject.getKey() == project.getKey();			
		}
		else {
			return destinationObject.getKey() == getKey();
		}		
	}
    
	@Override
	public JavaItem getParent() {	
		return project != null ? project : this;
	}
}
