/*
 * Created on Oct 10, 2005
 * By Fabien Benoit - http://www.fbksoft.com
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
package com.fbksoft.djinn.ui.dialogs;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.TitledBorder;

public class ProgressDialog extends JDialog {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private JLabel dialogLabel;
    private JPanel progressBarPanel;
    private JProgressBar progressBar;

    public ProgressDialog(Frame owner) {
        super(owner, "Progress Indicator", false);
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        initGUI();        
    }
    
    private void initGUI() {
        try {
            GridBagLayout thisLayout = new GridBagLayout();
            thisLayout.columnWeights = new double[] {0.1};
            thisLayout.columnWidths = new int[] {7};
            thisLayout.rowWeights = new double[] {0.5,1.0};
            thisLayout.rowHeights = new int[] {7,7};
            this.getContentPane().setLayout(thisLayout);
            this.getContentPane().add(getDialogLabel(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
            this.getContentPane().add(getProgressBarPanel(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

            this.setSize(377, 134);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JLabel getDialogLabel() {
        if (dialogLabel == null) {
            dialogLabel = new JLabel();
            dialogLabel.setText("Default Text");
        }
        return dialogLabel;
    }
    
    private JPanel getProgressBarPanel() {
        if (progressBarPanel == null) {
            progressBarPanel = new JPanel();
            progressBarPanel.setLayout(null);
            progressBarPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(""), "Progress", TitledBorder.LEADING, TitledBorder.TOP));
            progressBarPanel.add(getProgressBar());
        }
        return progressBarPanel;
    }
    
    public JProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = new JProgressBar();
            GridBagLayout progressBarLayout = new GridBagLayout();
            progressBarLayout.columnWeights = new double[] {0.1};
            progressBarLayout.columnWidths = new int[] {7};
            progressBarLayout.rowWeights = new double[] {0.1};
            progressBarLayout.rowHeights = new int[] {7};
            progressBar.setLayout(progressBarLayout);
            progressBar.setPreferredSize(new java.awt.Dimension(352, 16));
            progressBar.setBounds(9, 26, 352, 14);
        }
        return progressBar;
    }
    
}
