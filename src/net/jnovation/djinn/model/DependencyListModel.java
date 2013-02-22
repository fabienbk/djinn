package net.jnovation.djinn.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Icon;

import net.jnovation.djinn.db.data.DBObject;
import net.jnovation.djinn.db.data.Location;
import net.jnovation.djinn.db.data.Package;
import net.jnovation.djinn.model.workspace.ClassNode;
import net.jnovation.djinn.model.workspace.LocationNode;
import net.jnovation.djinn.model.workspace.PackageNode;

public class DependencyListModel extends AbstractListModel {
    
    private static final long serialVersionUID = -4344727518624732109L;
    
    private List<DecoratedNode> nodeList = new ArrayList<DecoratedNode>();
    
    public DependencyListModel() {
        nodeList.add(new DecoratedNode() {
           public Icon getIcon() {
                return null;
            } 
           public String getLabel() {
            return "Loading...";
        }
        });
    }
    
    public DependencyListModel(List<DBObject> dboList) {
        for (DBObject object : dboList) {
            if (object instanceof Location) {
                nodeList.add(new LocationNode(null, (Location)object));
            }
            if (object instanceof Package) {
                nodeList.add(new PackageNode(null, (Package)object));
            }
            if (object instanceof net.jnovation.djinn.db.data.Class) {
                nodeList.add(new ClassNode(null, (net.jnovation.djinn.db.data.Class)object));
            }
            
        }
    }

    public Object getElementAt(int index) {
        return nodeList.get(index);
    }

    public int getSize() {
        return nodeList.size();
    }

}
