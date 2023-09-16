package seamcarving;

import graphs.Edge;
import graphs.Graph;
import graphs.shortestpaths.DijkstraShortestPathFinder;
import graphs.shortestpaths.ShortestPath;
import graphs.shortestpaths.ShortestPathFinder;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class DijkstraSeamFinder implements SeamFinder {
    private final ShortestPathFinder<Graph<Vertex, Edge<Vertex>>, Vertex, Edge<Vertex>> pathFinder;

    public DijkstraSeamFinder() {
        this.pathFinder = createPathFinder();
    }

    protected <G extends Graph<V, Edge<V>>, V> ShortestPathFinder<G, V, Edge<V>> createPathFinder() {
        /*
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
        */
        return new DijkstraShortestPathFinder<>();
    }

    @Override
    public List<Integer> findHorizontalSeam(double[][] energies) {
        System.out.println("Energies matrix before modification:");
        for (int i = 0; i < energies.length; i++) {
            for (int j = 0; j < energies[i].length; j++) {
                System.out.print(energies[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();

        MyGraph newGraph = new MyGraph(energies);
        //find shortest path in our new graph from start to end vertex and store it in a variable
        ShortestPath<Vertex, Edge<Vertex>> test = pathFinder.findShortestPath(newGraph, newGraph.start, newGraph.end);
        //make a list to store the edges from that shortest path
        List<Edge<Vertex>> listOfEdges = test.edges();
        List<Integer> locationOfSeam = new LinkedList<>();

        //debugging code
        System.out.println();
        System.out.println("Size of listOfEdges: " + listOfEdges.size());
        System.out.println("Edges in listOfEdges:");
        for (Edge<Vertex> edge : listOfEdges) {
            System.out.println("Coming from: [" + (edge.from()).xValue + ", " + (edge.from()).yValue +
                "]. Going to: [" + (edge.to()).xValue + ", " + (edge.to()).yValue + "].");
        }

        //iterate over each edge in the listOfEdges
        for (Edge<Vertex> edge : listOfEdges) {
            //Check if the destination vertex is an actual pixel, not a dummy vertex
            Vertex toVertex = edge.to();
            if (toVertex != newGraph.end) {
                locationOfSeam.add(toVertex.yValue);
            }
        }

        System.out.println();
        System.out.println("Location of Seam:");
        for (Integer seamCoordinate : locationOfSeam) {
            System.out.print(seamCoordinate + " ");
        }
        System.out.println();

        System.out.println();
        System.out.println("Energies matrix after modification:");
        for (int i = 0; i < energies.length; i++) {
            for (int j = 0; j < energies[i].length; j++) {
                System.out.print(energies[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println();
        //return the list
        return locationOfSeam;
    }

    @Override
    public List<Integer> findVerticalSeam(double[][] energies) {
        int rowCount = energies.length;
        int colCount = energies[0].length;

        double[][] newEnergies = new double[colCount][rowCount];

        for (int i = 0; i < colCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                newEnergies[i][j] = energies[j][i];
            }
        }

        System.out.println("Energies matrix before modification:");
        for (int i = 0; i < energies.length; i++) {
            for (int j = 0; j < energies[i].length; j++) {
                System.out.print(energies[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println();

        MyGraph newGraph = new MyGraph(newEnergies);
        //find shortest path in our new graph from start to end vertex and store it in a variable
        ShortestPath<Vertex, Edge<Vertex>> test = pathFinder.findShortestPath(newGraph, newGraph.start, newGraph.end);
        //make a list to store the edges from that shortest path
        List<Edge<Vertex>> listOfEdges = test.edges();
        List<Integer> locationOfSeam = new LinkedList<>();

        //debugging code
        System.out.println();
        System.out.println("Size of listOfEdges: " + listOfEdges.size());
        System.out.println("Edges in listOfEdges:");
        for (Edge<Vertex> edge : listOfEdges) {
            System.out.println("Coming from: [" + (edge.from()).xValue + ", " + (edge.from()).yValue +
                "]. Going to: [" + (edge.to()).xValue + ", " + (edge.to()).yValue + "].");
        }

        //iterate over each edge in the listOfEdges
        for (Edge<Vertex> edge : listOfEdges) {
            //Check if the destination vertex is an actual pixel, not a dummy vertex
            Vertex toVertex = edge.to();
            if (toVertex != newGraph.end) {
                locationOfSeam.add(toVertex.yValue);
            }
        }

        System.out.println();
        System.out.println("Location of Seam:");
        for (Integer seamCoordinate : locationOfSeam) {
            System.out.print(seamCoordinate + " ");
        }
        System.out.println();

        System.out.println();
        System.out.println("Energies matrix after modification:");
        for (int i = 0; i < energies.length; i++) {
            for (int j = 0; j < energies[i].length; j++) {
                System.out.print(energies[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println();
        //return the list
        return locationOfSeam;
    }


    // Only info we have is 2D array of energies representing pixels
    // Pass into constructor of graph
    // How do I change these energies into a graph with verticies and edges
    // implementing outgoing edges return that list of edges when method is called
    //

    private static final class MyGraph implements Graph<Vertex, Edge<Vertex>> {

        private Vertex[][] vertexMatrix;
        private static Vertex start;
        private static Vertex end;
        private Map<Vertex, List<Edge<Vertex>>> edges;

        //start node should have extra column to left of the (0,0) coordinate where x -1 and y coordinate is anything
        //end node should be length + 1
        private MyGraph(double[][] energies) {
            double[] row = energies[0];
            int rowLength = row.length;
            int colHeight = energies.length;
            // Adding +2 in row length so that we can have room for dummy start and end nodes
            this.vertexMatrix = new Vertex[rowLength][colHeight + 2];

            Vertex startVertex = new Vertex(-1, 0, 0.0);
            Vertex endVertex = new Vertex(vertexMatrix[0].length - 2, 0, 0.0);

            vertexMatrix[0][0] = startVertex;
            vertexMatrix[0][vertexMatrix[0].length - 1] = endVertex;

            this.start = startVertex;
            this.end = endVertex;

            constructMatrix(energies);
            assignEdges();
        }
        private void constructMatrix(double[][] energies) {
            //loop through each row
            for (int i = 0; i < energies.length; i++) {
                double[] currRow = energies[i];
                //then loop through each column in given row
                for (int j = 1; j <= currRow.length; j++) {
                    //Create new vertex with appropriate location
                    //Adding everything +1 in the row so it is shifted, to allow room for dummy start and end nodes
                    Vertex currVertex = new Vertex(i, j - 1, currRow[j - 1]);
                    this.vertexMatrix[j - 1][i + 1] = currVertex;
                }
            }
        }



        // the assign edges is super messy sorry!
        // edges are stored in map with key as starting vertex and value is array of the edges
        // create new edges like this: Edge<Vertex> edge = new Edge<>(from, to, weight);
        // weight is the energy of the vertex its going to
        private void assignEdges() {
            //initialize the edges map
            this.edges = new HashMap<>();

            //Loop through each row:
            for (int i = 0; i < vertexMatrix.length; i++) {
                Vertex[] currRow = vertexMatrix[i];

                //Loop through each column in a given row:
                //We will start at 1 and end early to give room for our dummy node
                for (int j = 1; j < vertexMatrix[i].length - 2; j++) {
                    List<Edge<Vertex>> currEdges = new LinkedList<>();
                    //add the top diagonal vertex if we are NOT at top of matrix
                    if (i > 0) {
                        currEdges.add(new Edge<>(vertexMatrix[i][j], vertexMatrix[i - 1][j + 1],
                            vertexMatrix[i - 1][j + 1].energy));
                    }
                    //we will also add bottom diagonal vertex if we are not at the bottom of the matrix
                    if (i < vertexMatrix.length - 1) {
                        currEdges.add(new Edge<>(vertexMatrix[i][j], vertexMatrix[i + 1][j + 1],
                            vertexMatrix[i + 1][j + 1].energy));
                    }
                    //we will always add vertex to the right of the current vertex during this loop
                    currEdges.add(new Edge<>(vertexMatrix[i][j], vertexMatrix[i][j + 1],
                                             vertexMatrix[i][j + 1].energy));
                    //we will add the edge list with the key of the current vertex to our edge map
                    this.edges.put(vertexMatrix[i][j], currEdges);
                }
            }
            //We are adding the edges from our dummy nodes
            List<Edge<Vertex>> startEdges = new LinkedList<>();

            //Loop through each row in our matrix until it reaches the last row (exclude)
            for (int i = 0; i < vertexMatrix.length; i++) {
                //outgoing edges from last column to end vertex
                List<Edge<Vertex>> endEdge = new LinkedList<>();
                //create an edge from the start vertex to the current row and column 1
                startEdges.add(new Edge<>(this.start, vertexMatrix[i][1], vertexMatrix[i][1].energy));
                //creates an edge from the vertex in current row and the column before end vertex
                endEdge.add(new Edge<>(vertexMatrix[i][vertexMatrix[i].length - 2], this.end, this.end.energy));
                this.edges.put(vertexMatrix[i][vertexMatrix[i].length - 2], endEdge);
            }
            this.edges.put(this.start, startEdges);
        }
        @Override
        public Collection<Edge<Vertex>> outgoingEdgesFrom(Vertex V) {
            return edges.get(V);
        }
    }
    private static final class Vertex {
        private int xValue;
        private int yValue;
        private int myHashCode;

        private double energy;

        // just stores energy and x and y coordinates
        // Can use edge provided

        private Vertex(int x, int y, double energy) {
            this.xValue = x;
            this.yValue = y;

            // Bijective algorithm to get unique hashcode of two integers
            // call Objects.hash(x, y);

            this.myHashCode = this.hashCode();
            this.energy = energy;

        }

        @Override
        public boolean equals(Object otherObject) {
            if (this == otherObject) {
                return true;
            }
            if (otherObject == null || getClass() != otherObject.getClass()) {
                return false;
            }
            Vertex vertex = (Vertex) otherObject;
            return xValue == vertex.xValue && yValue == vertex.yValue;
        }



        @Override
        public int hashCode() {
            return Objects.hash(xValue, yValue);
            /*
            return this.myHashCode;
            */
        }

    }
}
