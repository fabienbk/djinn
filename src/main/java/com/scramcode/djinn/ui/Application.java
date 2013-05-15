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
package com.scramcode.djinn.ui;

import javax.swing.ActionMap;

import com.scramcode.djinn.ui.actions.AboutDialogAction;
import com.scramcode.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithClassesAction;
import com.scramcode.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithJarsAction;
import com.scramcode.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithPackagesAction;
import com.scramcode.djinn.ui.actions.ImportDirectoryAction;
import com.scramcode.djinn.ui.actions.ImportEclipseProjectAction;
import com.scramcode.djinn.ui.actions.NewWorkspaceAction;
import com.scramcode.djinn.ui.actions.QuitAction;
import com.scramcode.djinn.ui.actions.ShowProjectDependencyGraph;
import com.scramcode.djinn.ui.actions.ShowSelectedItemsDependencyGraph;
import com.scramcode.djinn.ui.actions.layout.FRLayoutAction;
import com.scramcode.djinn.ui.actions.layout.KKLayoutAction;
import com.scramcode.djinn.ui.actions.layout.SpringLayoutAction;
import com.scramcode.djinn.ui.dialogs.WorkspaceEditorDialogController;
import com.scramcode.djinn.ui.menu.ApplicationMenu;
import com.scramcode.djinn.ui.panels.DependencyDetailsPanelController;
import com.scramcode.djinn.ui.panels.TabbedGraphAreaController;
import com.scramcode.djinn.ui.panels.WorkspaceTreeController;

public final class Application {

	private ActionMap rootActionMap = new ActionMap();

	// Main frame
	private DjinnFrame applicationFrame;

	// Controllers
	private WorkspaceTreeController workspaceTreeController;
	private TabbedGraphAreaController graphAreaController;
	private WorkspaceEditorDialogController workspaceEditorDialogController;
	
	private DependencyDetailsPanelController dependencyDetailsPanelController;
	
	private static Application instance = new Application();

	private Application() {

		applicationFrame = new DjinnFrame();

		// Creating actions
		
		rootActionMap.put(NewWorkspaceAction.class, new NewWorkspaceAction(true));		
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
		menuBar.getWorkspaceMenu().add(rootActionMap.get(QuitAction.class));
		menuBar.getLayoutMenu().add(rootActionMap.get(FRLayoutAction.class));
		menuBar.getLayoutMenu().add(rootActionMap.get(KKLayoutAction.class));
		menuBar.getLayoutMenu().add(rootActionMap.get(SpringLayoutAction.class));
		menuBar.getHelpMenu().add(rootActionMap.get(AboutDialogAction.class));

		// Controllers
		workspaceTreeController = new WorkspaceTreeController(this);
		graphAreaController = new TabbedGraphAreaController(this);
		workspaceEditorDialogController = new WorkspaceEditorDialogController(this);
		
		dependencyDetailsPanelController = new DependencyDetailsPanelController(this);
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

	public DependencyDetailsPanelController getDependencyDetailsPanelController() {
		return dependencyDetailsPanelController;
	}

}
