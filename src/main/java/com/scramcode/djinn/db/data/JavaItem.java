package com.scramcode.djinn.db.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import com.scramcode.djinn.db.logic.JavaItemVistor;
import com.scramcode.djinn.model.workspace.AbstractJavaItemTreeNode;

@JsonTypeInfo(
  use = JsonTypeInfo.Id.NAME,
  include = JsonTypeInfo.As.PROPERTY,
  property = "instance")
@JsonSubTypes({
  @JsonSubTypes.Type(value = Project.class, name = "project"),
  @JsonSubTypes.Type(value = Location.class, name = "location")
})
public interface JavaItem {

	public long getKey();	
    
	public JavaItem getParent();
	
    public String getLabel();
    
    public ImageIcon getIcon();
    
    public ImageIcon getSmallIcon();
    
    public Color getColor();
    
    public void accept(JavaItemVistor javaItemVistor);

	boolean isContainedBy(JavaItem destinationObject);

	public AbstractJavaItemTreeNode getTreeNode();
}
