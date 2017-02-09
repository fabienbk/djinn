/*
 * Created on Feb 8, 2006
 * Created by Fabien Benoit
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
package com.fbksoft.djinn.ui.panels;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.fbksoft.djinn.ui.menu.WorkspaceTreeContextMenu;


public class WorkspaceTreePopupMouseListener extends MouseAdapter {

    private WorkspaceTreeContextMenu menuToDisplay;
    
    public WorkspaceTreePopupMouseListener(WorkspaceTreeContextMenu menuToDisplay) {
        this.menuToDisplay = menuToDisplay;
    }
    
    @Override
	public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {        	
        	menuToDisplay.refresh();
            menuToDisplay.show(e.getComponent(), e.getX(), e.getY());          
        }
    }
    @Override
	public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {            
            menuToDisplay.show(e.getComponent(), e.getX(), e.getY());
        }
    }
    
    
}
