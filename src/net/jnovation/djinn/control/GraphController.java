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
package net.jnovation.djinn.control;

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

import net.jnovation.djinn.db.data.DBObject;
import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.logic.ReferenceTools;
import net.jnovation.djinn.model.DependencyListModel;
import net.jnovation.djinn.model.GraphGranularityComboBoxModel;
import net.jnovation.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;
import net.jnovation.djinn.model.workspace.ClassNode;
import net.jnovation.djinn.model.workspace.DBTreeNode;
import net.jnovation.djinn.model.workspace.LocationNode;
import net.jnovation.djinn.model.workspace.PackageNode;
import net.jnovation.djinn.ui.panels.DependencyDetailsPanel;
import net.jnovation.djinn.ui.panels.DependencyGraphPanel;
import net.jnovation.djinn.util.SwingWorker;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;

/**
 * Control the panel displaying a dependency graph. 
 *
 * @author Fabien BENOIT
 */
public class GraphController implements TreeExpansionListener, TreeSelectionListener, ActionListener {
    
    private Graph graph;
    private DependencyGraphPanel graphPanel;
    private DependencyDetailsPanel detailsPanel;
    
    public GraphController(Graph graphInfo) {
        this.graph = graphInfo;        
        this.graphPanel = new DependencyGraphPanel(graphInfo);
        
        graphPanel.getPickedState().addItemListener(new ItemListener() {
           public void itemStateChanged(ItemEvent e) {               
               if (e.getItem() instanceof Edge) {
                   onEdgeSelectionEvent();
               }
           }
        });
        
        // Build models for the dependencies details panel        
        detailsPanel = graphPanel.getDependencyDetailsPanel();                
        detailsPanel.getNodeTree().addTreeExpansionListener(this);
        detailsPanel.getNodeTree().addTreeSelectionListener(this);
        detailsPanel.getNodeTree().setCellRenderer(new TreeCellRenderer());
                
        detailsPanel.getDependencyList().setCellRenderer(new DependencyListCellRenderer());
        detailsPanel.getGranularityComboBox().addActionListener(this);
        
        
        clearDependencyDetailsPanel();                
    }
    
    public DependencyGraphPanel getPanel() {
        return graphPanel;
    }
    
    public Graph getGraph() {
        return graph;
    }
    
    private void clearDependencyDetailsPanel() {
        detailsPanel.getNodeTree().setModel(new DefaultTreeModel(new DefaultMutableTreeNode()));
        detailsPanel.getNodeTree().setEnabled(false);
        detailsPanel.getDependencyList().setModel(new DefaultListModel());
        detailsPanel.getDependencyList().setEnabled(false);
        detailsPanel.getGranularityComboBox().setEnabled(false);
    }

    
    private void onNodeSelectionEvent() {
        
    }
    
    /**
     * Update the dependecy details panel. This is normally triggered on edge selection.
     */
    private void onEdgeSelectionEvent() {
        
        Set<DirectedSparseEdge> pickedEdges = graphPanel.getPickedState().getPickedEdges();
        if (pickedEdges.size() == 0) {
            clearDependencyDetailsPanel();            
        }
        else {
            detailsPanel.getNodeTree().setEnabled(true);
            detailsPanel.getDependencyList().setEnabled(true);
            detailsPanel.getGranularityComboBox().setEnabled(true);
            
            detailsPanel.getDependencyList().setModel(new DependencyListModel());
            detailsPanel.getDependencyList().repaint();
            
            DirectedSparseEdge pickedEdge = pickedEdges.iterator().next();            
            Vertex from = pickedEdge.getSource();
            Vertex to = pickedEdge.getDest();
            
            final DBObject fromDBOject = (DBObject)from.getUserDatum(DBObject.class.getName());
            final DBObject toDBOject = (DBObject)to.getUserDatum(DBObject.class.getName());            
            
            // Current selected granularity
            final GranularityLevel granularityLevel 
            = ((GraphGranularityComboBoxModel)
                    detailsPanel.getGranularityComboBox().getModel()).getGranularity();
                
            if (fromDBOject instanceof Location) {
                LocationNode locationNode = new LocationNode(null, (Location)fromDBOject);
                locationNode.refresh();
                ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).setRoot(locationNode);
            }
            else if (fromDBOject instanceof net.jnovation.djinn.db.data.Package) {
                PackageNode packageNode = new PackageNode(null, (net.jnovation.djinn.db.data.Package)fromDBOject);
                packageNode.refresh();
                ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).setRoot(packageNode);
            }
            else if (fromDBOject instanceof net.jnovation.djinn.db.data.Class) {
                ClassNode classNode = new ClassNode(null, (net.jnovation.djinn.db.data.Class)fromDBOject, false);                
                ((DefaultTreeModel)detailsPanel.getNodeTree().getModel()).setRoot(classNode);
            }
            
            SwingWorker  worker = new SwingWorker() {
                @Override
                public Object construct() {
                    return ReferenceTools.getReferences(fromDBOject, toDBOject, granularityLevel);
                }
                
                @Override
                public void finished() {
                    if (getValue() != null) {                        
                        DependencyListModel model = 
                            new DependencyListModel((List<DBObject>)getValue());
                        
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
        DBTreeNode treeNode = (DBTreeNode)event.getPath().getLastPathComponent();
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
		final DBObject fromDBOject = ((DBTreeNode)selectionPath.getLastPathComponent()).getDataObject();

		// Get the current granularity
		final GranularityLevel granularityLevel 
		= ((GraphGranularityComboBoxModel)
				detailsPanel.getGranularityComboBox().getModel()).getGranularity();
    		
		// Get the current selected 'target' vertex in the graph
		Set<DirectedSparseEdge> pickedEdges = graphPanel.getPickedState().getPickedEdges();
		DirectedSparseEdge pickedEdge = pickedEdges.iterator().next();            
		Vertex to = pickedEdge.getDest();
		final DBObject toDBOject = (DBObject)to.getUserDatum(DBObject.class.getName());
		            
		SwingWorker  worker = new SwingWorker() {
		    @Override
		    public Object construct() {
		        return ReferenceTools.getReferences(fromDBOject, toDBOject, granularityLevel);
		    }
		    
		    @Override
		    public void finished() {
		        if (getValue() != null) {                        
		            DependencyListModel model = 
		                new DependencyListModel((List<DBObject>)getValue());
		            
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
