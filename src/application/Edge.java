package application;

public class Edge {
private Vertex source;
private Vertex destination;
int weight;

public Edge(Vertex fromNode, Vertex toNode, int weight) {
	super();
	this.source = fromNode;
	this.destination = toNode;
	this.weight = weight;
}

public Vertex getFromNode() {
	return source;
}

public void setFromNode(Vertex fromNode) {
	this.source = fromNode;
}

public Vertex getToNode() {
	return destination;
}

public void setToNode(Vertex toNode) {
	this.destination = toNode;
}

public int getWeight() {
	return weight;
}

public void setWeight(int weight) {
	this.weight = weight;
}

@Override
public String toString() {
	return "Connection [fromNode=" + source + ", toNode=" + destination + ", weight=" + weight + "]";
}



}
