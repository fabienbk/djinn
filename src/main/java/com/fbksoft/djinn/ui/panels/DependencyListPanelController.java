package com.fbksoft.djinn.ui.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;

import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.db.logic.ReferenceTools;
import com.fbksoft.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;
import com.fbksoft.djinn.model.workspace.AbstractJavaItemTreeNode;
import com.fbksoft.djinn.ui.Application;
import com.fbksoft.djinn.ui.table.JavaItemTableModel;
import com.fbksoft.djinn.util.AbstractSwingWorker;

public class DependencyListPanelController {

	private Application application;
	private DependencyListPanel dependencyListPanel;


	public DependencyListPanelController(Application application) {
		this.application = application;
		this.dependencyListPanel = application.getApplicationFrame().getDependencyDetailsPanel();		
	}
	
	public void updateDependencyList() {		
		List<AbstractJavaItemTreeNode> selectionNode = application.getWorkspaceTreeController().getSelectionNode();
		if (selectionNode.size() > 0) {
			updateDependencyList(selectionNode.get(0));
		}
	}
	
	public void updateDependencyList(final AbstractJavaItemTreeNode sourceNode) {				
		// Get the current granularity		
		final GranularityLevel granularityLevel = dependencyListPanel.getGranularity();
    		
		// Get the current selected 'target' vertex in the graph		            
		AbstractSwingWorker  worker = new AbstractSwingWorker() {
		    @Override
		    public Object construct() {
		        return ReferenceTools.getAllReferences(sourceNode.getJavaItem(), granularityLevel);
		    }
		    
		    @Override
		    public void finished() {
		        if (getValue() != null) {                        
		        	Set<JavaItem> set = (Set<JavaItem>)getValue();		        	
		            JTable referencesTable = dependencyListPanel.getReferencesTable();
					
		            referencesTable.clearSelection();
		            JavaItemTableModel model = (JavaItemTableModel)referencesTable.getModel();
		            
		            ArrayList<JavaItem> filteredList = new ArrayList<JavaItem>();
		            for (JavaItem javaItem : set) {
		            	if (dependencyListPanel.getContainerFilterItem() != null) {
		            		if (javaItem.isContainedBy(dependencyListPanel.getContainerFilterItem())) {		            			
		            			filteredList.add(javaItem);
		            		}
		            	}	
		            	else {
		            		filteredList.add(javaItem);
		            	}
					}
					model.setList(filteredList);		            		            
		        }                    
		    }
		    
		};      
		worker.start();
	}
	
}
