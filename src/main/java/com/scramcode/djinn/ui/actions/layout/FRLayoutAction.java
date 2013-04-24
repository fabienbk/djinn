package com.scramcode.djinn.ui.actions.layout;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.AbstractJavaItem;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public class FRLayoutAction extends AbstractSetLayoutAction {

	private static final long serialVersionUID = 1L;

	public FRLayoutAction() {
		super("Fruchterman-Reingold (default)");
	}
	
	@Override
	public Layout<AbstractJavaItem, JavaDependency> getLayout(Graph<AbstractJavaItem, JavaDependency> graph) {	
		return new FRLayout<AbstractJavaItem, JavaDependency>(graph);
	}
	
}
