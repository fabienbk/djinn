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

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.ui.Application;


import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public class GraphAreaController {

    private JTabbedPane graphAreaTabbedPane;
    private Map<String, JPanel> tabNames = new HashMap<String, JPanel>();
    
    public GraphAreaController(Application application) {
        this.graphAreaTabbedPane = application.getApplicationFrame().getGraphAreaPanel().getTabbedPane();
    }
    
    public void showGraph(String title, Graph<JavaItem, JavaDependency>  graphInfo) {
        DependencyGraphPanelController graphController = new DependencyGraphPanelController(graphInfo);
        addTab(title, graphController.getPanel());     
    }
    
    public void addTab(String title, JPanel panel) {                               
        graphAreaTabbedPane.add(title, panel);
        tabNames.put(title, panel);
    }       
 
    public void deleteTab(String title) {                               
        graphAreaTabbedPane.remove(tabNames.get(title));        
    }
    
    public Graph<JavaItem, JavaDependency> getActiveGraph() {
    	DependencyGraphPanel dependencyGraphPanel = (DependencyGraphPanel)graphAreaTabbedPane.getSelectedComponent();
    	if (dependencyGraphPanel != null) {
    		return dependencyGraphPanel.getGraph();
    	}
    	return null;
    }

    public void changeLayout(Layout<JavaItem, JavaDependency> newLayout) {
    	DependencyGraphPanel dependencyGraphPanel = (DependencyGraphPanel)graphAreaTabbedPane.getSelectedComponent();
    	dependencyGraphPanel.getVisualizationViewer().setGraphLayout(newLayout);
    }
}
