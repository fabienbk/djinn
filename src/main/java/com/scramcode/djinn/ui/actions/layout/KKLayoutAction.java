package com.scramcode.djinn.ui.actions.layout;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.AbstractJavaItem;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public class KKLayoutAction extends AbstractSetLayoutAction {

	private static final long serialVersionUID = 1L;

	public KKLayoutAction() {
		super("Kamada-Kawai");
	}
	
	@Override
	public Layout<AbstractJavaItem, JavaDependency> getLayout(Graph<AbstractJavaItem, JavaDependency> graph) {	
		return new KKLayout<AbstractJavaItem, JavaDependency>(graph);
	}
	
}
