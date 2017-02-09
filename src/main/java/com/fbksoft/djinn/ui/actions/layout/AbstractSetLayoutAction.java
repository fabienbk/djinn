package com.fbksoft.djinn.ui.actions.layout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.fbksoft.djinn.db.data.JavaDependency;
import com.fbksoft.djinn.db.data.JavaItem;
import com.fbksoft.djinn.ui.Application;
import com.fbksoft.djinn.ui.panels.TabbedGraphAreaController;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public abstract class AbstractSetLayoutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public AbstractSetLayoutAction(String label) {
		putValue(Action.NAME, label);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		TabbedGraphAreaController graphAreaController = Application.getInstance().getGraphAreaController();
		Graph<JavaItem, JavaDependency> graph = graphAreaController.getActiveGraph();
		if (graph!=null) {
			Layout<JavaItem, JavaDependency> layout = getLayout(graph);
			graphAreaController.changeLayout(layout);
		}		
	}

	public abstract Layout<JavaItem, JavaDependency> getLayout(Graph<JavaItem, JavaDependency> graph);
}
