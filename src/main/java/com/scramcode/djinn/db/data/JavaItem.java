package com.scramcode.djinn.db.data;

import java.awt.Color;

import javax.swing.ImageIcon;

public interface JavaItem {

	public int getKey();	
    
    public String getMappedTable();    
    
    public String getLabel();
    
    public ImageIcon getImage();
    
    public Color getColor();
    
    public boolean isContainedBy(AbstractJavaItem destinationObject);
    
}
