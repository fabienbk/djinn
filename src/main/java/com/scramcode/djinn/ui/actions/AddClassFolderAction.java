/*
 * Created on Feb 8, 2006
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
package com.scramcode.djinn.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.sql.Connection;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import com.scramcode.djinn.bytecode.DefLocationVisitor;
import com.scramcode.djinn.bytecode.DirectoryReader;
import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Location;
import com.scramcode.djinn.db.data.Project;
import com.scramcode.djinn.model.workspace.AbstractJavaItemTreeNode;
import com.scramcode.djinn.model.workspace.ProjectNode;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Images;
import com.scramcode.djinn.ui.i18n.Messages;
import com.scramcode.djinn.ui.panels.WorkspaceTreeController;
import com.scramcode.djinn.util.FileChooserFactory;


public class AddClassFolderAction extends AbstractAction {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    public AddClassFolderAction(boolean enabled) {
        super(Messages.getString("AddClassFolderAction.label"), Images.getIcon("AddClassFolderAction.icon") );        
        setEnabled(enabled);
    }
    
    public void actionPerformed(ActionEvent e) {
        Application app = Application.getInstance();
        WorkspaceTreeController controller = app.getWorkspaceTreeController(); 
        
        // Get the current selected node in the tree
        AbstractJavaItemTreeNode selectedNode = controller.getSelectedNode();        
        // if not a project, return
        if (!(selectedNode instanceof ProjectNode)) {
            return;
        }
        
        JFileChooser fc = FileChooserFactory.getInstance().getDirectoryChooser();        
        int result = fc.showOpenDialog(Application.getInstance().getApplicationFrame());        
        
        if (result == JFileChooser.APPROVE_OPTION) {
            
            try {                       
                
                // the jar path
                String path = fc.getSelectedFile().getAbsolutePath();
                
                // the jar visitor
                DirectoryReader r = new DirectoryReader(new File(path));
                
                // the selected project key
                ProjectNode selectedProjectNode = (ProjectNode)selectedNode;
				Project project = (Project)selectedProjectNode.getJavaItem();
                
                
                Location location = new Location(path, Location.DIR_LOCATION_TYPE, project);
                DataHelper.putLocation(location);
                
                // visit the jar
                r.accept(new DefLocationVisitor(location));
                
                controller.refreshNode(selectedNode);
                
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }        
        }
        
    }

}