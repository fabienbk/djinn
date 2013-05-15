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

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Set;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.ui.Application;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * Control the panel displaying a dependency graph. 
 *
 * @author Fabien BENOIT
 */
public class GraphPanelController {
    
	private Graph<JavaItem, JavaDependency> graph;
    private GraphPanel graphPanel;
	private PickedState<JavaItem> pickedVertexState;
	private PickedState<JavaDependency> pickedEdgeState;
        
    public GraphPanelController(Graph<JavaItem, JavaDependency> graphInfo) {
        this.graph = graphInfo;        
        this.graphPanel = new GraphPanel(graphInfo);                                                             
        
        VisualizationViewer<JavaItem, JavaDependency> visualizationViewer = graphPanel.getVisualizationViewer();
        
		this.pickedVertexState = visualizationViewer.getPickedVertexState();
		this.pickedVertexState.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				Application.getInstance().getWorkspaceTreeController().select(pickedVertexState.getPicked());
				
				Set<JavaDependency> picked = pickedEdgeState.getPicked();
				for (JavaDependency javaDependency : picked) {
					pickedEdgeState.pick(javaDependency, false);
				}
			}
		});		
		this.pickedEdgeState = visualizationViewer.getPickedEdgeState();
		this.pickedEdgeState.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				for (JavaItem javaItem : pickedVertexState.getPicked()) {
					pickedVertexState.pick(javaItem, false);
				}
				Set<JavaDependency> picked = pickedEdgeState.getPicked();
				for (JavaDependency javaDependency : picked) {
					Application instance = Application.getInstance();										
					instance.getWorkspaceTreeController().select(javaDependency.getSourceItem(), javaDependency.getDestinationItem());	
				}
			}
		});		
    }
    
    private void updateSelection() {		
    	
	}    
    
    public GraphPanel getPanel() {
        return graphPanel;
    }
    
    public Graph<JavaItem, JavaDependency>  getGraph() {
        return graph;
    }

    
}
