package com.scramcode.djinn.db.data;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.tree.TreePath;

import com.scramcode.djinn.db.logic.JavaItemVistor;
import com.scramcode.djinn.model.workspace.AbstractJavaItemTreeNode;

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
