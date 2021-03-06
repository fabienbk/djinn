/*
 * Created on Feb 27, 2006
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
package com.fbksoft.djinn.model.workspace;

import java.util.List;
import java.util.Vector;

import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.db.data.Package;


public class LocationNode extends AbstractJavaItemTreeNode {
    
    public LocationNode(AbstractJavaItemTreeNode parent, Location location) {
        super(parent, location);        
    }
    
    @Override
    protected Vector<AbstractJavaItemTreeNode> computeChildren() {        
    	Vector<AbstractJavaItemTreeNode> children = new Vector<AbstractJavaItemTreeNode>();
    	Location location = (Location)getJavaItem();
    	List<Package> packages = location.getPackages();
    	for (Package packageObject : packages) {
    		PackageNode packageNode = new PackageNode(LocationNode.this, packageObject);
			children.add(packageNode);			
		}
        return children;
    }
    
}
