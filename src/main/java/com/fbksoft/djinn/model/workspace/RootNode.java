/*
 * Created on Mar 1, 2006
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

import javax.swing.Icon;

import com.fbksoft.djinn.db.data.DataHelper;
import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.db.data.Project;
import com.fbksoft.djinn.db.data.Workspace;
import com.fbksoft.djinn.ui.i18n.Images;
import com.fbksoft.djinn.ui.i18n.Messages;


public class RootNode extends AbstractJavaItemTreeNode {

    private static final Icon ICON = Images.getIcon("Workspace.icon"); 
    
    public RootNode() {
        super(null);
    }

    protected Vector<AbstractJavaItemTreeNode> computeChildren() {
    	Vector<AbstractJavaItemTreeNode> children = new Vector<AbstractJavaItemTreeNode>();
    	Workspace workspace = DataHelper.getWorkspace();    	
    	List<JavaItem> topLevelItems = workspace.getTopLevelItems();
    	for (JavaItem javaItem : topLevelItems) {
    		if (javaItem instanceof Project) {
    			ProjectNode projectNode = new ProjectNode(RootNode.this, (Project)javaItem);
    			children.add(projectNode);
    		}
    		else if (javaItem instanceof Location) {    			
    			LocationNode locationNode = new LocationNode(RootNode.this, (Location)javaItem);
    			children.add(locationNode);
    		}
		}    	
        return children;
    }
    
    @Override
    public String getLabel() {
        return Messages.getString("Workspace.label");
    }

    public Icon getIcon() {
        return ICON;
    }
    
}
