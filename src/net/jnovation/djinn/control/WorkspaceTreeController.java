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
package net.jnovation.djinn.control;

import javax.swing.ActionMap;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import net.jnovation.djinn.model.workspace.JavaItemTreeNode;
import net.jnovation.djinn.model.workspace.RootNode;
import net.jnovation.djinn.ui.menu.DBObjectContextMenu;

public class WorkspaceTreeController implements 
    TreeSelectionListener,
    TreeExpansionListener,
    TreeWillExpandListener, TreeModelListener {

    public DefaultTreeModel treeModel = null;
    public JTree tree = null;
    public JavaItemTreeNode selectedNode = null;
    public JavaItemTreeNode rootNode = null;
    
    public WorkspaceTreeController(Application application) {
        
        rootNode = new RootNode();
        treeModel = new DefaultTreeModel(rootNode);        
        tree = application.getApplicationFrame().getWorkspacePanel().getWorkspaceTree();        
        tree.setModel(treeModel);
        
        tree.setCellRenderer(new TreeCellRenderer());        
        tree.setShowsRootHandles(true);
        tree.setSelectionRow(0);
        tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        
        tree.addTreeSelectionListener(this);
        tree.addTreeExpansionListener(this);
        tree.addTreeWillExpandListener(this);
        treeModel.addTreeModelListener(this);
        
        // JTree (nodes) Popup menu
        ActionMap actionMap = application.getRootActionMap();       
        JPopupMenu popupMenu = new DBObjectContextMenu(actionMap);                         
        tree.addMouseListener(new PopupMouseListener(popupMenu));
        
        tree.collapseRow(0);        
    }

    public void valueChanged(TreeSelectionEvent e) {           
        this.selectedNode = (JavaItemTreeNode)e.getPath().getLastPathComponent();              
    }        

    public void treeExpanded(TreeExpansionEvent event) {
        JavaItemTreeNode treeNode = (JavaItemTreeNode)event.getPath().getLastPathComponent();
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
  
    public JavaItemTreeNode getSelectedNode() {
        return this.selectedNode;
    }

    public JavaItemTreeNode getRootNode() {
        return this.rootNode;
    }
    
    public void refreshNode(JavaItemTreeNode node) {
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
    
}
