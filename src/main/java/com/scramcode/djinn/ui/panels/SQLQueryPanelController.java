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
package com.scramcode.djinn.ui.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;

import com.scramcode.djinn.model.SQLResultSetTableModel;
import com.scramcode.djinn.ui.i18n.Messages;


public class SQLQueryPanelController implements ActionListener {

    private JFrame frame;
    private SQLQueryPanel panel = new SQLQueryPanel();
    private SQLResultSetTableModel tableModel;
    
    public SQLQueryPanelController() {
            	
    	frame = new JFrame();    	
        frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);        
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        
        JTable resultTable = panel.getResultTable();
        tableModel = new SQLResultSetTableModel();
        resultTable.setModel(tableModel);
        
        panel.getExecuteButton().addActionListener(this);
        
    }

    public void setVisible(boolean visible) {
    	frame.pack();    	
        frame.setVisible(visible);
    }

    public void actionPerformed(ActionEvent e) {
        
        // "Execute" Button         
        
        String query = panel.getQueryTextArea().getText();
        try {
            tableModel.update(query);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(panel, ex.getMessage(), 
                    Messages.getString("error.sql"), JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
}
