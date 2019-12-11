package application;

import java.util.HashSet;
import java.util.Set;

import javafx.scene.shape.Sphere;

public class Vertex extends Sphere {
	private String name;
	private int x;
	private int y;
	private int z;
	private Set<String> connectedNodes=new HashSet<String>(); 

	public Vertex() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Vertex(double arg0, int arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public Vertex(double arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public Vertex(double arg0, String name, int x, int y, int z) {
		super(arg0);
		this.name = name;
		this.x = x;
		this.y = y;
		this.z = z;
		this.connectedNodes.add(name);
		// TODO Auto-generated constructor stub
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getZ() {
		return z;
	}

	public void setZ(int z) {
		this.z = z;
	}

	public Set<String> getConnectedNodes() {
		return connectedNodes;
	}

	public void setConnectedNodes(Set<String> connectedNodes) {
		this.connectedNodes = connectedNodes;
	}

	public static Vertex gererateNode(int x, int y, int z, String name) {
		double radious = 4.0;
		Vertex node = new Vertex(radious, name, x, y, z);
		node.setTranslateX(x);
		node.setTranslateY(y);
		node.setTranslateZ(z);
		return node;
	}

	@Override
	public String toString() {
		return "Wêze³ " + name+" ";
	}

	
}
