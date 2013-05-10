/*
 * Created on May 5, 2006
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
package com.scramcode.djinn.ui.menu;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import com.scramcode.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithClassesAction;
import com.scramcode.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithJarsAction;
import com.scramcode.djinn.ui.actions.AbstractShowDependenciesAction.ShowLinksWithPackagesAction;
import com.scramcode.djinn.ui.actions.ShowProjectDependencyGraph;
import com.scramcode.djinn.ui.actions.ShowSelectedItemsDependencyGraph;
import com.scramcode.djinn.ui.actions.ShowTopLevelDependencyGraph;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;


public class WorkspaceTreeContextMenu extends JPopupMenu {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private ActionMap actionMap = null;

    public WorkspaceTreeContextMenu(ActionMap actionMap) {      
    	this.actionMap = actionMap;
    	this.add(actionMap.get(ShowTopLevelDependencyGraph.class));
    	this.add(actionMap.get(ShowSelectedItemsDependencyGraph.class));
    	this.add(actionMap.get(ShowProjectDependencyGraph.class));
        
        JMenu showLinksMenu = new JMenu(Messages.getString("ShowLinks.label"));                
        showLinksMenu.setIcon(Images.getIcon("ShowDependenciesAction.icon"));
        showLinksMenu.add(actionMap.get(ShowLinksWithJarsAction.class));
        showLinksMenu.add(actionMap.get(ShowLinksWithPackagesAction.class));
        showLinksMenu.add(actionMap.get(ShowLinksWithClassesAction.class));
        
        this.add(showLinksMenu);
    }

	public void refresh() {
		Object[] allKeys = actionMap.keys();		
		if (allKeys != null) {
			for (Object key : allKeys) {
				final Action action = actionMap.get(key);
				action.setEnabled(action.isEnabled());
			}
		}
	}
}
