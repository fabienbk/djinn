package com.scramcode.djinn.ui.actions.layout;

import org.apache.commons.collections15.Transformer;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.SpringLayout;
import edu.uci.ics.jung.graph.Graph;

public class SpringLayoutAction extends SetLayoutAction {

	private static final long serialVersionUID = 1L;

	public SpringLayoutAction() {
		super("Spring");
	}
	
	@Override
	public Layout<JavaItem, JavaDependency> getLayout(Graph<JavaItem, JavaDependency> graph) {	
		return new SpringLayout<JavaItem, JavaDependency>(graph, new Transformer<JavaDependency, Integer>() {
			public Integer transform(JavaDependency arg0) {			
				return 180;
			}
		});
	}
	
}
