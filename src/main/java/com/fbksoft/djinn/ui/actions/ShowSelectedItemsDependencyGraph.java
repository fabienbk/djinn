/*
 * Created on Feb 8, 2006
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
package com.fbksoft.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.fbksoft.djinn.db.data.JavaDependency;
import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.logic.ReferenceTools;
import com.fbksoft.djinn.graph.GraphTools;
import com.fbksoft.djinn.ui.Application;
import com.fbksoft.djinn.ui.i18n.Messages;
import com.fbksoft.djinn.util.AbstractSwingWorker;

import edu.uci.ics.jung.graph.Graph;


/**
 * Show the complete dependencies between top level items of the workspace.
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class ShowSelectedItemsDependencyGraph extends AbstractAction {
    
    private static final long serialVersionUID = 1L;    
    
    public ShowSelectedItemsDependencyGraph() {
        putValue(Action.NAME, Messages.getString("ShowSelectedItemsDependencyGraph.label"));
    }
    
    private static Logger logger = Logger.getLogger(ShowSelectedItemsDependencyGraph.class.getName());
    
    /*public boolean checkEnabled() {   	
    	if (Application.getInstance() != null) {
    		List<JavaItem> selection = Application.getInstance().getWorkspaceTreeController().getSelection();    		
    		return selection!= null && selection.size() > 1;    		    	
    	}    	    	
    	return false;
    }*/
    
    public void actionPerformed(ActionEvent e) {    	
    	    	    	
        AbstractSwingWorker worker = new AbstractSwingWorker(true) {
            @Override
            public Object construct() {
                
                updateMessage("Computing Dependencies...");
                updateProgress(0);
                
                List<JavaItem> selectedItems = Application.getInstance().getWorkspaceTreeController().getSelection();
                
                logger.info("selectedItems: " + selectedItems);
                                
                Map<JavaItem, Set<JavaItem>> graphData = new HashMap<JavaItem, Set<JavaItem>>();
                
                int progress = 0;                    
								int delta = 100;                
								if (selectedItems.size() > 0) {
									delta = 100 / selectedItems.size();                
								}
                
                for (JavaItem javaItem : selectedItems) {
                	graphData.put(javaItem, ReferenceTools.getAllReferencesFromSubSet(javaItem, selectedItems));
                	updateMessage("Checking " + javaItem.getLabel() + "...");
                	updateProgress(progress+=delta);                    
				}    
                                    
                updateMessage("Done!");
                updateProgress(100);
                    
                return GraphTools.createGraph(graphData);                               
            }
            
            /**
             * Display the graph, once built
             */
            @SuppressWarnings("unchecked")
			@Override
            public void finished() {
            	Graph<JavaItem, JavaDependency> builtGraph = (Graph<JavaItem, JavaDependency>) getValue();
            	if (builtGraph!=null) {            	            		
            		Application.getInstance().getGraphAreaController().showGraph("Top level item graph", (Graph<JavaItem, JavaDependency>) getValue());    	
            	}            	
            }
            
        };
        
        worker.start();
    }
    
}
