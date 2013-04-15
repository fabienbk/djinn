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

import javax.swing.AbstractAction;

import net.jnovation.djinn.control.Application;
import net.jnovation.djinn.db.logic.ReferenceTools;
import net.jnovation.djinn.i18n.Images;
import net.jnovation.djinn.model.workspace.JavaItemTreeNode;

public class DumpReferencesAction extends AbstractAction {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    public DumpReferencesAction(boolean enabled) {
        super("Debug", Images.getIcon("DebugAction.icon") );
        setEnabled(enabled);
    }

    public void actionPerformed(ActionEvent e) {
        
        Runnable runnable = new Runnable() {
            public void run() {
                Application instance = Application.getInstance();
                JavaItemTreeNode selectedNode =
                    instance.getWorkspaceTreeController().getSelectedNode();        
                
                System.out.println("Refs : ");
                System.out.println(
                        ReferenceTools.getAllLocationsReferences(selectedNode.getJavaItem())
                );
            }
        };
        
        Thread t = new Thread(runnable);
        t.start();
                
    }

}
