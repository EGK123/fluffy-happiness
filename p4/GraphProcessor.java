/////////////////////////////////////////////////////////////////////////////
// Semester: CS400 Spring 2018
// PROJECT: X-Team Exercise #4, Dictionary Graph
// FILES: Graph.java
// GraphTest.java
// GraphProcessor.java
// GraphProcessorTest.java
// WordProcessor.java
//
// Authors: Zach Kremer, Ege Kula, Patrick Lacina, Nathan Kolbow, Jong Kim
// Due date: 10:00 PM on Monday, April 16th
// Outside sources: None
//
// Instructor: Deb Deppeler (deppeler@cs.wisc.edu)
// Bugs: No known bugs
//
//////////////////////////// 80 columns wide //////////////////////////////////

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class adds additional functionality to the graph as a whole.
 * 
 * Contains an instance variable, {@link #graph}, which stores information for all the vertices and
 * edges.
 * 
 * @see #populateGraph(String) - loads a dictionary of words as vertices in the graph. - finds
 *      possible edges between all pairs of vertices and adds these edges in the graph. - returns
 *      number of vertices added as Integer. - every call to this method will add to the existing
 *      graph. - this method needs to be invoked first for other methods on shortest path
 *      computation to work.
 * @see #shortestPathPrecomputation() - applies a shortest path algorithm to precompute data
 *      structures (that store shortest path data) - the shortest path data structures are used
 *      later to to quickly find the shortest path and distance between two vertices. - this method
 *      is called after any call to populateGraph. - It is not called again unless new graph
 *      information is added via populateGraph().
 * @see #getShortestPath(String, String) - returns a list of vertices that constitute the shortest
 *      path between two given vertices, computed using the precomputed data structures computed as
 *      part of {@link #shortestPathPrecomputation()}. - {@link #shortestPathPrecomputation()} must
 *      have been invoked once before invoking this method.
 * @see #getShortestDistance(String, String) - returns distance (number of edges) as an Integer for
 *      the shortest path between two given vertices - this is computed using the precomputed data
 *      structures computed as part of {@link #shortestPathPrecomputation()}. -
 *      {@link #shortestPathPrecomputation()} must have been invoked once before invoking this
 *      method.
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class GraphProcessor {

    ArrayList<ArrayList<String>> paths;

    /**
     * Class that implements Dijkstra's Shortest Path algorithm and contains all data structures
     * that it requires. This class will not implement a default constructor, for Dijkstra's path
     * requiores that a source node be provided before it can perform any computations.
     * 
     * @author Zach
     *
     */
    class DijkstraPath {
        /**
         * Helper class to store data regarding the key and the value of a pair
         * 
         * @author Zach
         *
         * @param <WEIGHT> comparable object to identify an item with
         * @param <VERTEX> data or name associated with vertex
         */
        protected class Pair<WEIGHT, VERTEX> extends Object
                        implements Comparable<Pair<WEIGHT, VERTEX>> {

            private WEIGHT weight; // unique ID
            private VERTEX vertex;

            public Pair(WEIGHT w, VERTEX v) {
                this.weight = w;
                this.vertex = v;
            }

            public WEIGHT getWeight() {
                return weight;
            }

            public void setWeight(WEIGHT w) {
                this.weight = w;
            }

            public VERTEX getVertex() {
                return vertex;
            }

            public void setVertex(VERTEX v) {
                this.vertex = v;
            }

            public int compareTo(Pair<WEIGHT, VERTEX> o) {
                int thisWeight = (int) this.weight;
                int otherWeight = (int) o.weight;
                if (thisWeight < otherWeight) {
                    return -1;
                } else if (thisWeight > otherWeight) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }

        ArrayList<String> vertexList; // will store the nodes, its index should be parralel to all
                                      // other arrays
        ArrayList<Boolean> visited; // boolean determining if a node has been visited
        ArrayList<Integer> weight; // current weight to the node
        ArrayList<String> predecessor; // node that precedes this node in route to the shortest path
                                       // back to the source node
        PriorityQueue<Pair<Integer, String>> pq; // priority queue that will store pairs of weights
                                                 // and nodes
        String start; // source node
        String last; // strictly used for data output

        /**
         * Main constructor to form perform Dijkstra's Shortest Path on a source need. The
         * 
         * @param startVertex
         */
        public DijkstraPath(String startVertex) {
            int vertexIndex = 0;
            start = startVertex;

            // set up brand new data structs
            vertexList = new ArrayList<String>();
            visited = new ArrayList<Boolean>();
            weight = new ArrayList<Integer>();
            predecessor = new ArrayList<String>();

            // populate data structures
            for (String v : graph.getAllVertices()) {
                vertexList.add(v);
                visited.add(false);

                if (v.equals(startVertex)) {
                    weight.add(0);
                    vertexIndex = vertexList.size() - 1;
                } else {
                    weight.add(Integer.MAX_VALUE);
                }

                predecessor.add(null);
            }

            pq = new PriorityQueue<Pair<Integer, String>>();

            // prime le pump
            pq.add(new Pair<Integer, String>(0, vertexList.get(vertexIndex)));
        }

        /**
         * This is the meat of the algorithm, requirees that the data structures have been set up
         * properly and a source node was provided in the constructor. Method will compute shortest
         * paths from the source node to all other reachable nodes within the graph.
         * 
         * @return returns an arraylist of strings representing all shortest paths from source node
         *         to every other node in the graph (including itself)
         */
        public ArrayList<String> computePaths() {
            ArrayList<String> paths = new ArrayList<String>();

            // main while loop
            while (!pq.isEmpty()) {
                Pair<Integer, String> current = pq.remove();
                int currentIndex = 0;
                while (!current.getVertex().equals(vertexList.get(currentIndex))) {
                    currentIndex++; // find vertex in parralel arrays
                }

                visited.set(currentIndex, true); // current node has now been visited

                // get reachable nodes that neighbor the current node
                Iterable<String> neighbors = graph.getNeighbors(vertexList.get(currentIndex));

                for (String s : neighbors) {
                    int succIndex = 0;
                    while (!vertexList.get(succIndex).equals(s)) {
                        succIndex++; // find s in list
                    }

                    // for each unvisited successor
                    if (!visited.get(succIndex)) {
                        // + 1 is because this graph is unweighted (i.e. each edge has exactly a
                        // weight of 1)
                        int possibleDistance = current.getWeight() + 1;
                        if (possibleDistance < weight.get(succIndex)) {

                            // vvvv // this code will remove any instance of duplicate data
                            LinkedList<Pair<Integer, String>> arbList =
                                            new LinkedList<Pair<Integer, String>>();
                            arbList.add(new Pair<Integer, String>(weight.get(succIndex),
                                            vertexList.get(succIndex)));

                            pq.removeAll(arbList);

                            // ^^^^ // potentially not needed, seems to work without it

                            weight.set(succIndex, possibleDistance); // set new weight/dist for the
                                                                     // successor

                            predecessor.set(succIndex, current.getVertex()); // current node becomes
                                                                             // successor node's
                                                                             // predecessor

                            pq.add(new Pair<Integer, String>(weight.get(succIndex),
                                            vertexList.get(succIndex))); // add succ / new weight to
                                                                         // PQ
                        }
                    }
                }
            }

            for (String v : vertexList) {
                last = v;
                paths.add(getPath(v)); // add all paths to arraylist
            }

            return paths;
        }

        /**
         * Class that recursively follows predecessor nodes until it creates a string representing
         * the shortest path from (source node->final node)
         * 
         * @param vertex starts with the end vertex and slowly works backwards via the predecessor
         *        list until it hits the source node
         * @return returns the string represent the shortest path from src->end
         */
        private String getPath(String vertex) {
            String path = "";
            int vertexIndex = 0;
            while (!vertex.equals(vertexList.get(vertexIndex))) {
                vertexIndex++; // find node in list
            }

            String pred = predecessor.get(vertexIndex);

            if (pred != null) {
                path += getPath(pred); // recursive call
            } else {
                return ("(" + vertex + "->" + last + "): " + vertex);
            }

            path += "," + vertex; // add new vertex
            return path;
        }
    }

    /**
     * Graph which stores the dictionary words and their associated connections
     */
    private GraphADT<String> graph;

    /**
     * Constructor for this class. Initializes instances variables to set the starting state of the
     * object
     */
    public GraphProcessor() {
        this.graph = new Graph<>();
    }

    /**
     * Builds a graph from the words in a file. Populate an internal graph, by adding words from the
     * dictionary as vertices and finding and adding the corresponding connections (edges) between
     * existing words.
     * 
     * Reads a word from the file and adds it as a vertex to a graph. Repeat for all words.
     * 
     * For all possible pairs of vertices, finds if the pair of vertices is adjacent
     * {@link WordProcessor#isAdjacent(String, String)} If a pair is adjacent, adds an undirected
     * and unweighted edge between the pair of vertices in the graph.
     * 
     * @param filepath file path to the dictionary
     * @return Integer the number of vertices (words) added
     */
    public Integer populateGraph(String filepath) {
        try {
            Stream<String> wordStream = WordProcessor.getWordStream(filepath);
            List<String> listOfWords = wordStream.collect(Collectors.toList());
            // adds words to graph
            for (String word : listOfWords) {
                graph.addVertex(word);
            }
            // checks if words should be adjacent to one another
            for (int i = 0; i < listOfWords.size() - 1; i++) {
                for (int j = i + 1; j < listOfWords.size(); j++) {
                    if (WordProcessor.isAdjacent(listOfWords.get(i), listOfWords.get(j))) {
                        graph.addEdge(listOfWords.get(i), listOfWords.get(j));
                    }
                }
            }
            return listOfWords.size();
        } catch (IOException e) {
            System.out.println("Incorrect filepath.");
            // e.printStackTrace();
            return 0;
        }
    }

    /**
     * Gets the list of words that create the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit shortest path between cat and wheat
     * is the following list of words: [cat, hat, heat, wheat]
     * 
     * @param word1 first word
     * @param word2 second word
     * @return List<String> list of the words
     * @throws NoSuchElementException
     */
    public List<String> getShortestPath(String word1, String word2) throws NoSuchElementException {
        int i = 0; // index of first word
        int j = 0; // index of second word

        List<String> l = new ArrayList<String>();

        while (!paths.get(i).get(0).contains(word1 + "->")) {
            i++; // find first word

            if (i >= paths.size()) {
                throw new NoSuchElementException(
                                "ERROR: Word \"" + word1 + "\" does not exist within the graph");
            }
        }

        while (!paths.get(i).get(j).contains("->" + word2)) {
            j++; // find second word

            if (j >= paths.get(i).size()) {
                throw new NoSuchElementException(
                                "ERROR: Word \"" + word2 + "\" does not exist within the graph");
            }
        }

        String generalTokens[] = paths.get(i).get(j).split(": ");
        String pathTokens[] = generalTokens[1].split(",");

        for (String s : pathTokens) {
            l.add(s);
        }

        return l;
    }

    /**
     * Gets the distance of the shortest path between word1 and word2
     * 
     * Example: Given a dictionary, cat rat hat neat wheat kit distance of the shortest path between
     * cat and wheat, [cat, hat, heat, wheat] = 3 (the number of edges in the shortest path)
     * 
     * @param word1 first word
     * @param word2 second word
     * @return Integer distance, or -1 if there was an error
     */
    public Integer getShortestDistance(String word1, String word2) {
        try {
            return getShortestPath(word1, word2).size() - 1;
        } catch (NoSuchElementException e) {
            return -1;
        }
    }

    /**
     * Computes shortest paths and distances between all possible pairs of vertices. This method is
     * called after every set of updates in the graph to recompute the path information. Any
     * shortest path algorithm can be used (Djikstra's or Floyd-Warshall recommended).
     */
    public void shortestPathPrecomputation() {
        paths = new ArrayList<ArrayList<String>>();

        for (String s : graph.getAllVertices()) {
            DijkstraPath d = new DijkstraPath(s);
            paths.add(d.computePaths());
        }
    }

    public static void main(String[] args) {
        // nothing right now
    }
}
