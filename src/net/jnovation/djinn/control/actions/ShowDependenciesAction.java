/*
 * Created on Feb 8, 2006
 * By Fabien Benoit - http://www.jnovation.net
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

import edu.uci.ics.jung.graph.Graph;

import net.jnovation.djinn.control.Application;
import net.jnovation.djinn.db.data.Class;
import net.jnovation.djinn.db.data.DBObject;
import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.data.Package;
import net.jnovation.djinn.db.logic.ReferenceTools;
import net.jnovation.djinn.graph.GraphTools;
import net.jnovation.djinn.i18n.Images;
import net.jnovation.djinn.i18n.Messages;
import net.jnovation.djinn.model.workspace.DBTreeNode;
import net.jnovation.djinn.util.SwingWorker;

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
        public List<DBObject> getReferences(DBObject dbObject) {
            return  ReferenceTools.getAllLocationsReferences(dbObject);
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
        public List<DBObject> getReferences(DBObject dbObject) {
            return  ReferenceTools.getAllPackagesReferences(dbObject);
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
        public List<DBObject> getReferences(DBObject dbObject) {
            return  ReferenceTools.getAllClassReferences(dbObject);
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
                DBTreeNode selectedNode = instance.getWorkspaceTreeController()
                        .getSelectedNode();

                DBObject dbObject = selectedNode.getDataObject();

                if (dbObject instanceof Location || dbObject instanceof Package
                        || dbObject instanceof Class) {
                    
                    Set<DBObject> locRefList = new HashSet<DBObject>(getReferences(dbObject));
                    
                    updateMessage("Creating Graph...");
                    updateProgress(70);

                    // Build graph data
                    Map<DBObject, Set<DBObject>> graphData = new HashMap<DBObject, Set<DBObject>>();

                    // Put central object in the vertices list, with it's references
                    graphData.put(dbObject, locRefList);

                    // Put referenced objects in the vertices list
                    for (Iterator<DBObject> iter = locRefList.iterator(); iter
                            .hasNext();) {
                        DBObject ref = iter.next();
                        if (!ref.equals(dbObject)) {
                            graphData.put(ref, new HashSet<DBObject>());
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
            public void finished() {
            	Graph builtGraph = (Graph) getValue();
            	if (builtGraph!=null) {           

            		Application instance = Application.getInstance();
            		DBTreeNode selectedNode = instance.getWorkspaceTreeController().getSelectedNode();
            		String label = selectedNode.getDataObject().getLabel();
            		            		
            		Application.getInstance().getGraphAreaController().showGraph(label, (Graph) getValue());    	
            	}       
            }

        };

        worker.start();
    }
    
    public abstract List<DBObject> getReferences(DBObject dbObject);

}
