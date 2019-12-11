package application;

import javafx.geometry.Point3D;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.util.Pair;

public class LineOn extends Cylinder{
private Vertex fromNode;
private Vertex toNode;
private int lenght;
private int angle;
private int x;
private int y;
private int z;

public LineOn(Vertex fromNode, Vertex toNode, int lenght, int angle, int x, int y, int z) {
	super();
	this.fromNode = fromNode;
	this.toNode = toNode;
	this.lenght = lenght;
	this.angle = angle;
	this.x = x;
	this.y = y;
	this.z = z;
}



public LineOn() {
	super();
	// TODO Auto-generated constructor stub
}



public LineOn(double radius, double height, int divisions) {
	super(radius, height, divisions);
	// TODO Auto-generated constructor stub
}



public LineOn(double radius, double height) {
	super(radius, height);
	// TODO Auto-generated constructor stub
}



public Vertex getFromNode() {
	return fromNode;
}

public void setFromNode(Vertex fromNode) {
	this.fromNode = fromNode;
}

public Vertex getToNode() {
	return toNode;
}

public void setToNode(Vertex toNode) {
	this.toNode = toNode;
}

public int getLenght() {
	return lenght;
}

public void setLenght(int lenght) {
	this.lenght = lenght;
}

public int getAngle() {
	return angle;
}

public void setAngle(int angle) {
	this.angle = angle;
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

static int pow(int x)
{
	return x*x;
}

/*public static LineOn genereLine(NodeOn fromNode, NodeOn toNode)
{
	double radious=0.5;
	Point3D point=new Point3D((fromNode.getX()+toNode.getX())/2, (fromNode.getY()+toNode.getY())/2, (fromNode.getZ()+toNode.getZ())/2);
	int lenght=(int)Math.sqrt(pow(fromNode.getX()-toNode.getX())+pow(fromNode.getY()-toNode.getY())+pow(fromNode.getZ()-toNode.getZ()));
	int denominator=(int)(fromNode.getX()*toNode.getX()+fromNode.getY()*toNode.getY()+fromNode.getZ()*toNode.getZ());
	int counter=(int)(Math.abs(Math.sqrt(pow(fromNode.getX())+pow(fromNode.getY())+pow(fromNode.getZ()))*Math.sqrt(pow(toNode.getX())+pow(toNode.getY())+pow(toNode.getZ()))));
	int angle=(int)Math.acos(counter/denominator);
	LineOn cylinder = new LineOn(radious, lenght);
    cylinder.setTranslateX(point.getX());
    cylinder.setTranslateY(point.getY());
    cylinder.setTranslateZ(point.getZ());
    cylinder.setRotationAxis(point);
    cylinder.setRotate(angle);
    return cylinder;
	}

}*/

public static LineOn createConnection(Vertex originVertex, Vertex targetVertex) {
	Point3D origin=new Point3D(originVertex.getX(), originVertex.getY(), originVertex.getZ());
	Point3D target=new Point3D(targetVertex.getX(), targetVertex.getY(), targetVertex.getZ());
    Point3D yAxis = new Point3D(0, 1, 0);
    Point3D diff = target.subtract(origin);
    double height = diff.magnitude();

    Point3D mid = target.midpoint(origin);
    Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

    Point3D axisOfRotation = diff.crossProduct(yAxis);
    double angle = Math.acos(diff.normalize().dotProduct(yAxis));
    Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

    LineOn line = new LineOn(0.3, height);

    //line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);
    line.setTranslateX(mid.getX());
    line.setTranslateY(mid.getY());
    line.setTranslateZ(mid.getZ());
    line.getTransforms().addAll(rotateAroundCenter);
    line.setFromNode(originVertex);
    line.setToNode(targetVertex);
    return line;
}



@Override
public String toString() {
	return "wêze³ "+fromNode+ " to "+toNode+" \n";
}






}