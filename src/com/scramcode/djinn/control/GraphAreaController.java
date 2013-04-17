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
package com.scramcode.djinn.control;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;


import edu.uci.ics.jung.graph.Graph;

public class GraphAreaController {

    private JTabbedPane graphAreaTabbedPane;
    private Map<String, JPanel> tabNames = new HashMap<String, JPanel>();
    
    public GraphAreaController(Application application) {
        this.graphAreaTabbedPane = application.getApplicationFrame().getGraphAreaPanel().getTabbedPane();
    }
    
    public void showGraph(String title, Graph<JavaItem, JavaDependency>  graphInfo) {
        GraphController graphController = new GraphController(graphInfo);
        addTab(title, graphController.getPanel());     
    }
    
    public void addTab(String title, JPanel panel) {                               
        graphAreaTabbedPane.add(title, panel);
        tabNames.put(title, panel);
    }       
 
    public void deleteTab(String title) {                               
        graphAreaTabbedPane.remove(tabNames.get(title));        
    }

}
