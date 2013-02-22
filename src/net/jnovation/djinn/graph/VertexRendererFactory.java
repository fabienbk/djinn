/*
 * Created on May 1, 2006
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
package net.jnovation.djinn.graph;

import java.awt.Shape;

import javax.swing.Icon;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.decorators.AbstractVertexShapeFunction;
import edu.uci.ics.jung.graph.decorators.EdgeShape;
import edu.uci.ics.jung.graph.decorators.VertexAspectRatioFunction;
import edu.uci.ics.jung.graph.decorators.VertexIconFunction;
import edu.uci.ics.jung.graph.decorators.VertexSizeFunction;
import edu.uci.ics.jung.graph.decorators.VertexStringer;
import edu.uci.ics.jung.visualization.PluggableRenderer;

public class VertexRendererFactory {
    
    public static PluggableRenderer getRenderer() {
        PluggableRenderer pr = new PluggableRenderer();
                        
        // Edge shape
        pr.setEdgeShapeFunction(new EdgeShape.Line());
        
        // Vertex shape
        pr.setVertexShapeFunction(new VertexShapeSizeAspect());
        
        //pr.setVertexPaintFunction(new VertexPainter());
        
        pr.setVertexIconFunction(new VertexIconizer());
        
        // Vertex Labelization
        pr.setVertexStringer(new VertexStringer() {                     
            public String getLabel(ArchetypeVertex v) {
                return GraphTools.getObjectFromVertex(v).getLabel();
            }
        });               
        
        return pr;
    }
    
    private final static class VertexIconizer implements VertexIconFunction {

        public Icon getIcon(ArchetypeVertex v) {
            return GraphTools.getObjectFromVertex(v).getImage();
        }

    }
        
    private final static class VertexShapeSizeAspect 
    extends AbstractVertexShapeFunction 
    implements VertexSizeFunction, VertexAspectRatioFunction {        
        public VertexShapeSizeAspect() {
            setSizeFunction(this);
            setAspectRatioFunction(this);
        }
        
        public int getSize(Vertex v) {            
            return 32;
        }
        
        public float getAspectRatio(Vertex v) {
            return 1.0f;
        }
        
        public Shape getShape(Vertex v) {            
            return factory.getEllipse(v);
        }        
    }
}
