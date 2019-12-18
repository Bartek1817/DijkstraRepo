package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.TypeOfGraph;

public class Generator {

	final Group root = new Group();
	final Group axisGroup = new Group();
	final Xform world = new Xform();
	final PerspectiveCamera camera = new PerspectiveCamera(true);
	final Xform cameraXform = new Xform();
	final Xform cameraXform2 = new Xform();
	final Xform cameraXform3 = new Xform();
	final double cameraDistance = 450;
	final Xform moleculeGroup = new Xform();
	private Timeline timeline;
	boolean timelinePlaying = false;
	double ONE_FRAME = 1.0 / 24.0;
	double DELTA_MULTIPLIER = 200.0;
	double CONTROL_MULTIPLIER = 0.1;
	double SHIFT_MULTIPLIER = 0.1;
	double ALT_MULTIPLIER = 0.5;
	double mousePosX;
	double mousePosY;
	double mouseOldX;
	double mouseOldY;
	double mouseDeltaX;
	double mouseDeltaY;
	Xform moleculeXform = new Xform();
	Xform oxygenXform = new Xform();
	final PhongMaterial redMaterial = new PhongMaterial();
	final PhongMaterial whiteMaterial = new PhongMaterial();
	final PhongMaterial greyMaterial = new PhongMaterial();
	final PhongMaterial blueMaterial = new PhongMaterial();
	final PhongMaterial blackMaterial = new PhongMaterial();
	final PhongMaterial greenMaterial = new PhongMaterial();
	// ^^^^^^^^^^^ ZMIENNE KTÓRE JU¯ BY£Y POWY¯EJ ^^^^^^^^^^^
	// vvvvvvvvvvv*W³ASNE ZMIENNE DEKLAROWANE PONI¯EJvvvvvvvvvv
	List<Vertex> vertexes = new ArrayList<Vertex>();
	List<Text> grapicalNodesNames = new ArrayList<Text>();// graficzne cyferki nad graficznym wezlem
	int numberOfNodesInTheGraph = 15;
	int minimalNumerOfConnection = 4;
	int maximalNumerOfConnetion = 6;
	int minimalValue = 4;
	int maximalValue = 6;
	TypeOfGraph typeOfGraph = TypeOfGraph.Normal ; // typ Grafu Normal, Directed, DifferentValues
	List<LineOn> lines = new ArrayList<LineOn>();
	List<Edge> edges = new ArrayList<Edge>();

	Button btnOpenTableWindow;

	// funckja do budowana SCENE
	private void buildScene() {
		root.getChildren().add(world);
	}

	// funkcja do generowania camery
	private void buildCamera() {
		root.getChildren().add(cameraXform);
		cameraXform.getChildren().add(cameraXform2);
		cameraXform2.getChildren().add(cameraXform3);
		cameraXform3.getChildren().add(camera);
		cameraXform3.setRotateZ(180.0);

		camera.setNearClip(0.1);
		camera.setFarClip(10000.0);
		camera.setTranslateZ(-cameraDistance);
		cameraXform.ry.setAngle(320.0);
		cameraXform.rx.setAngle(40);
	}

	// WY£¥CZONE - funkcja do generowania graficznych osi x, y i z
	private void buildAxes() {
		final Box xAxis = new Box(240.0, 1, 1);
		final Box yAxis = new Box(1, 240.0, 1);
		final Box zAxis = new Box(1, 1, 240.0);

		xAxis.setMaterial(redMaterial);
		yAxis.setMaterial(greenMaterial);
		zAxis.setMaterial(blueMaterial);

		// axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
		world.getChildren().addAll(axisGroup);
	}

	// funkcja do obs³ugi obracania myszk¹
	private void handleMouse(Scene scene, final Node root) {
		scene.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseOldX = me.getSceneX();
				mouseOldY = me.getSceneY();
			}
		});
		scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mouseOldX = mousePosX;
				mouseOldY = mousePosY;
				mousePosX = me.getSceneX();
				mousePosY = me.getSceneY();
				mouseDeltaX = (mousePosX - mouseOldX);
				mouseDeltaY = (mousePosY - mouseOldY);

				double modifier = 1.0;
				double modifierFactor = 0.1;

				if (me.isControlDown()) {
					modifier = 0.1;
				}
				if (me.isShiftDown()) {
					modifier = 10.0;
				}
				if (me.isPrimaryButtonDown()) {
					cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX * modifierFactor * modifier * 2.0); // +
					cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY * modifierFactor * modifier * 2.0); // -
				} else if (me.isSecondaryButtonDown()) {
					double z = camera.getTranslateZ();
					double newZ = z + mouseDeltaX * modifierFactor * modifier;
					camera.setTranslateZ(newZ);
				} else if (me.isMiddleButtonDown()) {
					cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX * modifierFactor * modifier * 0.3); // -
					cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY * modifierFactor * modifier * 0.3); // -
				}
			}
		});
	}

	// ^^^^^^^^^^^ FUNKCJE KTÓRE JU¯ BY£Y POWY¯EJ ^^^^^^^^^^^
	// vvvvvvvvvvv*W³ASNE FUNCKJE PONI¯EJvvvvvvvvvv

	// funkcja do dodawania tekstu nad wezlem
	private Text addNodeName(Vertex node) {
		Text text = new Text(node.getName());
		text.setStyle("-fx-font-size: 7pt;");
		text.setTranslateX(node.getX() + 5);
		text.setTranslateY(node.getY() + 10);
		text.setTranslateZ(node.getZ());
		text.setRotate(180);
		return text;
	}

	// funkcja do generowania graficznych wezlow
	private void generateVertexes() {
		for (int i = 0; i < numberOfNodesInTheGraph; i++) {
			vertexes.add(Vertex.gererateNode(Randomizer.generate(-100, 100), Randomizer.generate(-100, 100),
					Randomizer.generate(-100, 100), Integer.toString(i)));
		}
	}

	// funkcja do generowania graficznych numerow wezlow
	private void generateGraphicalTextOnTheNode() {
		for (int i = 0; i < numberOfNodesInTheGraph; i++) {
			grapicalNodesNames.add(addNodeName(vertexes.get(i)));
		}
	}

	// funkcja do generowania graficznych po³¹czeñ miêdzy wêz³ami
	private void generateGraphicalConnectionsBetweenNodes() {
		for (int i = 1; i <= vertexes.size(); i++) {// dla ka¿dego wezla
			for (int a = 0; a < minimalNumerOfConnection || a >= maximalNumerOfConnetion; a++) {// przechodzimy
																		// tyle
																		// razy
																		// ile
																		// zadeklarujemy

				int nodeNUmber = Randomizer.generate(0, vertexes.size() - 1);
				if ((i - 1 != nodeNUmber)
						&& !vertexes.get(i - 1).getConnectedNodes().contains(String.valueOf(nodeNUmber)) 
						&& vertexes.get(nodeNUmber).getConnectedNodes().size() <= maximalNumerOfConnetion
						&& vertexes.get(i-1).getConnectedNodes().size() <= maximalNumerOfConnetion) {

					int weightOfConnection = Randomizer.generate(minimalValue, maximalValue);
					LineOn conectionLine = LineOn.createConnection(vertexes.get(i - 1), vertexes.get(nodeNUmber));

					// tutaj modyfikowac skierowany/nieskierowany - albo to samo
					// losowanie wag dla obu, albo rozne w zale¿noœci od tego jak chcemy ¿eby
					// wygl¹da³ skierowany

					vertexes.get(i - 1).getConnectedNodes().add(Integer.toString(nodeNUmber));
					edges.add(new Edge(vertexes.get(i - 1), vertexes.get(nodeNUmber), weightOfConnection));

					if (typeOfGraph.equals(TypeOfGraph.Normal)) { // Graf Normalny
						edges.add(new Edge(vertexes.get(nodeNUmber), vertexes.get(i - 1), weightOfConnection));
						vertexes.get(nodeNUmber).getConnectedNodes().add(vertexes.get(i - 1).getName());
					}
					if (typeOfGraph.equals(TypeOfGraph.DifferentValues)) { // Graf z ró¿nymi wartoœciami
																			
						weightOfConnection = Randomizer.generate(1, 10);
						edges.add(new Edge(vertexes.get(nodeNUmber), vertexes.get(i - 1), weightOfConnection));
						vertexes.get(nodeNUmber).getConnectedNodes().add(vertexes.get(i - 1).getName());
					}

					lines.add(conectionLine);

				} else if (vertexes.get(i - 1).getConnectedNodes().size() <= minimalNumerOfConnection) {
					--a;
				}
			}
		}
	}

	// funkcja do ustawiania kolorow zadeklarowanych wczesniej materii
	private void setColorsOfTheMaterials() {
		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		whiteMaterial.setDiffuseColor(Color.WHITE);
		whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

		greyMaterial.setDiffuseColor(Color.DARKGREY);
		greyMaterial.setSpecularColor(Color.GREY);

		blueMaterial.setDiffuseColor(Color.BLUE);
		blueMaterial.setSpecularColor(Color.BLUE);

		blackMaterial.setDiffuseColor(Color.BLACK);
		blackMaterial.setSpecularColor(Color.BLACK);

		redMaterial.setDiffuseColor(Color.DARKRED);
		redMaterial.setSpecularColor(Color.RED);

		greenMaterial.setDiffuseColor(Color.DARKGREEN);
		greenMaterial.setSpecularColor(Color.GREEN);

		blueMaterial.setDiffuseColor(Color.DARKBLUE);
		blueMaterial.setSpecularColor(Color.BLUE);
	}

	// funkcja do tworzenia tabeli wag
	private TableView<String[]> initTableWithWeightsOfConnections() {
		// DEKLAROWANIE TABELI WAG:
		String[][] arrayOfConnectionsWeight;
		arrayOfConnectionsWeight = new String[numberOfNodesInTheGraph + 1][numberOfNodesInTheGraph + 1];
		// WYPE£NIANIE TABELI WAG DANYMI:
		for (int i = 0; i < vertexes.size(); i++) {
			arrayOfConnectionsWeight[0][i + 1] = Integer.toString(i);
			arrayOfConnectionsWeight[i + 1][0] = Integer.toString(i);
		}
		for (int i = 0; i < edges.size(); i++) {
			int from = Integer.parseInt(edges.get(i).getFromNode().getName()) + 1;
			int to = Integer.parseInt(edges.get(i).getToNode().getName()) + 1;
			String fromToWeight = Integer.toString(edges.get(i).getWeight());
			arrayOfConnectionsWeight[from][to] = fromToWeight;
		}
		// TWORZENIE TABELVIEW:
		final ObservableList<Edge> edgesAsObservableList = FXCollections.observableArrayList();
		edgesAsObservableList.addAll(edges);
		ObservableList<String[]> listOfCennectionsWeight = FXCollections.observableArrayList();
		listOfCennectionsWeight.addAll(Arrays.asList(arrayOfConnectionsWeight));
		listOfCennectionsWeight.remove(0);// remove titles from data
		TableView<String[]> tableOfConnectionsWeight = new TableView<>();
		for (int i = 0; i < arrayOfConnectionsWeight[0].length; i++) {
			TableColumn tableColumnAsNamesOfVertexes = new TableColumn(arrayOfConnectionsWeight[0][i]);
			final int columnNumber = i;
			tableColumnAsNamesOfVertexes
					.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
							return new SimpleStringProperty((p.getValue()[columnNumber]));
						}
					});
			tableColumnAsNamesOfVertexes.setPrefWidth(25);
			tableOfConnectionsWeight.getColumns().add(tableColumnAsNamesOfVertexes);
		}
		tableOfConnectionsWeight.setItems(listOfCennectionsWeight);
		// TWORZENIE I INICJACJA BUTTONA "TABELA":
		btnOpenTableWindow = new Button("Tabela");
		btnOpenTableWindow.setTranslateX(100);
		btnOpenTableWindow.resize(10, 5);
		btnOpenTableWindow.setRotate(180);
		btnOpenTableWindow.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				final Group root = new Group();
				root.getChildren().add(tableOfConnectionsWeight);
				Stage stage = new Stage();
				stage.setTitle("Tabela po³¹czeñ");
				stage.setScene(new Scene(root, 500, 500));
				stage.show();
			}
		});
		return tableOfConnectionsWeight;
	}

	// funkcja do tworzenia grafu i wywolywania funckji algorytmu
	private LinkedList<Vertex> findTheShortestPathBetween(int nameOfStartedNode, int nameOfEndedNode) {
		Graph graph1 = new Graph(vertexes, edges);
		Algorithm dijkstra = new Algorithm(graph1);
		dijkstra.execute(vertexes.get(nameOfStartedNode));
		LinkedList<Vertex> path = dijkstra.getPath(vertexes.get(nameOfEndedNode));
		System.out.println(dijkstra.getDistance().toString());
		return path;
	}

	// funkcja do zmiany koloru krawêdzi nale¿¹cych do najkrótszej scie¿ki w grafie
	private void fillTheEgdesOfTheShortesPath(LinkedList<Vertex> path) {
		ArrayList<String> listOfNodesInShortesPath = new ArrayList<String>();
		if (path == null) {
			System.out.println("Nie ma takiej œcie¿ki = Pasuje to obs³u¿yæ w GUI nie tylko komunikatem z consoli");
		} else {
			for (int i = 0; i < path.size(); i++) {
				listOfNodesInShortesPath.add(path.get(i).getName());
			}
			System.out.println(listOfNodesInShortesPath);
			for (LineOn i : lines) {
				for (int s = 1; s < listOfNodesInShortesPath.size(); s++) {
					if (i.getFromNode().getName().toString().equals(listOfNodesInShortesPath.get(s - 1))
							&& i.getToNode().getName().toString().equals(listOfNodesInShortesPath.get(s))) {
						i.setMaterial(blueMaterial);
					}
					if (i.getToNode().getName().toString().equals(listOfNodesInShortesPath.get(s - 1))
							&& i.getFromNode().getName().toString().equals(listOfNodesInShortesPath.get(s))) {
						i.setMaterial(blueMaterial);
					}
				}
			}
		}
	}

	// funkcja do dodawania wszystkiego do roota
	private void addChildrenToTheRoot() {
		moleculeXform.getChildren().add(oxygenXform);
		oxygenXform.getChildren().addAll(vertexes);
		oxygenXform.getChildren().addAll(lines);
		oxygenXform.getChildren().addAll(grapicalNodesNames);
		oxygenXform.getChildren().add(initTableWithWeightsOfConnections());
		oxygenXform.getChildren().add(btnOpenTableWindow);
		moleculeGroup.getChildren().add(moleculeXform);
		world.getChildren().addAll(moleculeGroup);
	}

	public void startm(int edges, int minConection, int maxConetion, int minValue, int maxValue ) {
		
		numberOfNodesInTheGraph = edges;
		minimalNumerOfConnection = minConection;
		maximalNumerOfConnetion = maxConetion;
		minimalValue = minValue;
		maximalValue = maxValue;
		typeOfGraph = TypeOfGraph.Normal ; // typ Grafu Normal, Directed, DifferentValues
		
		buildScene();
		buildCamera();
		setColorsOfTheMaterials();
		buildAxes();
		// po kolei
		generateVertexes();
		generateGraphicalTextOnTheNode();
		generateGraphicalConnectionsBetweenNodes();
		// WYŒWIETLANIE NAJKRÓTSZEJ SCIE¯KI W KONSOLI:
		System.out.println(findTheShortestPathBetween(0, 5));
		fillTheEgdesOfTheShortesPath(findTheShortestPathBetween(0, 5));

		addChildrenToTheRoot();


		
		Scene scene = new Scene(root, 1024, 768, true);
		scene.setFill(Color.DARKSLATEGRAY);
		handleMouse(scene, world);
		Stage primaryStage = new Stage();
		primaryStage.setScene(scene);
		primaryStage.setTitle("Dijkstra Path");
		primaryStage.setScene(scene);
		primaryStage.show();

		scene.setCamera(camera);

	}

}
