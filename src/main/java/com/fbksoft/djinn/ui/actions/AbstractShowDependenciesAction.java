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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.fbksoft.djinn.db.data.Clazz;
import com.fbksoft.djinn.db.data.JavaDependency;
import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.db.data.Package;
import com.fbksoft.djinn.db.logic.ReferenceTools;
import com.fbksoft.djinn.graph.GraphTools;
import com.fbksoft.djinn.ui.Application;
import com.fbksoft.djinn.ui.i18n.Images;
import com.fbksoft.djinn.ui.i18n.Messages;
import com.fbksoft.djinn.util.AbstractSwingWorker;

import edu.uci.ics.jung.graph.Graph;


/**
 * If a node is selected, compute the transitive closure of its dependencies and
 * display them in a graph.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public abstract class AbstractShowDependenciesAction extends AbstractAction {

    private static final long serialVersionUID = 1L;
    
    public AbstractShowDependenciesAction() {
    }

    @Override
    public boolean isEnabled() {
    	if (Application.getInstance() != null) {
    		List<JavaItem> selection = Application.getInstance().getWorkspaceTreeController().getSelection();
			return selection != null && selection.size() == 1;			
    	}
    	return false;
    }
    
    public void actionPerformed(ActionEvent e) {

        AbstractSwingWorker worker = new AbstractSwingWorker(true) {
            @Override
            public Object construct() {
                
                updateMessage("Computing Dependencies...");
                updateProgress(0);

                Application instance = Application.getInstance();
                JavaItem javaItem = instance.getWorkspaceTreeController().getSelection().get(0);                

                if (javaItem instanceof Location || javaItem instanceof Package
                        || javaItem instanceof Clazz) {
                    
                    Set locRefList = getReferences(javaItem);                    
                    
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
            		JavaItem javaItem = instance.getWorkspaceTreeController().getSelection().get(0);
            		String label = javaItem.toString();
            		            		
            		Application.getInstance().getGraphAreaController().showGraph(label, (Graph<JavaItem, JavaDependency>) getValue());    	
            	}       
            }

        };

        worker.start();
    }
    
    public abstract Set<? extends JavaItem> getReferences(JavaItem dbObject);
    
    public static class ShowLinksWithJarsAction extends AbstractShowDependenciesAction {
        private static final long serialVersionUID = 1L;
        public ShowLinksWithJarsAction() {
            putValue(Action.SMALL_ICON, Images.getIcon("ShowLinksWithJarsAction.icon"));
            putValue(Action.NAME, Messages.getString("ShowLinksWithJarsAction.label"));
        }
        @Override
        public Set<Location> getReferences(JavaItem dbObject) {
            return  ReferenceTools.getAllReferencesGroupByLocation(dbObject);
        }
    }
    
    public static class ShowLinksWithPackagesAction extends AbstractShowDependenciesAction {
        private static final long serialVersionUID = 1L;
        public ShowLinksWithPackagesAction() {
            putValue(Action.SMALL_ICON, Images.getIcon("ShowLinksWithPackagesAction.icon"));
            putValue(Action.NAME, Messages.getString("ShowLinksWithPackagesAction.label"));
        }
        @Override
        public Set<Package> getReferences(JavaItem dbObject) {
            return  ReferenceTools.getAllReferencesGroupByPackage(dbObject);
        }
    }
    
    public static class ShowLinksWithClassesAction extends AbstractShowDependenciesAction {
        private static final long serialVersionUID = 1L;
        public ShowLinksWithClassesAction() {
            putValue(Action.SMALL_ICON, Images.getIcon("ShowLinksWithClassesAction.icon"));
            putValue(Action.NAME, Messages.getString("ShowLinksWithClassesAction.label"));
        }
        @Override
        public Set<Clazz> getReferences(JavaItem dbObject) {
            return  ReferenceTools.getAllReferencesGroupByClass(dbObject);
        }
    }


}
