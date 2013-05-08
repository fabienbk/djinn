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

import javax.swing.Icon;

import com.scramcode.djinn.db.data.Clazz;
import com.scramcode.djinn.db.data.Package;
import com.scramcode.djinn.ui.i18n.Images;


public class PackageNode extends AbstractJavaItemTreeNode {
    
    private static final Icon ICON = Images.getIcon("Package.icon");    

    public PackageNode(LocationNode parent, Package dataObject) {
        super(parent, dataObject);
    }
    
    @Override
    protected Vector<AbstractJavaItemTreeNode> computeChildren() {        
        Vector<AbstractJavaItemTreeNode> children = new Vector<AbstractJavaItemTreeNode>();        
        Package packageObject = (Package)getJavaItem();
        List<Clazz> classes = packageObject.getClasses();
        for (Clazz clazz : classes) {
        	ClassNode classNode = new ClassNode(PackageNode.this, clazz);        
        	children.add(classNode);			
		}        
        return children;
    }

    public Icon getIcon() {
        return ICON;
    }


}
