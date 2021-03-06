/*
 * Created on Mar 9, 2006
 * By Fabien Benoit - http://www.fbksoft.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.fbksoft.djinn.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import com.fbksoft.djinn.db.data.JavaDependency;
import com.fbksoft.djinn.db.data.JavaItem;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.RenderContext;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;
import edu.uci.ics.jung.visualization.picking.PickedState;

public class GraphPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    
    private VisualizationViewer<JavaItem, JavaDependency> visualizationViewer;       

    private Graph<JavaItem, JavaDependency> graph;
    
    public GraphPanel(Graph<JavaItem, JavaDependency> graph) {
                
    	this.graph = graph;
    	
        Layout<JavaItem, JavaDependency> layout = new FRLayout<JavaItem, JavaDependency>(graph);                       
        visualizationViewer = new VisualizationViewer<JavaItem, JavaDependency>(layout);
        /*visualizationViewer.getRenderer().setVertexRenderer(new Vertex<JavaItem, JavaDependency>() {			
			@Override
			public void paintVertex(RenderContext<JavaItem, JavaDependency> rc, Layout<JavaItem, JavaDependency> layout, JavaItem javaItem) {				
				Point2D center = layout.transform(javaItem);
				
				GraphicTools.drawNode(rc.getGraphicsContext().getDelegate(), (int)center.getX(), (int)center.getY(), javaItem.getImage(), javaItem.getLabel(), 3);
			}
		});*/
              
        RenderContext<JavaItem, JavaDependency> renderContext = visualizationViewer.getRenderContext();
        
		renderContext.setVertexIconTransformer(new Transformer<JavaItem, Icon>() {				
			public Icon transform(JavaItem node) {
	            return node.getIcon();
			}
		});
		renderContext.setVertexLabelTransformer(new Transformer<JavaItem, String>() {
			public String transform(JavaItem node) {
				return node.getLabel();
			}
		});
    
        visualizationViewer.setBackground(Color.white);        
        GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(visualizationViewer);
        
        final PluggableGraphMouse graphMouse = new PluggableGraphMouse();
        graphMouse.add( new PickingGraphMousePlugin<JavaItem, JavaDependency>() );
        graphMouse.add( new PopupGraphPlugin() );
                
        visualizationViewer.setGraphMouse(graphMouse);
        
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
          
    }
    
    public PickedState<JavaDependency> getPickedState() {
        return visualizationViewer.getPickedEdgeState();
    }
            
    /**
     * copy the visible part of the graph to a file as a jpeg image
     * @param file
     */
    public void writeJPEGImage(File file) {
        int width = visualizationViewer.getWidth();
        int height = visualizationViewer.getHeight();

        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        visualizationViewer.paint(graphics);
        graphics.dispose();
        
        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Graph<JavaItem, JavaDependency> getGraph() {
		return graph;
	}
    
    public VisualizationViewer<JavaItem, JavaDependency> getVisualizationViewer() {
		return visualizationViewer;
	}
        
}
