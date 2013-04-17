/*
 * Created on Nov 27, 2005
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

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import com.scramcode.djinn.i18n.Messages;


public class ApplicationMenu extends JMenuBar {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private JMenu workspaceMenu;
    private JMenu toolsMenu;
    private JMenu newMenu;
    private JMenu helpMenu;    
    
    public ApplicationMenu() {
        workspaceMenu = new JMenu(Messages.getString("ApplicationMenu.workspaceMenu"));
        newMenu = new JMenu(Messages.getString("ApplicationMenu.workspaceMenu.newMenu"));
        toolsMenu = new JMenu(Messages.getString("ApplicationMenu.toolsMenu"));
        helpMenu = new JMenu(Messages.getString("ApplicationMenu.helpMenu"));
        this.add(workspaceMenu);
        workspaceMenu.add(newMenu);
        
        this.add(toolsMenu);
        
        this.add(helpMenu);
    }

    public JMenu getWorkspaceMenu() {
        return this.workspaceMenu;
    }

    public JMenu getNewMenu() {
        return this.newMenu;
    }

    public JMenu getToolsMenu() {
        return this.toolsMenu;
    }
        

    public JMenu getHelpMenu() {
        return this.helpMenu;
    }
}
