package com.scramcode.djinn.ui.frame;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class GraphAreaPanel extends JPanel {
    
    private static final long serialVersionUID = 9029228682475406520L;
    
    private JTabbedPane tabbedPane;
    
    public GraphAreaPanel() {
        this.setLayout(new BorderLayout());     
        tabbedPane = new JTabbedPane();
        this.add(tabbedPane, BorderLayout.CENTER);
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
    
}
