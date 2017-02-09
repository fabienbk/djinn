/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.fbksoft.com
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
package com.fbksoft.djinn.db.data;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import com.fbksoft.djinn.db.logic.JavaItemVistor;
import com.fbksoft.djinn.ui.i18n.Images;


public class Package extends AbstractJavaItem {
    
    public static final ImageIcon ICON = Images.getIcon("Package.graph.icon");

    public static final ImageIcon ICON_SMALL = Images.getIcon("Package.icon");    
    
    private Location location;
    private String qname;

	private List<Clazz> clazzes = new ArrayList<Clazz>();  
    
    public Package(String qname, Location location) {
        this.qname = qname;
        this.location = location;
        
        location.getPackages().add(this);
    }
    
    @Override
    public JavaItem getParent() {    
    	return location;
    }
    
    public String getQname() {
        return this.qname;
    }

    public void setQname(String qname) {
        this.qname = qname;
    }
    
    @Override
    public String getLabel() {
        return qname;
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
        return Color.ORANGE;
    }
    
    public Location getLocation() {
		return location;
	}

	public List<Clazz> getClasses() {
		return clazzes;
	}

	@Override
	public void accept(JavaItemVistor javaItemVistor) {
		javaItemVistor.visitPackage(this);
		if (javaItemVistor.isClassVisitEnabled()) {
			for (Clazz clazz : clazzes) {
				clazz.accept(javaItemVistor);
			}
		}		
	}

	@Override
	public boolean isContainedBy(JavaItem destinationObject) {			
		return location.getKey() == destinationObject.getKey() || location.isContainedBy(destinationObject);
	}
}
