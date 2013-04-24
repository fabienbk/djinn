package com.scramcode.djinn.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

@SuppressWarnings("rawtypes")
public class GraphGranularityComboBoxModel extends AbstractListModel implements ComboBoxModel {	

    public enum GranularityLevel {
        JAR, PACKAGE, CLASS 
    }
    
    private static final long serialVersionUID = 7974025009804311758L;
    
    private List<String> objectList = new ArrayList<String>();
    private String selectedItem = "Class";
            
    public GraphGranularityComboBoxModel() {               
        objectList.add("Class");
        objectList.add("Package");
        objectList.add("Jar");
    }

    public Object getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(Object anItem) {
        this.selectedItem = (String)anItem;
    }

    public String getElementAt(int index) {
        return objectList.get(index);
    }

    public int getSize() {        
        return objectList.size();
    }

    public GranularityLevel getGranularity() {
        if ("Class".equals(selectedItem)) {
            return GranularityLevel.CLASS;
        }
        else if ("Package".equals(selectedItem)) {
            return GranularityLevel.PACKAGE;            
        }
        return GranularityLevel.JAR;        
    }
            
}
