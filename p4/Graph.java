import java.util.ArrayList;

/**
 * Undirected and unweighted graph implementation
 * 
 * @param <E> type of a vertex
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * 
 */
public class Graph<E> implements GraphADT<E> {
    
    /**
     * Instance variables and constructors
     */
	
	private boolean[][] am;
	private ArrayList<E> vertices = new ArrayList<E>();
	
	public Graph() {
		
	}
	
	private void incrementAM() {
		if(am == null) {
			am = new boolean[1][1];
		} else {
			boolean[][] temp = new boolean[am.length + 1][am.length + 1];
			for(int y = 0; y < am.length; y++) {
				for(int x = 0; x < am.length; x++) {
					temp[y][x] = am[y][x];
				}
			}
			am = temp;
		}
	}
	
	private void decrementAM(int _remove) {
		boolean[][] temp = new boolean[am.length - 1][am.length - 1];
		for(int y = 0; y < am.length - 1; y++) {
			if(y == _remove)
				continue;
			for(int x = 0; x < am.length - 1; x++) {
				if(x == _remove)
					continue;
				temp[y][x] = am[(y > _remove) ? y - 1 : y][(x > _remove) ? x - 1 : x];
			}
		}
	}
	
    /**
     * {@inheritDoc}
     */
    @Override
    public E addVertex(E vertex) {
        if(vertices.contains(vertex) || vertex == null)
        	return null;
        
        incrementAM();
        vertices.add(vertex);
        return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public E removeVertex(E vertex) {
    	if(!vertices.contains(vertex))
    		return null;
    	
        int _vertex = vertices.indexOf(vertex);
    	decrementAM(_vertex);
    	vertices.remove(vertex);
    	return vertex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addEdge(E vertex1, E vertex2) {
    	if(vertex1 == vertex2)
    		return false;
    	
        int _vertex1 = vertices.indexOf(vertex1);
        int _vertex2 = vertices.indexOf(vertex2);
        if(_vertex1 < 0 || _vertex2 < 0)
        	return false;
        
        am[_vertex1][_vertex2] = true;
        am[_vertex2][_vertex1] = true;
        return true;
    }    

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeEdge(E vertex1, E vertex2) {
    	if(vertex1 == vertex2)
    		return false;
    	
        int _vertex1 = vertices.indexOf(vertex1);
        int _vertex2 = vertices.indexOf(vertex2);
        if(_vertex1 < 0 || _vertex2 < 0)
        	return false;
        
        am[_vertex1][_vertex2] = false;
        am[_vertex2][_vertex1] = false;
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAdjacent(E vertex1, E vertex2) {
        int _vertex1 = vertices.indexOf(vertex1);
        int _vertex2 = vertices.indexOf(vertex2);
        
        if(_vertex1 == -1 || _vertex2 == -1)
        	return false;
        
        return am[_vertex1][_vertex2];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getNeighbors(E vertex) {
        ArrayList<E> out = new ArrayList<E>();
        int _vertex = vertices.indexOf(vertex);
        for(int i = 0; i < am.length; i++) {
        	if(i == _vertex)
        		continue;
        	if(am[_vertex][i])
        		out.add(vertices.get(i));
        }
        return out;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Iterable<E> getAllVertices() {
        return vertices;
    }

}
