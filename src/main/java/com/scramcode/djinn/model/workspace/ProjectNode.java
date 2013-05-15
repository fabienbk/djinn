/*
 * Created on Mar 1, 2006
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
package com.scramcode.djinn.model.workspace;

import java.util.List;
import java.util.Vector;

import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Project;


public class ProjectNode extends AbstractJavaItemTreeNode {
    
    public ProjectNode(RootNode parent, Project project) {
        super(parent, project);
    }
        
    protected Vector<AbstractJavaItemTreeNode> computeChildren() {        
        Vector<AbstractJavaItemTreeNode> children = new Vector<AbstractJavaItemTreeNode>();
        Project project = (Project)getJavaItem();
        List<Location> locations = project.getLocations();
        for (Location location : locations) {
        	LocationNode locationNode = new LocationNode(ProjectNode.this, location);                                                          
        	children.add(locationNode);			
		}
        
        return children;
    }
         
    
}
