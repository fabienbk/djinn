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
package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.scramcode.djinn.db.data.Class;
import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Package;
import com.scramcode.djinn.db.logic.ReferenceTools;
import com.scramcode.djinn.graph.GraphTools;
import com.scramcode.djinn.model.workspace.JavaItemTreeNode;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;
import com.scramcode.djinn.util.SwingWorker;

import edu.uci.ics.jung.graph.Graph;


/**
 * If a node is selected, compute the transitive closure of its dependencies and
 * display them in a graph.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public abstract class ShowDependenciesAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    public static class ShowLinksWithJarsAction extends ShowDependenciesAction {
        private static final long serialVersionUID = 1L;
        public ShowLinksWithJarsAction(boolean enabled) {
            super(enabled);
            putValue(Action.SMALL_ICON, Images.getIcon("ShowLinksWithJarsAction.icon"));
            putValue(Action.NAME, Messages.getString("ShowLinksWithJarsAction.label"));
        }
        @Override
        public List<Location> getReferences(JavaItem dbObject) {
            return  ReferenceTools.getAllReferencesGroupByLocation(dbObject);
        }
    }
    
    public static class ShowLinksWithPackagesAction extends ShowDependenciesAction {
        private static final long serialVersionUID = 1L;
        public ShowLinksWithPackagesAction(boolean enabled) {
            super(enabled);
            putValue(Action.SMALL_ICON, Images.getIcon("ShowLinksWithPackagesAction.icon"));
            putValue(Action.NAME, Messages.getString("ShowLinksWithPackagesAction.label"));
        }
        @Override
        public List<Package> getReferences(JavaItem dbObject) {
            return  ReferenceTools.getAllReferencesGroupByPackage(dbObject);
        }
    }
    
    public static class ShowLinksWithClassesAction extends ShowDependenciesAction {
        private static final long serialVersionUID = 1L;
        public ShowLinksWithClassesAction(boolean enabled) {
            super(enabled);
            putValue(Action.SMALL_ICON, Images.getIcon("ShowLinksWithClassesAction.icon"));
            putValue(Action.NAME, Messages.getString("ShowLinksWithClassesAction.label"));
        }
        @Override
        public List<Class> getReferences(JavaItem dbObject) {
            return  ReferenceTools.getAllReferencesGroupByClass(dbObject);
        }
    }


    public ShowDependenciesAction(boolean enabled) {
        setEnabled(enabled);
    }

    public void actionPerformed(ActionEvent e) {

        SwingWorker worker = new SwingWorker(true) {
            @Override
            public Object construct() {
                
                updateMessage("Computing Dependencies...");
                updateProgress(0);

                Application instance = Application.getInstance();
                JavaItemTreeNode selectedNode = instance.getWorkspaceTreeController()
                        .getSelectedNode();

                JavaItem javaItem = selectedNode.getJavaItem();

                if (javaItem instanceof Location || javaItem instanceof Package
                        || javaItem instanceof Class) {
                    
                    Set<JavaItem> locRefList = new HashSet<JavaItem>(getReferences(javaItem));
                    
                    updateMessage("Creating Graph...");
                    updateProgress(70);

                    // Build graph data
                    Map<JavaItem, Set<JavaItem>> graphData = new HashMap<JavaItem, Set<JavaItem>>();

                    // Put central object in the vertices list, with it's references
                    graphData.put(javaItem, locRefList);

                    // Put referenced objects in the vertices list
                    for (Iterator<JavaItem> iter = locRefList.iterator(); iter
                            .hasNext();) {
                        JavaItem ref = iter.next();
                        if (!ref.equals(javaItem)) {
                            graphData.put(ref, new HashSet<JavaItem>());
                        }
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
            @SuppressWarnings("unchecked")
            public void finished() {
				Graph<JavaItem, JavaDependency> builtGraph = (Graph<JavaItem, JavaDependency>) getValue();
            	if (builtGraph!=null) {           

            		Application instance = Application.getInstance();
            		JavaItemTreeNode selectedNode = instance.getWorkspaceTreeController().getSelectedNode();
            		String label = selectedNode.getJavaItem().getLabel();
            		            		
            		Application.getInstance().getGraphAreaController().showGraph(label, (Graph<JavaItem, JavaDependency>) getValue());    	
            	}       
            }

        };

        worker.start();
    }
    
    public abstract List<? extends JavaItem> getReferences(JavaItem dbObject);

}
