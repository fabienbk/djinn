/*
 * Created on Mar 1, 2006
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
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ProjectWizardPanel extends javax.swing.JPanel {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private JButton baseLocationBrowseButton;    
    private JLabel projectNameLabel;
    private JLabel baseLocationLabel;
    private JTextField baseLocationBrowseTextField;
    private JTextField projectNameTextField;

    /**
    * Auto-generated main method to display this 
    * JPanel inside a new JFrame.
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new ProjectWizardPanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public ProjectWizardPanel() {
        super();
        initGUI();
    }
    
    private void initGUI() {
        try {
            GridBagLayout thisLayout = new GridBagLayout();
            thisLayout.columnWeights = new double[] {0.0,0.1,0.0};
            thisLayout.columnWidths = new int[] {7,7,7};
            thisLayout.rowWeights = new double[] {0.1,0.1};
            thisLayout.rowHeights = new int[] {7,7};
            this.setLayout(thisLayout);
            this.setPreferredSize(new java.awt.Dimension(366, 70));
            this.add(getBaseLocationBrowseButton(), new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
            this.add(getBaseLocationBrowseTextField(), new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
            this.add(getBaseLocationLabel(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
            this.add(getProjectNameLabel(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
            this.add(getProjectNameTextField(), new GridBagConstraints(1, 0, 2, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 5), 0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JButton getBaseLocationBrowseButton() {
        if (baseLocationBrowseButton == null) {
            baseLocationBrowseButton = new JButton();
            baseLocationBrowseButton.setText("Browse...");
        }
        return baseLocationBrowseButton;
    }
    
    public JTextField getBaseLocationBrowseTextField() {
        if (baseLocationBrowseTextField == null) {
            baseLocationBrowseTextField = new JTextField();
        }
        return baseLocationBrowseTextField;
    }
    
    public JLabel getBaseLocationLabel() {
        if (baseLocationLabel == null) {
            baseLocationLabel = new JLabel();
            baseLocationLabel.setText("Project Location");
        }
        return baseLocationLabel;
    }
    
    public JLabel getProjectNameLabel() {
        if (projectNameLabel == null) {
            projectNameLabel = new JLabel();
            projectNameLabel.setText("Project Name");
        }
        return projectNameLabel;
    }
    
    public JTextField getProjectNameTextField() {
        if (projectNameTextField == null) {
            projectNameTextField = new JTextField();
        }
        return projectNameTextField;
    }

}
