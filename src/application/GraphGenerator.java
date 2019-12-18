package application;

import java.util.List;

public class Graph {
	private List<Vertex> vertexes;
	private List<Edge> edges;
	
	public Graph(List<Vertex> nodesInTheGraph, List<Edge> connectionsInTheGraph) {
		super();
		this.vertexes = nodesInTheGraph;
		this.edges = connectionsInTheGraph;
	}

	public List<Vertex> getVertexes() {
		return vertexes;
	}

	public void setNodesInTheGraph(List<Vertex> nodesInTheGraph) {
		this.vertexes = nodesInTheGraph;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setConnectionsInTheGraph(List<Edge> connectionsInTheGraph) {
		this.edges = connectionsInTheGraph;
	}
	
	
}
