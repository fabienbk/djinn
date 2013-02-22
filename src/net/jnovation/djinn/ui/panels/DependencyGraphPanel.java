/*
 * Created on Mar 9, 2006
 * By Fabien Benoit - http://www.jnovation.net 
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
package net.jnovation.djinn.ui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.jnovation.djinn.graph.VertexRendererFactory;
import net.jnovation.djinn.ui.panels.DependencyDetailsPanel;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.decorators.PickableEdgePaintFunction;
import edu.uci.ics.jung.visualization.FRLayout;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layout;
import edu.uci.ics.jung.visualization.PickedState;
import edu.uci.ics.jung.visualization.PluggableRenderer;
import edu.uci.ics.jung.visualization.ShapePickSupport;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.PickingGraphMousePlugin;
import edu.uci.ics.jung.visualization.control.PluggableGraphMouse;

public class DependencyGraphPanel extends JPanel {

    /** Comment for <code>serialVersionUID</code> */
    private static final long serialVersionUID = -2343340380947041680L;
    
    private VisualizationViewer visualizationViewer;
    private PluggableRenderer pluggableRenderer;
    private DependencyDetailsPanel dependencyDetailsPanel;	    
    private PickedState pickedState;

    public DependencyGraphPanel(Graph g) {
                
        pluggableRenderer = VertexRendererFactory.getRenderer();
        Layout layout = new FRLayout(g);               
        visualizationViewer = new VisualizationViewer(layout, pluggableRenderer);
                
        pickedState = visualizationViewer.getPickedState();
        // Selectable edge paint function
        pluggableRenderer.setEdgePaintFunction(new PickableEdgePaintFunction(pickedState,Color.black,Color.cyan));
                
        ShapePickSupport ps = new ShapePickSupport();
        visualizationViewer.setPickSupport(ps);
                       
        visualizationViewer.setBackground(Color.white);        
        GraphZoomScrollPane scrollPane = new GraphZoomScrollPane(visualizationViewer);
        
        //DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
        final PluggableGraphMouse graphMouse = new PluggableGraphMouse();
        graphMouse.add( new PickingGraphMousePlugin() );
                
        //graphMouse.setMode(Mode.PICKING);
        visualizationViewer.setGraphMouse(graphMouse);
        
        this.setLayout(new BorderLayout());
        this.add(scrollPane, BorderLayout.CENTER);
                
        dependencyDetailsPanel = new DependencyDetailsPanel();
        this.add(dependencyDetailsPanel, BorderLayout.SOUTH);
     
                        
    }
    
    public PickedState getPickedState() {
        return pickedState;
    }
    
    public DependencyDetailsPanel getDependencyDetailsPanel() {
        return dependencyDetailsPanel;
    }
        
    /**
     * copy the visible part of the graph to a file as a jpeg image
     * @param file
     */
    public void writeJPEGImage(File file) {
        int width = visualizationViewer.getWidth();
        int height = visualizationViewer.getHeight();

        BufferedImage bi = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = bi.createGraphics();
        visualizationViewer.paint(graphics);
        graphics.dispose();
        
        try {
            ImageIO.write(bi, "jpeg", file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        
}
