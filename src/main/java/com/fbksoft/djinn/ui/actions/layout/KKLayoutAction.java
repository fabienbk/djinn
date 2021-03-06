package com.fbksoft.djinn.ui.actions.layout;

import com.fbksoft.djinn.db.data.JavaDependency;
import com.fbksoft.djinn.db.data.JavaItem;

import edu.uci.ics.jung.algorithms.layout.KKLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;

public class KKLayoutAction extends AbstractSetLayoutAction {

	private static final long serialVersionUID = 1L;

	public KKLayoutAction() {
		super("Kamada-Kawai");
	}
	
	@Override
	public Layout<JavaItem, JavaDependency> getLayout(Graph<JavaItem, JavaDependency> graph) {	
		return new KKLayout<JavaItem, JavaDependency>(graph);
	}
	
}
