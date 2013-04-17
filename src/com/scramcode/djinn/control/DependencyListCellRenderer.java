/*
 * Created on Dec 5, 2006
 * Created by Fabien Benoit
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
package com.scramcode.djinn.control;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.scramcode.djinn.model.DecoratedNode;


public class DependencyListCellRenderer extends JLabel implements ListCellRenderer {	

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof DecoratedNode) {
            DecoratedNode node = (DecoratedNode)value;

            if (node.getIcon() != null) {
                setIcon(node.getIcon());
            }
            setText(node.getLabel());
        }
        else {
            setText(value.toString());
        }
        
        return this; 
    }

}
