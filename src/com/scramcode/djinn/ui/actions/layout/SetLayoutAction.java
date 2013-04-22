package com.scramcode.djinn.ui.actions.layout;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Messages;
import com.scramcode.djinn.ui.panels.GraphAreaController;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public abstract class SetLayoutAction extends AbstractAction {

	private static final long serialVersionUID = 1L;

	public SetLayoutAction(String label) {
		putValue(Action.NAME, label);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		GraphAreaController graphAreaController = Application.getInstance().getGraphAreaController();
		Graph<JavaItem, JavaDependency> graph = graphAreaController.getActiveGraph();
		if (graph!=null) {
			Layout<JavaItem, JavaDependency> layout = getLayout(graph);
			graphAreaController.changeLayout(layout);
		}		
	}

	public abstract Layout<JavaItem, JavaDependency> getLayout(Graph<JavaItem, JavaDependency> graph);
}
