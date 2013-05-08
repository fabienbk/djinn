/*
 * Created on Dec 5, 2006
 * Created by Fabien Benoit
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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.logic.ReferenceTools;
import com.scramcode.djinn.model.DependencyListModel;
import com.scramcode.djinn.model.GraphGranularityComboBoxModel;
import com.scramcode.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;
import com.scramcode.djinn.model.workspace.AbstractJavaItemTreeNode;
import com.scramcode.djinn.model.workspace.ClassNode;
import com.scramcode.djinn.model.workspace.LocationNode;
import com.scramcode.djinn.model.workspace.PackageNode;
import com.scramcode.djinn.util.AbstractSwingWorker;

import edu.uci.ics.jung.graph.Graph;

/**
 * Control the panel displaying a dependency graph. 
 *
 * @author Fabien BENOIT
 */
public class DependencyGraphPanelController implements TreeExpansionListener, TreeSelectionListener, ActionListener {
    
    private Graph<JavaItem, JavaDependency> graph;
    private DependencyGraphPanel graphPanel;
    private DependencyDetailsPanel detailsPanel;
    
    public DependencyGraphPanelController(Graph<JavaItem, JavaDependency> graphInfo) {
        this.graph = graphInfo;        
        this.graphPanel = new DependencyGraphPanel(graphInfo);
                
        graphPanel.getPickedState().addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {
        	   if(e.getItem() instanceof JavaDependency) {
        		   onEdgeSelectionEvent((JavaDependency)e.getItem());
        	   }
           }
        });
        
        // Build models for the dependencies details panel        
        detailsPanel = graphPanel.getDependencyDetailsPanel();                
        detailsPanel.getNodeTree().addTreeExpansionListener(this);
        detailsPanel.getNodeTree().addTreeSelectionListener(this);
        detailsPanel.getNodeTree().setCellRenderer(new WorkspaceTreeCellRenderer());
                
        detailsPanel.getDependencyList().setCellRenderer(new DependencyDetailsListCellRenderer());
        detailsPanel.getGranularityComboBox().addActionListener(this);
        
        
        clearDependencyDetailsPanel();                
    }
    
    public DependencyGraphPanel getPanel() {
        return graphPanel;
    }
    
    public Graph<JavaItem, JavaDependency>  getGraph() {
        return graph;
    }
    
    private void clearDependencyDetailsPanel() {
        detailsPanel.getNodeTree().setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        detailsPanel.getNodeTree().setEnabled(false);
        detailsPanel.getDependencyList().setModel(new DefaultListModel());
        detailsPanel.getDependencyList().setEnabled(false);
        detailsPanel.getGranularityComboBox().setEnabled(false);
    }
    
    /**
     * Update the dependecy details panel. This is normally triggered on edge selection.
     */
    private void onEdgeSelectionEvent(JavaDependency javaDependency) {
        
        Set<JavaDependency> pickedEdges = graphPanel.getPickedState().getPicked();
        if (pickedEdges.size() == 0) {
            clearDependencyDetailsPanel();            
        }
        else {
            detailsPanel.getNodeTree().setEnabled(true);
            detailsPanel.getDependencyList().setEnabled(true);
            detailsPanel.getGranularityComboBox().setEnabled(true);
            
            detailsPanel.getDependencyList().setModel(new DependencyListModel());
            detailsPanel.getDependencyList().repaint();
          
            final JavaItem fromDBOject = javaDependency.getSourceItem();
            final JavaItem toDBOject = javaDependency.getDestinationItem();
            
            // Current selected granularity
            final GranularityLevel granularityLevel = ((GraphGranularityComboBoxModel)detailsPanel.getGranularityComboBox().getModel()).getGranularity();
                
            if (fromDBOject instanceof Location) {
                LocationNode locationNode = new LocationNode(null, (Location)fromDBOject);
                locationNode.refresh();
                ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).setRoot(locationNode);
            }
            else if (fromDBOject instanceof com.scramcode.djinn.db.data.Package) {
                PackageNode packageNode = new PackageNode(null, (com.scramcode.djinn.db.data.Package)fromDBOject);
                packageNode.refresh();
                ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).setRoot(packageNode);
            }
            else if (fromDBOject instanceof com.scramcode.djinn.db.data.Clazz) {
                ClassNode classNode = new ClassNode(null, (com.scramcode.djinn.db.data.Clazz)fromDBOject, false);                
                ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).setRoot(classNode);
            }
            
            AbstractSwingWorker worker = new AbstractSwingWorker() {            	
                @Override
                public Object construct() {
                    return ReferenceTools.getReferences(fromDBOject, toDBOject, granularityLevel);
                }
                
                @Override
                public void finished() {
                    if (getValue() != null) {                        
                        DependencyListModel model = new DependencyListModel((List<JavaItem>)getValue());                        
                        detailsPanel.getDependencyList().setModel(model);                        
                    }                    
                }
                                
            };      
            worker.start();
            
        }        
    }

    public void treeCollapsed(TreeExpansionEvent event) {
        // TODO Auto-generated method stub        
    }

    public void treeExpanded(TreeExpansionEvent event) {
    	AbstractJavaItemTreeNode treeNode = (AbstractJavaItemTreeNode)event.getPath().getLastPathComponent();
        treeNode.refresh();
        ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).reload(treeNode);
    }

    /**
     * TreeSelectionListener callback
     */
    public void valueChanged(TreeSelectionEvent e) {
    	updateDependencyListOnUserChange(); 
    }

	private void updateDependencyListOnUserChange() {

		// Clear the dependency list
		detailsPanel.getDependencyList().setModel(new DependencyListModel());
		detailsPanel.getDependencyList().repaint();
		
		// Get the selected 'origin' object
		TreePath selectionPath = detailsPanel.getNodeTree().getSelectionPath();
		if (selectionPath == null) {
			return;
		}
		final JavaItem fromJavaItem = ((AbstractJavaItemTreeNode)selectionPath.getLastPathComponent()).getJavaItem();

		// Get the current granularity
		final GranularityLevel granularityLevel 
		= ((GraphGranularityComboBoxModel)
				detailsPanel.getGranularityComboBox().getModel()).getGranularity();
    		
		// Get the current selected 'target' vertex in the graph
		Set<JavaDependency> pickedEdges = graphPanel.getPickedState().getPicked();
		JavaDependency pickedEdge = pickedEdges.iterator().next();            
		final JavaItem toJavaItem = pickedEdge.getDestinationItem();
		            
		AbstractSwingWorker  worker = new AbstractSwingWorker() {
		    @Override
		    public Object construct() {
		        return ReferenceTools.getReferences(fromJavaItem, toJavaItem, granularityLevel);
		    }
		    
		    @Override
		    public void finished() {
		        if (getValue() != null) {                        
		            DependencyListModel model =  new DependencyListModel((List<JavaItem>)getValue());		            
		            detailsPanel.getDependencyList().setModel(model);                        
		        }                    
		    }
		    
		};      
		worker.start();
	}

    public void actionPerformed(ActionEvent e) {
		updateDependencyListOnUserChange();    	
    }

        
}
