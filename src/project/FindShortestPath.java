package project;

import edgeWeightedSymbolGraph.EdgeWeightedSymbolGraph;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Stack;

/**
 * Class that helps find the shortest path from the current location to the destination, in feet
 * @author Cameron Dickinson + Guillermo Nasif
 */
public class FindShortestPath {
	private String destination;
	private MapConstructor map;
	private Student user;

	public FindShortestPath(String destination, MapConstructor map, Student user) {
		this.destination = destination;
		this.map = map;
		this.user = user;
	}
	
	/**
	 * Finds the shortest path by feet from current user location to 
	 * input destination.
	 */
	public void find() {

		 	EdgeWeightedSymbolGraph sg = map.getCampusMapDistance();
	        EdgeWeightedGraph g = sg.graph();

	        int start = sg.indexOf(user.getLocation());
	        int end = sg.indexOf(destination);

	        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(g, start); 
	        Iterable<Edge> path = sp.pathTo(end);
	        
	        //using stacks to store sequence of edges returned by dijkstra
	        Stack<String> pathSteps = new Stack<>();
	        for (Edge e : path) {
	            String step = sg.nameOf(e.either()) + " → " +
	                          sg.nameOf(e.other(e.either()));
	            pathSteps.push(step);
	        }

	        // Draw map + messages
	        CampusMapGUI.drawMap(sg);
	        CampusMapGUI.showMessage("Shortest path from " + user.getLocation() + " to " + destination);

	        double feet = sp.distTo(end);
	        CampusMapGUI.showInfo("Shortest path: " + (int) feet + " feet");

	        // Draw path last so buttons don’t cover it
	        CampusMapGUI.highlightPath(path, sg);

	        // Buttons
	        CampusMapGUI.drawButton(25, 10, 15, 4, "New Start");
	        CampusMapGUI.drawButton(75, 10, 15, 4, "New Destination");

	        // Wait for button clicks
	        while (true) {
	            if (CampusMapGUI.buttonClicked(25, 10, 15, 4)) {
	                CampusMapGUI.showMessage("Click new starting building");
	                user.setLocation(CampusMapGUI.waitForBuildingClick());
	                return;
	            }
	            if (CampusMapGUI.buttonClicked(75, 10, 15, 4)) {
	                CampusMapGUI.showMessage("Click new destination");
	                String newDest = CampusMapGUI.waitForBuildingClick();
	                new FindShortestPath(newDest, map, user).find();
	                return;
	            }
	    }
	}
	
}
