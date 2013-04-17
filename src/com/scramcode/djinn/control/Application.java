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

import javax.swing.ActionMap;
import javax.swing.JToolBar;

import com.scramcode.djinn.control.actions.AboutDialogAction;
import com.scramcode.djinn.control.actions.AddClassFolderAction;
import com.scramcode.djinn.control.actions.AddJarAction;
import com.scramcode.djinn.control.actions.AddProjectAction;
import com.scramcode.djinn.control.actions.DumpReferencesAction;
import com.scramcode.djinn.control.actions.ImportDirectoryAction;
import com.scramcode.djinn.control.actions.ImportEclipseProjectAction;
import com.scramcode.djinn.control.actions.QuitAction;
import com.scramcode.djinn.control.actions.ShowDependenciesAction;
import com.scramcode.djinn.control.actions.ShowProjectDependencyGraph;
import com.scramcode.djinn.control.actions.ShowSQLQueryManager;
import com.scramcode.djinn.control.wizards.WizardCollection;
import com.scramcode.djinn.ui.frame.DjinnFrame;
import com.scramcode.djinn.ui.menu.ApplicationMenu;


public class Application {
    
    private ActionMap rootActionMap = new ActionMap();
    private WizardCollection wizardManager;
    
    // Main frame
    private DjinnFrame applicationFrame;
    
    // Controllers
    private WorkspaceTreeController workspaceTreeController;
    private GraphAreaController graphAreaController;
    private SQLPanelController sqlPanelController;
    
    private static Application instance = new Application();

    private Application() {
                        
        applicationFrame = new DjinnFrame();
                
        wizardManager = new WizardCollection(this);        
        
        // Creating actions
        rootActionMap.put(AddProjectAction.class, new AddProjectAction(true));
        rootActionMap.put(AddClassFolderAction.class, new AddClassFolderAction(true));
        rootActionMap.put(AddJarAction.class, new AddJarAction(true));
        rootActionMap.put(QuitAction.class, new QuitAction());                        
        rootActionMap.put(AboutDialogAction.class, new AboutDialogAction());
        
        rootActionMap.put(ShowProjectDependencyGraph.class, 
                new ShowProjectDependencyGraph(true));
        rootActionMap.put(ShowDependenciesAction.ShowLinksWithJarsAction.class, 
                new ShowDependenciesAction.ShowLinksWithJarsAction(true));
        rootActionMap.put(ShowDependenciesAction.ShowLinksWithPackagesAction.class, 
                new ShowDependenciesAction.ShowLinksWithPackagesAction(true));
        rootActionMap.put(ShowDependenciesAction.ShowLinksWithClassesAction.class, 
                new ShowDependenciesAction.ShowLinksWithClassesAction(true));
        
        rootActionMap.put(DumpReferencesAction.class, new DumpReferencesAction(true));
        rootActionMap.put(ShowSQLQueryManager.class, new ShowSQLQueryManager(true));
        rootActionMap.put(ImportEclipseProjectAction.class, new ImportEclipseProjectAction(true));
        rootActionMap.put(ImportDirectoryAction.class, new ImportDirectoryAction(true));
        
        
        // Creating menu bar actions
        ApplicationMenu menuBar = applicationFrame.getApplicationMenu();
        menuBar.getNewMenu().add(rootActionMap.get(AddProjectAction.class));
        menuBar.getNewMenu().add(rootActionMap.get(AddClassFolderAction.class));
        menuBar.getNewMenu().add(rootActionMap.get(AddJarAction.class));
        menuBar.getWorkspaceMenu().add(rootActionMap.get(QuitAction.class));
        menuBar.getToolsMenu().add(rootActionMap.get(ShowSQLQueryManager.class));
        menuBar.getHelpMenu().add(rootActionMap.get(AboutDialogAction.class));
        
        JToolBar workspaceTreeToolbar = applicationFrame.getWorkspacePanel().getWorkspaceTreeToolBar();
        workspaceTreeToolbar.add(rootActionMap.get(AddProjectAction.class));
        workspaceTreeToolbar.add(rootActionMap.get(AddClassFolderAction.class));
        workspaceTreeToolbar.add(rootActionMap.get(AddJarAction.class));
        workspaceTreeToolbar.add(rootActionMap.get(ImportEclipseProjectAction.class));
        workspaceTreeToolbar.add(rootActionMap.get(ImportDirectoryAction.class));
        
        
        
        
        // Controllers
        workspaceTreeController = new WorkspaceTreeController(this);
        graphAreaController = new GraphAreaController(this);
        sqlPanelController = new SQLPanelController();
    }       

    public DjinnFrame getApplicationFrame() {
        return applicationFrame;
    }

    public static Application getInstance() {
        return instance;
    }

    public void start() {
        applicationFrame.setVisible(true);
    }

    public WizardCollection getWizardController() {
        return this.wizardManager;
    }

    public ActionMap getRootActionMap() {
        return this.rootActionMap;
    }

    public WorkspaceTreeController getWorkspaceTreeController() {
        return this.workspaceTreeController;
    }

    public GraphAreaController getGraphAreaController() {
        return this.graphAreaController;
    }

    public SQLPanelController getSQLPanelController() {
        return this.sqlPanelController;
    }
    
}
