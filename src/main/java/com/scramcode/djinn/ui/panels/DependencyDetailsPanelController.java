package com.scramcode.djinn.ui.panels;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JTable;

import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.db.logic.ReferenceTools;
import com.scramcode.djinn.model.GraphGranularityComboBoxModel.GranularityLevel;
import com.scramcode.djinn.model.workspace.AbstractJavaItemTreeNode;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.table.JavaItemTableModel;
import com.scramcode.djinn.util.AbstractSwingWorker;

public class DependencyDetailsPanelController {

	private Application application;
	private DependencyDetailsPanel dependencyDetailsPanel;


	public DependencyDetailsPanelController(Application application) {
		this.application = application;
		this.dependencyDetailsPanel = application.getApplicationFrame().getDependencyDetailsPanel();		
	}
	
	public void updateSourceSelection() {		
		List<AbstractJavaItemTreeNode> selectionNode = application.getWorkspaceTreeController().getSelectionNode();
		if (selectionNode.size() > 0) {
			updateSourceSelection(selectionNode.get(0));
		}
	}
	
	public void updateSourceSelection(final AbstractJavaItemTreeNode sourceNode) {				
		// Get the current granularity
		final GranularityLevel granularityLevel = dependencyDetailsPanel.getGranularity();
    		
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
		            JTable referencesTable = dependencyDetailsPanel.getReferencesTable();
					
		            referencesTable.clearSelection();
		            JavaItemTableModel model = (JavaItemTableModel)referencesTable.getModel();
		            
		            ArrayList filteredList = new ArrayList();
		            for (JavaItem javaItem : set) {
		            	if (dependencyDetailsPanel.getContainerFilterItem() != null) {
		            		if (!javaItem.isContainedBy(dependencyDetailsPanel.getContainerFilterItem())) {
		            			continue;
		            		}
		            	}
		            	filteredList.add(javaItem);
					}
					model.setList(filteredList);		            		            
		        }                    
		    }
		    
		};      
		worker.start();
	}
	
}
