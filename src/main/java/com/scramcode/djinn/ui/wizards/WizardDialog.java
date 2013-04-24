/*
 * Created on Nov 25, 2005
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
package com.scramcode.djinn.ui.wizards;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import com.scramcode.djinn.ui.i18n.Messages;


public class WizardDialog extends JDialog {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private JButton cancelButton = new JButton(Messages.getString("WizardDialog.Cancel")); //$NON-NLS-1$
    private JButton okButton = new JButton(Messages.getString("WizardDialog.OK")); //$NON-NLS-1$

    public WizardDialog(Frame owner, String title, boolean modal, WizardHeaderPanel headerPanel, JPanel contentPanel) {
        super(owner, title, modal);
                        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        
        contentPanel.setBorder(BorderFactory.createEtchedBorder());
        
        getContentPane().add(headerPanel, BorderLayout.NORTH); 
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
        pack();        
    }    

    public JButton getCancelButton() {
        return this.cancelButton;
    }

    public JButton getOkButton() {
        return this.okButton;
    }

}
