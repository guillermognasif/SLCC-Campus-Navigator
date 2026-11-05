package project;

import edgeWeightedSymbolGraph.EdgeWeightedSymbolGraph;

/**
 * Creates distance based campus symbol graph and time based campus symbol graph using
 * a text file. 
 * 
 * @author Cameron Dickinson
 */
public class MapConstructor {
	private EdgeWeightedSymbolGraph campusMapDistance;
	private EdgeWeightedSymbolGraph campusMapTime;
	public MapConstructor(String filenameDist, String filenameTime) {
		
		campusMapDistance = new EdgeWeightedSymbolGraph(filenameDist, ",");
		campusMapTime = new EdgeWeightedSymbolGraph(filenameTime, ",");
	}
	public EdgeWeightedSymbolGraph getCampusMapDistance() {
		return campusMapDistance;
	}
	public EdgeWeightedSymbolGraph getCampusMapTime() {
		return campusMapTime;
	}
}
