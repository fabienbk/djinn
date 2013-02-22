/*
 * Created on Feb 27, 2006
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
package net.jnovation.djinn.model.workspace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;

import net.jnovation.djinn.db.data.Class;
import net.jnovation.djinn.db.data.Field;
import net.jnovation.djinn.db.data.Method;
import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.db.mgmt.QueryHelper;
import net.jnovation.djinn.db.mgmt.RowConverter;
import net.jnovation.djinn.i18n.Images;

public class ClassNode extends DBTreeNode {

    private static final Icon ICON = Images.getIcon("Class.icon");
    private boolean showChildren = true;    
    
    public ClassNode(PackageNode parent, Class dataObject) {
        super(parent, dataObject);
    }
    
    public ClassNode(PackageNode parent, Class dataObject, boolean showChildren) {
        super(parent, dataObject);
        this.showChildren = showChildren;
    }
    
    /**
     * Override to prevent children from showing in case of showChildren == false.
     */
    @Override    
    public void refresh() {
        if (showChildren) {
            super.refresh();
        }
    }

    @Override
    protected Vector<DBTreeNode> getChildren() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Vector<DBTreeNode> children = new Vector<DBTreeNode>();
        
        if (showChildren) {         
            QueryHelper<MethodNode> queryHelper = new QueryHelper<MethodNode>();
            List<MethodNode> methodNodeList = queryHelper.executeQuery(conn,
                    "SELECT * FROM METHODS WHERE class_key = " + getDataObject().getKey(), 
                    new RowConverter<MethodNode>(){
                public MethodNode getRow(ResultSet rs) throws SQLException {       
                    Method method = new Method(rs);                        
                    MethodNode methodNode = new MethodNode(ClassNode.this, method);
                    return methodNode;
                }            
            });

            QueryHelper<FieldNode> queryHelper2 = new QueryHelper<FieldNode>();        
            List<FieldNode> fieldNodeList = queryHelper2.executeQuery(conn,
                    "SELECT * FROM FIELDS WHERE class_key = " + getDataObject().getKey(), 
                    new RowConverter<FieldNode>(){
                public FieldNode getRow(ResultSet rs) throws SQLException {       
                    Field field = new Field(rs);                        
                    FieldNode fieldNode = new FieldNode(ClassNode.this, field);
                    return fieldNode;
                }            
            });

            children.addAll(methodNodeList);
            children.addAll(fieldNodeList);
        
        }
        return children;
    }    

    public Icon getIcon() {
        return ICON;
    }

    /**
     * @return the showChildren
     */
    public boolean isShowChildren() {
        return showChildren;
    }

    /**
     * @param showChildren the showChildren to set
     */
    public void setShowChildren(boolean showChildren) {
        this.showChildren = showChildren;
    }

}
