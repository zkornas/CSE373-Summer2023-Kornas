package graphs.shortestpaths;

import priorityqueues.ExtrinsicMinPQ;
import priorityqueues.NaiveMinPQ;
import graphs.BaseEdge;
import graphs.Graph;


import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new NaiveMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        // return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    //Computes a shortest path from start to end in a given graph, and returns a ShortestPath object.
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        Set<V> known = new HashSet<>();
        Map<V, E> edgeTo = new HashMap<>(); // Stores previous vertex, shortest path to that vertex
        Map<V, Double> distTo = new HashMap<>(); // Stores vertex, given shortest distance to that vertex

        // Initialize distTo with start node as zero, all others are not initialized
        distTo.put(start, 0.0);

        //Edge Case: if there is only 1 vertex, then there is a distance of 0 as the shortest path
        if (Objects.equals(start, end)) {
            distTo.put(end, 0.0);
            return edgeTo;
        }

        //Create a priority queue to keep track of the vertices that need to be explored
        ExtrinsicMinPQ<V> options =  createMinPQ();
        V curr = start; //start vertex
        options.add(curr, 0);

        //while the options in the priority queue us not empty
        while (!options.isEmpty()) {
            //remove and return the vertex with the shortest distance
            curr = options.removeMin();     //currently the known shortest distance
            known.add(curr);
            if (Objects.equals(curr, end)) {
                break;
            }

            //add to the known vertices to keep track for shortest path
            Collection<E> currEdges = graph.outgoingEdgesFrom(curr); //outgoing edges from the currext vertex
            //for each outgoing edge
            for (E edge : currEdges) {
                //if the edge of that vertex has not been recognized
                if (!known.contains(edge.to())) {
                    //calculate the new distance to that vertex
                    double newDist = distTo.get(curr) + edge.weight();
                    //if the vertex isnt in the priority queue or if the new distance is smaller
                    if (!options.contains(edge.to()) || newDist < distTo.get(edge.to())) {
                        //then update the new shortest distance
                        distTo.put(edge.to(), newDist);
                        //and the preceding edge
                        edgeTo.put(edge.to(), edge);
                        //if the vertex with new dist is already in the priority queue
                        if (!options.contains(edge.to())) {
                            //then add the vertex
                            options.add(edge.to(), newDist);
                        } else {
                            //otherwise update the priority queue if the vertex is already in it wit the new dist
                            options.changePriority(edge.to(), newDist);
                        }
                    }
                }
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        // back tracks through edgeTo
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        //Retrieves the last edge associated with the end vertex
        E edge = spt.get(end);
        //if there's no edge associated with the end vertex, there's no path from the start vertex to the
        //end vertex
        if (Objects.equals(edge, null)) {
            return new ShortestPath.Failure<>();
        }

        List<E> edges = new LinkedList<>();

        //add edges to edges list
        while (!Objects.equals(edge, null)) {
            edges.add(edge);
            edge = spt.get(edge.from());
        }

        if (edges.isEmpty()) {
            System.out.println("THE LIST IS EMPTY!!!");
        }
        Collections.reverse(edges);



        return new ShortestPath.Success<>(edges);
    }

}
