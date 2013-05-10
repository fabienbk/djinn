/*
 * Created on Feb 8, 2006
 * Created by Fabien Benoit
 * 
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
package com.scramcode.djinn.ui.panels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ActionMap;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.model.workspace.AbstractJavaItemTreeNode;
import com.scramcode.djinn.model.workspace.RootNode;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.menu.WorkspaceTreeContextMenu;

public class WorkspaceTreeController implements 
    TreeSelectionListener,
    TreeExpansionListener,
    TreeWillExpandListener, TreeModelListener {

	private DefaultTreeModel treeModel = null;
    private JTree tree = null;
    private AbstractJavaItemTreeNode rootNode = null;
	
    private List<JavaItem> selection = new ArrayList<JavaItem>();
    
    public WorkspaceTreeController(Application application) {
        
        rootNode = new RootNode();
        treeModel = new DefaultTreeModel(rootNode);        
        tree = application.getApplicationFrame().getWorkspacePanel().getWorkspaceTree();        
        tree.setModel(treeModel);
        
        tree.setCellRenderer(new WorkspaceTreeCellRenderer());        
        tree.setShowsRootHandles(true);
        tree.setSelectionRow(0);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.DISCONTIGUOUS_TREE_SELECTION);
        
        tree.addTreeSelectionListener(this);
        tree.addTreeExpansionListener(this);
        tree.addTreeWillExpandListener(this);
        treeModel.addTreeModelListener(this);
        
        // JTree (nodes) Popup menu
        ActionMap actionMap = application.getRootActionMap();       
        WorkspaceTreeContextMenu popupMenu = new WorkspaceTreeContextMenu(actionMap);                         
        tree.addMouseListener(new WorkspaceTreePopupMouseListener(popupMenu));
        
        tree.collapseRow(0);        
    }

    public void valueChanged(TreeSelectionEvent e) {    	
    	selection.clear();
        TreePath[] paths = tree.getSelectionModel().getSelectionPaths();
        if (paths != null) {
	        for (TreePath treePath : paths) {
	        	Object[] pathObjects = treePath.getPath();
	        	for (Object object : pathObjects) {
					if (object instanceof AbstractJavaItemTreeNode && ((AbstractJavaItemTreeNode)object).getJavaItem() != null) {
						selection.add(((AbstractJavaItemTreeNode)object).getJavaItem());
					}
				}        	
			}
        }
    }        

    public void treeExpanded(TreeExpansionEvent event) {
        AbstractJavaItemTreeNode treeNode = (AbstractJavaItemTreeNode)event.getPath().getLastPathComponent();
        treeNode.refresh();
        treeModel.reload(treeNode);        
    }

    public void treeCollapsed(TreeExpansionEvent event) {
        // Do nothing.
    }

    public void treeWillExpand(TreeExpansionEvent event) {
        // Do nothing.        
    }

    public void treeWillCollapse(TreeExpansionEvent event) {
        // Do nothing.
    }
  
    public AbstractJavaItemTreeNode getRootNode() {
        return this.rootNode;
    }
    
    public void refreshNode(AbstractJavaItemTreeNode node) {
        node.refresh();
        treeModel.reload(); // will trigger TreeModel events handled below               
    }
    
    public void refresh() {
        rootNode.refresh();
        treeModel.reload(); // will trigger TreeModel events handled below               
    }

    // TreeModel listener
    
    public void treeNodesChanged(TreeModelEvent e) {
        tree.setSelectionPath(e.getTreePath());
    }

    public void treeNodesInserted(TreeModelEvent e) {
        tree.setSelectionPath(e.getTreePath());
    }

    public void treeNodesRemoved(TreeModelEvent e) {
        tree.setSelectionPath(e.getTreePath());
    }

    public void treeStructureChanged(TreeModelEvent e) {
        tree.setSelectionPath(e.getTreePath());
    }
    
    public List<JavaItem> getSelection() {
		return selection;
	}
    
}
