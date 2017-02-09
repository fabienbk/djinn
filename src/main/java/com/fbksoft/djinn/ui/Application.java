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
package com.fbksoft.djinn.ui;

import javax.swing.ActionMap;

import com.fbksoft.djinn.ui.actions.AboutDialogAction;
import com.fbksoft.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithClassesAction;
import com.fbksoft.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithJarsAction;
import com.fbksoft.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithPackagesAction;
import com.fbksoft.djinn.ui.actions.ImportDirectoryAction;
import com.fbksoft.djinn.ui.actions.ImportEclipseProjectAction;
import com.fbksoft.djinn.ui.actions.EditWorkspaceAction;
import com.fbksoft.djinn.ui.actions.NewWorkspaceAction;
import com.fbksoft.djinn.ui.actions.OpenWorkspaceAction;
import com.fbksoft.djinn.ui.actions.SaveWorkspaceAction;
import com.fbksoft.djinn.ui.actions.QuitAction;
import com.fbksoft.djinn.ui.actions.ShowProjectDependencyGraph;
import com.fbksoft.djinn.ui.actions.ShowSelectedItemsDependencyGraph;
import com.fbksoft.djinn.ui.actions.layout.FRLayoutAction;
import com.fbksoft.djinn.ui.actions.layout.KKLayoutAction;
import com.fbksoft.djinn.ui.actions.layout.SpringLayoutAction;
import com.fbksoft.djinn.ui.dialogs.WorkspaceEditorDialogController;
import com.fbksoft.djinn.ui.menu.ApplicationMenu;
import com.fbksoft.djinn.ui.panels.DependencyListPanelController;
import com.fbksoft.djinn.ui.panels.TabbedGraphAreaController;
import com.fbksoft.djinn.ui.panels.WorkspaceTreeController;

public final class Application {

	private ActionMap rootActionMap = new ActionMap();

	// Main frame
	private DjinnFrame applicationFrame;

	// Controllers
	private WorkspaceTreeController workspaceTreeController;
	private TabbedGraphAreaController graphAreaController;
	private WorkspaceEditorDialogController workspaceEditorDialogController;
	
	private DependencyListPanelController dependencyDetailsPanelController;
	
	private static Application instance = new Application();

	private Application() {

		applicationFrame = new DjinnFrame();

		// Creating actions
		
		rootActionMap.put(NewWorkspaceAction.class, new NewWorkspaceAction(true));		
		rootActionMap.put(OpenWorkspaceAction.class, new OpenWorkspaceAction(true));		
		rootActionMap.put(EditWorkspaceAction.class, new EditWorkspaceAction(true));		
		rootActionMap.put(SaveWorkspaceAction.class, new SaveWorkspaceAction(true));		
		rootActionMap.put(QuitAction.class, new QuitAction());
		rootActionMap.put(AboutDialogAction.class, new AboutDialogAction());

		rootActionMap.put(ShowSelectedItemsDependencyGraph.class, new ShowSelectedItemsDependencyGraph());
		rootActionMap.put(ShowProjectDependencyGraph.class, new ShowProjectDependencyGraph(true));
		
		rootActionMap.put(ShowLinksWithJarsAction.class, new ShowLinksWithJarsAction());
		rootActionMap.put(ShowLinksWithPackagesAction.class, new ShowLinksWithPackagesAction());
		rootActionMap.put(ShowLinksWithClassesAction.class, new ShowLinksWithClassesAction());

		rootActionMap.put(ImportEclipseProjectAction.class, new ImportEclipseProjectAction(true));
		rootActionMap.put(ImportDirectoryAction.class, new ImportDirectoryAction(true));

		rootActionMap.put(FRLayoutAction.class, new FRLayoutAction());
		rootActionMap.put(KKLayoutAction.class, new KKLayoutAction());
		rootActionMap.put(SpringLayoutAction.class, new SpringLayoutAction());
		
		
		// Creating menu bar actions
		ApplicationMenu menuBar = applicationFrame.getApplicationMenu();
		menuBar.getWorkspaceMenu().add(rootActionMap.get(NewWorkspaceAction.class));
		menuBar.getWorkspaceMenu().add(rootActionMap.get(OpenWorkspaceAction.class));
		menuBar.getWorkspaceMenu().add(rootActionMap.get(EditWorkspaceAction.class));
		menuBar.getWorkspaceMenu().add(rootActionMap.get(SaveWorkspaceAction.class));
		menuBar.getWorkspaceMenu().add(rootActionMap.get(QuitAction.class));
		menuBar.getLayoutMenu().add(rootActionMap.get(FRLayoutAction.class));
		menuBar.getLayoutMenu().add(rootActionMap.get(KKLayoutAction.class));
		menuBar.getLayoutMenu().add(rootActionMap.get(SpringLayoutAction.class));
		menuBar.getHelpMenu().add(rootActionMap.get(AboutDialogAction.class));

		// Controllers
		workspaceTreeController = new WorkspaceTreeController(this);
		graphAreaController = new TabbedGraphAreaController(this);
		workspaceEditorDialogController = new WorkspaceEditorDialogController(this);
		
		dependencyDetailsPanelController = new DependencyListPanelController(this);
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

	public ActionMap getRootActionMap() {
		return this.rootActionMap;
	}

	public WorkspaceTreeController getWorkspaceTreeController() {
		return this.workspaceTreeController;
	}

	public TabbedGraphAreaController getGraphAreaController() {
		return this.graphAreaController;
	}

	public WorkspaceEditorDialogController getWorkspaceEditorDialogController() {
		return workspaceEditorDialogController;
	}

	public DependencyListPanelController getDependencyDetailsPanelController() {
		return dependencyDetailsPanelController;
	}

}
