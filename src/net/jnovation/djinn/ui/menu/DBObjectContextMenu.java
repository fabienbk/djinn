/*
 * Created on May 5, 2006
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
package net.jnovation.djinn.ui.menu;

import javax.swing.ActionMap;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import net.jnovation.djinn.control.actions.ShowProjectDependencyGraph;
import net.jnovation.djinn.control.actions.ShowDependenciesAction.ShowLinksWithClassesAction;
import net.jnovation.djinn.control.actions.ShowDependenciesAction.ShowLinksWithJarsAction;
import net.jnovation.djinn.control.actions.ShowDependenciesAction.ShowLinksWithPackagesAction;
import net.jnovation.djinn.i18n.Images;
import net.jnovation.djinn.i18n.Messages;

public class DBObjectContextMenu extends JPopupMenu {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    public DBObjectContextMenu(ActionMap actionMap) {        
        this.add(actionMap.get(ShowProjectDependencyGraph.class));
        
        JMenu showLinksMenu = new JMenu(Messages.getString("ShowLinks.label"));                
        showLinksMenu.setIcon(Images.getIcon("ShowDependenciesAction.icon"));
        showLinksMenu.add(actionMap.get(ShowLinksWithJarsAction.class));
        showLinksMenu.add(actionMap.get(ShowLinksWithPackagesAction.class));
        showLinksMenu.add(actionMap.get(ShowLinksWithClassesAction.class));
        
        this.add(showLinksMenu);
    }
}
