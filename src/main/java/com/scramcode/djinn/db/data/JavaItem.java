package com.scramcode.djinn.db.data;

import java.awt.Color;

import javax.swing.ImageIcon;

import com.scramcode.djinn.db.logic.JavaItemVistor;

public interface JavaItem {

	public long getKey();	
    
    public String getLabel();
    
    public ImageIcon getImage();
    
    public Color getColor();
    
    public void accept(JavaItemVistor javaItemVistor);

	boolean isContainedBy(JavaItem destinationObject);
}
