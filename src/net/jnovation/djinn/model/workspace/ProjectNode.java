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
package net.jnovation.djinn.model.workspace;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;

import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.data.Project;
import net.jnovation.djinn.db.mgmt.ConnectionManager;
import net.jnovation.djinn.db.mgmt.QueryHelper;
import net.jnovation.djinn.db.mgmt.RowConverter;
import net.jnovation.djinn.i18n.Images;

public class ProjectNode extends JavaItemTreeNode {
    
    private static final Icon ICON = Images.getIcon("Project.icon");

    public ProjectNode(RootNode parent, Project project) {
        super(parent, project);
    }
        
    protected Vector<JavaItemTreeNode> getChildren() {
        Connection conn = ConnectionManager.getInstance().getConnection();
        Vector<JavaItemTreeNode> children = new Vector<JavaItemTreeNode>();
        QueryHelper<LocationNode> queryHelper = new QueryHelper<LocationNode>();
        List<LocationNode> projectNodeList = queryHelper.executeQuery(conn,
                "SELECT * FROM LOCATIONS WHERE project_key = " + getJavaItem().getKey(),
                new RowConverter<LocationNode>(){
                    public LocationNode getRow(ResultSet rs) throws SQLException {
                        Location location = new Location(rs);
                        LocationNode locationNode = new LocationNode(ProjectNode.this, location);                                            
                        return locationNode;
                    }            
        });
        
        children.addAll(projectNodeList);
        return children;
    }

    public Icon getIcon() {
        return ICON;
    }    
         
    
}
