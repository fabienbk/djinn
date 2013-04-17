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
package com.scramcode.djinn.ui.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ExceptionDialog extends javax.swing.JDialog {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -8381358996143789428L;
    
    private JPanel hideablePanel;
    private JPanel mainPanel;
    private JLabel iconLabel;
    private JLabel messageLabel;
    private JPanel messagePanel;
    private JButton detailsButton;
    private JButton okButton;
    private JPanel buttonPanel;
    private JTextArea exceptionTextArea;
    private JScrollPane jScrollPane1;

    /**
    * Auto-generated main method to display this JDialog
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        ExceptionDialog inst = new ExceptionDialog(frame);
        inst.setVisible(true);
    }
    
    public ExceptionDialog(JFrame frame) {
        super(frame);
        initGUI();
    }
    
    private void initGUI() {
        try {
            this.getContentPane().add(getHideablePanel(), BorderLayout.SOUTH);
            this.getContentPane().add(getMainPanel(), BorderLayout.CENTER);
            
            ExceptionDialog.this.hideablePanel.setVisible(false);            
            this.setSize(400, 254);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JPanel getHideablePanel() {
        BorderLayout thisLayout = new BorderLayout();
        this.getContentPane().setLayout(thisLayout);
        if (hideablePanel == null) {
            hideablePanel = new JPanel();
            BorderLayout hideablePanelLayout = new BorderLayout();
            hideablePanel.setLayout(hideablePanelLayout);
            hideablePanel.setPreferredSize(new java.awt.Dimension(392, 87));
            hideablePanel.add(getJScrollPane1(), BorderLayout.CENTER);            
        }
        return hideablePanel;
    }
    
    public JPanel getMainPanel() {
        if (mainPanel == null) {
            mainPanel = new JPanel();
            BorderLayout mainPanelLayout = new BorderLayout();
            mainPanel.setLayout(mainPanelLayout);
            mainPanel.setPreferredSize(new java.awt.Dimension(392, 180));
            mainPanel.add(getButtonPanel(), BorderLayout.SOUTH);
            mainPanel.add(getMessagePanel(), BorderLayout.CENTER);
        }
        return mainPanel;
    }

    private JScrollPane getJScrollPane1() {
        if (jScrollPane1 == null) {
            jScrollPane1 = new JScrollPane();
            jScrollPane1.setPreferredSize(new java.awt.Dimension(392, 107));
            jScrollPane1.setViewportView(getExceptionTextArea());
        }
        return jScrollPane1;
    }
    
    public JTextArea getExceptionTextArea() {
        if (exceptionTextArea == null) {
            exceptionTextArea = new JTextArea();
        }
        return exceptionTextArea;
    }
    
    public JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            FlowLayout buttonPanelLayout = new FlowLayout();
            buttonPanelLayout.setAlignment(FlowLayout.RIGHT);
            buttonPanel.setLayout(buttonPanelLayout);
            buttonPanel.add(getOkButton());
            buttonPanel.add(getDetailsButton());
        }
        return buttonPanel;
    }
    
    private JButton getOkButton() {
        if (okButton == null) {
            okButton = new JButton();
            okButton.setText("OK");
        }
        return okButton;
    }
    
    public JButton getDetailsButton() {
        if (detailsButton == null) {
            detailsButton = new JButton();
            detailsButton.setText("Details >>");
            detailsButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ExceptionDialog.this.hideablePanel.setVisible(true);
                    detailsButton.setEnabled(false);
                }                
            });
        }
        return detailsButton;
    }
    
    public JPanel getMessagePanel() {
        if (messagePanel == null) {
            messagePanel = new JPanel();
            GridBagLayout messagePanelLayout = new GridBagLayout();
            messagePanelLayout.columnWeights = new double[] {0.0,1.0};
            messagePanelLayout.columnWidths = new int[] {7,7};
            messagePanel.setLayout(messagePanelLayout);
            messagePanel.setPreferredSize(new java.awt.Dimension(392, 127));
            messagePanel.add(getMessageLabel(), new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
            messagePanel.add(getIconLabel(), new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
        }
        return messagePanel;
    }
    
    public JLabel getMessageLabel() {
        if (messageLabel == null) {
            messageLabel = new JLabel();
            messageLabel.setText("message");
        }
        return messageLabel;
    }
    
    private JLabel getIconLabel() {
        if (iconLabel == null) {
            iconLabel = new JLabel();
            iconLabel.setText("ERROR");
        }
        return iconLabel;
    }

}
