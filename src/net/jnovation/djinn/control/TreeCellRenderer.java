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
package net.jnovation.djinn.control;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

import net.jnovation.djinn.model.DecoratedNode;

public class TreeCellRenderer extends DefaultTreeCellRenderer {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = 1L;

    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean isFocused) {        
        super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, isFocused);
                
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
