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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import edu.uci.ics.jung.graph.ArchetypeVertex;
import edu.uci.ics.jung.graph.Edge;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.Vertex;
import edu.uci.ics.jung.graph.impl.DirectedSparseEdge;
import edu.uci.ics.jung.graph.impl.DirectedSparseGraph;
import edu.uci.ics.jung.graph.impl.DirectedSparseVertex;
import edu.uci.ics.jung.utils.UserDataContainer.CopyAction;

import net.jnovation.djinn.db.data.DBObject;

/**
 * Tools for producing graphs from various sources.
 * 
 * @author Fabien Benoit <fabien.benoit@gmail.com>
 */
public class GraphTools {
    
    public static DBObject getObjectFromVertex(ArchetypeVertex v) {
        return (DBObject)v.getUserDatum(DBObject.class.getName());
    }
    
    public static DBObject getObjectFromVertex(Vertex v) {
        return (DBObject)v.getUserDatum(DBObject.class.getName());
    }

    /**
     * Converts a set of DBObjects relationships into a graph.
     * @param graphData A map of DBObject linked to their relations.
     * @return A new Graph.
     */
    public static Graph createGraph(Map<DBObject, Set<DBObject>> graphData) {
        
        Graph graph = new DirectedSparseGraph();
        
        // Store created vertices for convenient access during edge construction
        HashMap<DBObject, Vertex> createdVertices = 
            new HashMap<DBObject, Vertex>();
        
        // fill with vertices
        Set<DBObject> verticesSet = graphData.keySet();
        for (Iterator<DBObject> iter = verticesSet.iterator(); iter.hasNext();) {
            DBObject dbObject = iter.next();
            Vertex vertex = new DirectedSparseVertex();
            vertex.setUserDatum(DBObject.class.getName(), dbObject, new CopyAction.Shared());
            graph.addVertex(vertex);            
            createdVertices.put(dbObject, vertex);
        }
        
        // fill with edges
        for (Iterator<DBObject> iter = verticesSet.iterator(); iter.hasNext();) {
            DBObject dbObject = iter.next();
            
            Vertex from = createdVertices.get(dbObject);
            
            // Iterate over the list of referenced dbobjects
            Set<DBObject> references = graphData.get(dbObject);
            for (Iterator<DBObject> iterRef = references.iterator(); iterRef.hasNext();) {
                DBObject referencedDbObject = iterRef.next();

                Vertex to = createdVertices.get(referencedDbObject); 
                if (from != to) {                
                    Edge edge = new DirectedSparseEdge(from, to);
                    graph.addEdge(edge);
                }
            }
            
        }        
        return graph;
    }
        
}
