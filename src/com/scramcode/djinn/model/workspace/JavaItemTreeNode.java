/*
 * Created on Feb 24, 2006
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

import java.util.Enumeration;
import java.util.Vector;

import javax.swing.tree.TreeNode;

import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.model.DecoratedNode;


public abstract class JavaItemTreeNode implements TreeNode, DecoratedNode {    
    
    private JavaItem javaItem;    
    private JavaItemTreeNode parent;
    private Vector<JavaItemTreeNode> children;    
    
    public JavaItemTreeNode(JavaItemTreeNode parent) {
        this.parent = parent;
        this.children = new Vector<JavaItemTreeNode>();
    }
    
    public JavaItemTreeNode(JavaItemTreeNode parent, JavaItem dataObject) {
        this.parent = parent;
        this.children = new Vector<JavaItemTreeNode>();
        this.javaItem = dataObject;                
    }          
    
    public String getLabel() {
        if (javaItem != null) {
            return javaItem.getLabel();
        }
        return "";
    }
    
    public void refresh() {
        children = computeChildren();        
    }
    
    protected abstract Vector<JavaItemTreeNode> computeChildren();

    public TreeNode getChildAt(int childIndex) {
        return children.get(childIndex);
    }

    public int getChildCount() {
        return children.size();
    }

    public TreeNode getParent() {
        return parent;
    }
    
    public JavaItemTreeNode getDBTreeNodeParent() {
        return parent;
    }

    public int getIndex(TreeNode node) {
        return children.indexOf(node);
    }

    public boolean getAllowsChildren() {
        return true;
    }

    public boolean isLeaf() {
        return false;
    }

    public Enumeration<JavaItemTreeNode> children() {
        return children.elements();
    }

    public JavaItem getJavaItem() {
        return this.javaItem;
    }

}
