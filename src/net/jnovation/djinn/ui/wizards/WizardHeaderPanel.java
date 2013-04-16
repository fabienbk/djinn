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
package net.jnovation.djinn.ui.wizards;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Icon;
import javax.swing.JLabel;


public class WizardHeaderPanel extends javax.swing.JPanel {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private JLabel wizardTitleLabel;   
    private JLabel wizardSubTitleLabel;
    private JLabel iconLabel;
    
    public WizardHeaderPanel() {
        this("Wizard Title", "No description", null);
    }
    
    public WizardHeaderPanel(String title, String description, Icon icon) {
        
        wizardTitleLabel = new JLabel();
        wizardTitleLabel.setText(title);
        wizardTitleLabel.setFont(new java.awt.Font("Verdana",1,18));
        
        wizardSubTitleLabel = new JLabel();
        wizardSubTitleLabel.setText(description);
        
        iconLabel = new JLabel();
        
        if (icon != null) {
            iconLabel.setIcon(icon);
        }
        
        GridBagLayout thisLayout = new GridBagLayout();
        thisLayout.columnWeights = new double[] {0.1,0.1};
        thisLayout.columnWidths = new int[] {7,7};
        thisLayout.rowWeights = new double[] {0.1,0.1};
        thisLayout.rowHeights = new int[] {7,7};
        this.setLayout(thisLayout);
        this.setPreferredSize(new java.awt.Dimension(430, 80));
        this.setBackground(new java.awt.Color(255,255,255));
        this.add(wizardTitleLabel, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        this.add(wizardSubTitleLabel, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(0, 5, 0, 0), 0, 0));
        this.add(iconLabel, new GridBagConstraints(1, 0, 1, 2, 0.0, 0.0, GridBagConstraints.SOUTHEAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));        
        
    }
    
    public JLabel getWizardTitleLabel() {        
        return wizardTitleLabel;
    }
    
    public JLabel getWizardSubTitleLabel() {        
        return wizardSubTitleLabel;
    }
    
    public JLabel getIconLabel() {
        return iconLabel;
    }

}
