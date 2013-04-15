/*
 * Created on Mar 12, 2006
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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.jnovation.djinn.db.data.JavaDependency;
import net.jnovation.djinn.db.data.JavaItem;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;

/**
 * Tools for producing graphs from various sources.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class GraphTools {
    
    /**
     * Converts a set of DBObjects relationships into a graph.
     * @param graphData A map of DBObject linked to their relations.
     * @return A new Graph.
     */
    public static Graph<JavaItem, JavaDependency> createGraph(Map<JavaItem, Set<JavaItem>> graphData) {
        
        Graph<JavaItem, JavaDependency> graph = new DirectedSparseMultigraph<JavaItem, JavaDependency>();
        
        // fill with vertices
        Set<JavaItem> verticesSet = graphData.keySet();
        for (JavaItem javaItem : verticesSet) {
            graph.addVertex(javaItem);            
        }
        
        // fill with edges
        for (Iterator<JavaItem> iter = verticesSet.iterator(); iter.hasNext();) {
            JavaItem originJavaItem = iter.next();
                        
            // Iterate over the list of referenced dbobjects
            Set<JavaItem> references = graphData.get(originJavaItem);
            for (Iterator<JavaItem> iterRef = references.iterator(); iterRef.hasNext();) {
                JavaItem destinationJavaItem = iterRef.next();
 
                if (destinationJavaItem != originJavaItem) {                
                    graph.addEdge(new JavaDependency(originJavaItem, destinationJavaItem), originJavaItem, destinationJavaItem);
                }
            }
            
        }        
        return graph;
    }
        
}
