/*
 * Created on Mar 1, 2006
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
package net.jnovation.djinn.ui.panels;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;

import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
* This code was generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* *************************************
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED
* for this machine, so Jigloo or this code cannot be used legally
* for any corporate or commercial purpose.
* *************************************
*/
public class SQLQueryPanel extends javax.swing.JPanel {
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 4048581686184990048L;
    private JScrollPane queryScrollPane;
    private JTextArea queryTextArea;
    private JLabel queryLabel;
    private JButton copyButton;
    private JTable resultTable;
    private JScrollPane resultScrollPane;
    private JButton executeButton;
    private JPanel buttonPanel;

    /**
    * Auto-generated main method to display this 
    * JPanel inside a new JFrame.
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new SQLQueryPanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
        
    public SQLQueryPanel() {
        super();
        initGUI();
    }
    
    private void initGUI() {
        try {
            GridBagLayout thisLayout = new GridBagLayout();
            thisLayout.columnWeights = new double[] {0.1};
            thisLayout.columnWidths = new int[] {7};
            thisLayout.rowWeights = new double[] {0.0,1.0,0.0,1.0};
            thisLayout.rowHeights = new int[] {7,7,7,7};
            this.setLayout(thisLayout);
            this.setPreferredSize(new java.awt.Dimension(451, 317));
            this.add(getQueryScrollPane(), new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            this.add(getButtonPanel(), new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.EAST, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            this.add(getResultScrollPane(), new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
            this.add(getQueryLabel(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private JScrollPane getQueryScrollPane() {
        if (queryScrollPane == null) {
            queryScrollPane = new JScrollPane();
            queryScrollPane.setViewportView(getQueryTextArea());
        }
        return queryScrollPane;
    }
    
    public JTextArea getQueryTextArea() {
        if (queryTextArea == null) {
            queryTextArea = new JTextArea();
        }
        return queryTextArea;
    }
    
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            FlowLayout buttonPanelLayout = new FlowLayout();
            buttonPanelLayout.setAlignment(FlowLayout.RIGHT);
            buttonPanel.setLayout(buttonPanelLayout);
            buttonPanel.add(getExecuteButton());
            buttonPanel.add(getCopyButton());
        }
        return buttonPanel;
    }
    
    public JButton getExecuteButton() {
        if (executeButton == null) {
            executeButton = new JButton();
            executeButton.setText("Execute");
        }
        return executeButton;
    }
    
    private JScrollPane getResultScrollPane() {
        if (resultScrollPane == null) {
            resultScrollPane = new JScrollPane();
            resultScrollPane.setViewportView(getResultTable());
        }
        return resultScrollPane;
    }
    
    public JTable getResultTable() {
        if (resultTable == null) {
            TableModel resultTableModel = new DefaultTableModel(new String[][] {
                    { "One", "Two" }, { "Three", "Four" } }, new String[] {
                    "Column 1", "Column 2" });
            resultTable = new JTable();
            resultTable.setModel(resultTableModel);
        }
        return resultTable;
    }
    
    public JButton getCopyButton() {
        if (copyButton == null) {
            copyButton = new JButton();
            copyButton.setText("Copy");
        }
        return copyButton;
    }
    
    private JLabel getQueryLabel() {
        if (queryLabel == null) {
            queryLabel = new JLabel();
            queryLabel.setText("Enter your SQL Query :");
        }
        return queryLabel;
    }

}
