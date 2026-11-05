package project;

import edgeWeightedSymbolGraph.EdgeWeightedSymbolGraph;
import edu.princeton.cs.algs4.DijkstraUndirectedSP;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.EdgeWeightedGraph;
import edu.princeton.cs.algs4.Stack;

/**
 * Class to find the fastest path from the selected origin to the selected Destination
 * @author Cameron Dickinson + Guillermo Nasif
 */
public class FindFastestPath {
	private String destination;
	private MapConstructor map;
	private Student user;
	
	public FindFastestPath(String destination, MapConstructor map, Student user) {
		this.destination = destination;
		this.map = map;
		this.user = user;
	}
	
	/**
	 * Finds the fastest path by minutes from current user location to 
	 * input destination.
	 * 
	 */
	public void find() {

		 	EdgeWeightedSymbolGraph sg = map.getCampusMapTime();
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
	        CampusMapGUI.showMessage("Fastest path from " + user.getLocation() + " to " + destination);

	        double minutes = sp.distTo(end);
	        CampusMapGUI.showInfo("Fastest path: " + minutes + " minutes");

	        // Draw the red path
	        CampusMapGUI.highlightPath(path, sg);

	        // Buttons
	        CampusMapGUI.drawButton(25, 10, 15, 4, "New Start");
	        CampusMapGUI.drawButton(75, 10, 15, 4, "New Destination");

	        while (true) {
	            if (CampusMapGUI.buttonClicked(25, 10, 15, 4)) { // new start button clicked
	                CampusMapGUI.showMessage("Click new starting building");
	                user.setLocation(CampusMapGUI.waitForBuildingClick()); //setting new start location
	                return;
	            }
	            if (CampusMapGUI.buttonClicked(75, 10, 15, 4)) { //new destination clicked
	                CampusMapGUI.showMessage("Click new destination");
	                String newDest = CampusMapGUI.waitForBuildingClick();
	                new FindFastestPath(newDest, map, user).find(); 
	                return;
	            }
	        }
	}
}
