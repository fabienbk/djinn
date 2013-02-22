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
package net.jnovation.djinn.control.wizards;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JPanel;

import net.jnovation.djinn.control.Application;
import net.jnovation.djinn.ui.wizards.WizardDialog;
import net.jnovation.djinn.ui.wizards.WizardHeaderPanel;

public abstract class Wizard {
    
    private WizardDialog dialog;
    
    public Wizard(Application application, String title, String subTitle, Icon icon) {
        
        WizardHeaderPanel wizardHeaderPanel = new WizardHeaderPanel(title, subTitle, icon);
        
        JPanel panel = getMainPanel();
        this.dialog = new WizardDialog(application.getApplicationFrame(), title, true, wizardHeaderPanel, panel);
        
        dialog.getOkButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCompletion();
            }            
        });
        
        dialog.getCancelButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Wizard.this.dialog.setVisible(false);
            }            
        });
        
    }        
        
    public void show() {
        int x, y;
        Point topLeft = Application.getInstance().getApplicationFrame().getLocationOnScreen();
        Dimension parentSize = Application.getInstance().getApplicationFrame().getSize();
        Dimension mySize = dialog.getSize();
        if (parentSize.width > mySize.width) { 
            x = ((parentSize.width - mySize.width)/2) + topLeft.x;
        }
        else { 
            x = topLeft.x;
        }
        if (parentSize.height > mySize.height) { 
            y = ((parentSize.height - mySize.height)/2) + topLeft.y;
        }
        else { 
            y = topLeft.y;
        }         
        dialog.setLocation (x, y);        
        dialog.setVisible(true);
    }
    
    public void hide() {
        dialog.setVisible(false);
    }
    
    public abstract JPanel getMainPanel();

    public abstract void onCompletion();
    
}
