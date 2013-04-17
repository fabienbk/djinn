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
package com.scramcode.djinn.control.wizards;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import com.scramcode.djinn.control.Application;
import com.scramcode.djinn.control.WorkspaceTreeController;
import com.scramcode.djinn.db.data.DataHelper;
import com.scramcode.djinn.db.data.Project;
import com.scramcode.djinn.db.mgmt.ConnectionManager;
import com.scramcode.djinn.i18n.Images;
import com.scramcode.djinn.i18n.Messages;
import com.scramcode.djinn.ui.wizards.ProjectWizardPanel;
import com.scramcode.djinn.util.FileChooserFactory;


public class ProjectWizard extends Wizard {

    protected ProjectWizardPanel projectWizardPanel;      
    
    public ProjectWizard(Application application) {        
        super(application,
                Messages.getString("ProjectWizard.title"), 
                Messages.getString("ProjectWizard.subtitle"), 
                Images.getIcon("ProjectWizard.icon"));
    }       

    @Override
    public void onCompletion() {
        
        Project project = new Project(projectWizardPanel.getProjectNameTextField().getText());
        DataHelper.putProject(ConnectionManager.getInstance().getConnection(), project);        
        
        // refresh root        
        WorkspaceTreeController controller = Application.getInstance().getWorkspaceTreeController();        
        controller.refreshNode(controller.getRootNode());
        
        hide();               
        projectWizardPanel.getProjectNameTextField().setText("");
        projectWizardPanel.getBaseLocationBrowseTextField().setText("");                
    }

    @Override
    public JPanel getMainPanel() {
        if (projectWizardPanel == null) {
            projectWizardPanel = new ProjectWizardPanel();
            
            projectWizardPanel.getBaseLocationBrowseButton().addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {                    
                    JFileChooser fileChooser = FileChooserFactory.getInstance().getDirectoryChooser();
                    int choice = fileChooser.showOpenDialog(Application.getInstance().getApplicationFrame());
                    if (choice == JFileChooser.APPROVE_OPTION) {
                        projectWizardPanel.getBaseLocationBrowseTextField().setText(fileChooser.getSelectedFile().getPath());
                    }
                }
            });
        }
        return projectWizardPanel;
    }

}
