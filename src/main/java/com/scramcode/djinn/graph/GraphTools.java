/*
 * Created on Mar 12, 2006
 * By Fabien Benoit - http://www.scramcode.com 
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
package com.scramcode.djinn.graph;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.scramcode.djinn.db.data.JavaDependency;
import com.scramcode.djinn.db.data.AbstractJavaItem;

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
    public static Graph<AbstractJavaItem, JavaDependency> createGraph(Map<AbstractJavaItem, Set<AbstractJavaItem>> graphData) {
        
        Graph<AbstractJavaItem, JavaDependency> graph = new DirectedSparseMultigraph<AbstractJavaItem, JavaDependency>();
        
        // fill with vertices
        Set<AbstractJavaItem> verticesSet = graphData.keySet();
        for (AbstractJavaItem javaItem : verticesSet) {
            graph.addVertex(javaItem);            
        }
        
        // fill with edges
        for (Iterator<AbstractJavaItem> iter = verticesSet.iterator(); iter.hasNext();) {
            AbstractJavaItem originJavaItem = iter.next();
                        
            // Iterate over the list of referenced dbobjects
            Set<AbstractJavaItem> references = graphData.get(originJavaItem);
            for (Iterator<AbstractJavaItem> iterRef = references.iterator(); iterRef.hasNext();) {
                AbstractJavaItem destinationJavaItem = iterRef.next();
 
                if (!destinationJavaItem.equals(originJavaItem)) {                
                    graph.addEdge(new JavaDependency(originJavaItem, destinationJavaItem), originJavaItem, destinationJavaItem);
                }
            }
            
        }        
        return graph;
    }
        
}
