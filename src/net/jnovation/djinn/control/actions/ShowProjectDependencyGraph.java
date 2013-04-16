/*
 * Created on Feb 8, 2006
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
package net.jnovation.djinn.control.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import edu.uci.ics.jung.graph.Graph;

import net.jnovation.djinn.control.Application;
import net.jnovation.djinn.db.data.JavaItem;
import net.jnovation.djinn.db.data.DataHelper;
import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.data.Project;
import net.jnovation.djinn.db.logic.ReferenceTools;
import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.graph.GraphTools;
import net.jnovation.djinn.i18n.Images;
import net.jnovation.djinn.i18n.Messages;
import net.jnovation.djinn.model.workspace.JavaItemTreeNode;
import net.jnovation.djinn.util.SwingWorker;

/**
 * Show the complete dependencies between locations of a project.
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class ShowProjectDependencyGraph extends AbstractAction {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -5053966529305854614L;

    public ShowProjectDependencyGraph(boolean enabled) {
        setEnabled(enabled);
        putValue(Action.SMALL_ICON, Images.getIcon("ShowProjectDependencyGraph.icon"));
        putValue(Action.NAME, Messages.getString("ShowProjectDependencyGraph.label"));
    }
    
    public void actionPerformed(ActionEvent e) {
    	    	    	
        SwingWorker worker = new SwingWorker(true) {
            @Override
            public Object construct() {
                
                updateMessage("Computing Dependencies...");
                updateProgress(0);
                
                Application instance = Application.getInstance();
                JavaItemTreeNode selectedNode = instance.getWorkspaceTreeController().getSelectedNode();
                
                JavaItem dbObject = selectedNode.getJavaItem();
                
                if (dbObject instanceof Project) {
                                       
//                  Build graph data
                    Map<JavaItem, Set<JavaItem>> graphData = new HashMap<JavaItem, Set<JavaItem>>();
                                        
                    // Get all locations and transform them into vertices
                    List<Location> locationsList =
                    DataHelper.getLocations(ConnectionManager.getInstance().getConnection(), 
                            (Project)dbObject);
                    
                    for (Iterator iter = locationsList.iterator(); iter.hasNext();) {
                        Location element = (Location) iter.next();
                        graphData.put(element, new HashSet<JavaItem>());
                    }
                                            
                    int progress = 0;                    
                    int delta = 100 / locationsList.size();  
                                        
                    for (Iterator iter = locationsList.iterator(); iter.hasNext();) {
                        
                        progress+=delta;
                        updateProgress(progress);
                        
                        Location element = (Location) iter.next();
                        
                        // Get the references of the current element
                        List<JavaItem> elementRefList = ReferenceTools.getAllLocationsReferences(element);
                        Set<JavaItem> elementRefListFiltered = new HashSet<JavaItem>();
                        
                        // Check that all references are located in the vertices set
                        // Otherwise we have a dependency that is outside the project - delete it.
                        for (Iterator iterator = elementRefList.iterator(); iterator.hasNext();) {
                            JavaItem elementRef = (JavaItem) iterator.next();
                            if (!locationsList.contains(elementRef)) {
                                JOptionPane.showConfirmDialog(null, Messages.getString("warning.referenceIsOutsideTheProject"), 
                                        Messages.getString("warning"), JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE);                                
                            }
                            else {
                                elementRefListFiltered.add(elementRef);
                            }
                        }
                        
                        graphData.put(element, elementRefListFiltered);                        
                    }
                    
                    updateMessage("Done!");
                    updateProgress(100);
                    
                    return GraphTools.createGraph(graphData);
                }
                
                return null;                
            }
            
            /**
             * Display the graph, once built
             */
            @Override
            public void finished() {
            	Graph builtGraph = (Graph) getValue();
            	if (builtGraph!=null) {
            	
            		Application instance = Application.getInstance();
            		JavaItemTreeNode selectedNode = instance.getWorkspaceTreeController().getSelectedNode();
            		String projetName = selectedNode.getJavaItem().getLabel();
            		
                        Application.getInstance().getGraphAreaController().showGraph(projetName, (Graph) getValue());    	
            	}            	
            }
            
        };
        
        worker.start();
    }
    
}
