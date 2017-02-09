package com.fbksoft.djinn.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.Icon;

import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.data.Location;
import com.fbksoft.djinn.db.data.Package;
import com.fbksoft.djinn.model.workspace.ClassNode;
import com.fbksoft.djinn.model.workspace.LocationNode;
import com.fbksoft.djinn.model.workspace.PackageNode;


@SuppressWarnings("rawtypes")
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
    
    public DependencyListModel(List<JavaItem> javaItemList) {
        for (JavaItem object : javaItemList) {
            if (object instanceof Location) {
                nodeList.add(new LocationNode(null, (Location)object));
            }
            if (object instanceof Package) {
                nodeList.add(new PackageNode(null, (Package)object));
            }
            if (object instanceof com.fbksoft.djinn.db.data.Clazz) {
                nodeList.add(new ClassNode(null, (com.fbksoft.djinn.db.data.Clazz)object));
            }
            
        }
    }

    public DecoratedNode getElementAt(int index) {
        return nodeList.get(index);
    }

    public int getSize() {
        return nodeList.size();
    }

}
