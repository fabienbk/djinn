package com.scramcode.djinn.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JPopupMenu;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.JavaItem;
import com.scramcode.djinn.ui.Application;
import com.scramcode.djinn.ui.i18n.Images;

import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class PopupGraphPlugin extends AbstractPopupGraphMousePlugin {

	protected JPopupMenu popup;

	public PopupGraphPlugin() {
	}

	
	@SuppressWarnings({ "unchecked", "serial" })
	protected void handlePopup(MouseEvent e) {
		popup = new JPopupMenu();
		final VisualizationViewer<JavaItem, JavaDependency> vv = (VisualizationViewer<JavaItem, JavaDependency>) e.getSource();
		final Layout<JavaItem, JavaDependency> layout = vv.getGraphLayout();
		final Graph<JavaItem, JavaDependency> graph = layout.getGraph();
		GraphElementAccessor<JavaItem, JavaDependency> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {
			final PickedState<JavaItem> pickedVertexState = vv.getPickedVertexState();
			final PickedState<JavaDependency> pickedEdgeState = vv.getPickedEdgeState();

			final Set<JavaItem> pickedVertices = pickedVertexState.getPicked();

			AbstractAction abstractAction = new AbstractAction("Remove " + pickedVertices.size() + " selected item(s)", Images.getIcon("Delete.icon")) {
				public void actionPerformed(ActionEvent e) {
					for (JavaItem javaItem : pickedVertices) {
						graph.removeVertex(javaItem);
					}
					vv.repaint();
				}
			};
			abstractAction.setEnabled(pickedVertices.size() > 0);
			popup.add(abstractAction);

			popup.show(vv, e.getX(), e.getY());
		}
	}
}
