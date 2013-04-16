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
package net.jnovation.djinn.ui.frame;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.JTree;

import net.jnovation.djinn.i18n.Images;
import net.jnovation.djinn.i18n.Messages;

public class WorkspacePanel extends javax.swing.JPanel {
    
    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;
    
    private JScrollPane workspaceScrollPane;
    
    private JTree packageIndexTree;
    private JTabbedPane tabbedPane;
    private JToolBar workspaceTreeToolBar;
    private JTree workspaceTree;

    /**
    * Auto-generated main method to display this 
    * JPanel inside a new JFrame.
    */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new WorkspacePanel());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
    
    public WorkspacePanel() {
        super();
        initGUI();
    }
    
    private void initGUI() {
        try {
            BorderLayout thisLayout = new BorderLayout();
            this.setLayout(thisLayout);
            this.setPreferredSize(new java.awt.Dimension(248, 334));                                     
            this.add(getTabbedPane(), BorderLayout.CENTER);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public JScrollPane getWorkspaceScrollPane() {
        if (workspaceScrollPane == null) {
            workspaceScrollPane = new JScrollPane();            
            workspaceScrollPane.setViewportView(getWorkspaceTree());
            workspaceScrollPane.setBorder(BorderFactory.createEmptyBorder());
        }
        return workspaceScrollPane;
    }
    
    public JTree getWorkspaceTree() {
        if (workspaceTree == null) {
            workspaceTree = new JTree();
            workspaceTree.setAutoscrolls(true);            
        }
        return workspaceTree;
    }
    
    public JToolBar getWorkspaceTreeToolBar() {
        if (workspaceTreeToolBar == null) {
            workspaceTreeToolBar = new JToolBar();            
        }
        return workspaceTreeToolBar;
    }
    
    public JTabbedPane getTabbedPane() {
        if (tabbedPane == null) {
            
            tabbedPane = new JTabbedPane();
            tabbedPane.setTabPlacement(SwingConstants.TOP);
            tabbedPane.setPreferredSize(new java.awt.Dimension(260, 332));
            tabbedPane.setBorder(null);
            
            JPanel panel = new JPanel();
            workspaceTreeToolBar = new JToolBar();                                                                        
                        
            panel.setLayout(new BorderLayout());
            panel.add(workspaceTreeToolBar, BorderLayout.NORTH);            
            panel.add(getWorkspaceScrollPane(), BorderLayout.CENTER);
                        
            workspaceTreeToolBar.setBorder(BorderFactory.createEtchedBorder());
            
            tabbedPane.addTab(Messages.getString("WorkspacePanel.workspaceTabLabel"), 
                    Images.getIcon("WorkspacePanel.workspaceTabIcon"), 
                    panel, null);            
        }
        return tabbedPane;
    }
    
    public JTree getPackageIndexTree() {
        if (packageIndexTree == null) {
            packageIndexTree = new JTree();
            packageIndexTree.setAutoscrolls(true);
        }
        return packageIndexTree;
    }
    
}
