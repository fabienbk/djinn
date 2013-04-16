/*
 * Created on Feb 27, 2006
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
package net.jnovation.djinn.model.workspace;

import java.util.Vector;

import javax.swing.Icon;

import net.jnovation.djinn.db.data.Field;
import net.jnovation.djinn.i18n.Images;

public class FieldNode extends JavaItemTreeNode {

    private static final Icon ICON = Images.getIcon("Field.icon");
    
    public FieldNode(ClassNode parent, Field dataObject) {
        super(parent, dataObject);
    }

    @Override
    protected Vector<JavaItemTreeNode> getChildren() {
        return new Vector<JavaItemTreeNode>();
    }
    
    @Override
    public boolean isLeaf() {
        return true;
    }

    public Icon getIcon() {
        return ICON;
    }

}
