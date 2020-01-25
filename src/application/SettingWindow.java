package application;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import com.sun.jndi.cosnaming.CNNameParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SettingWindow {

	static Generator graph = new Generator();

	public static void showSettingWindow() {
		final Group root = new Group();
		root.setId("roocik");
		
		Stage stage = new Stage();
		stage.setTitle("Paramety grafu");

		Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|1)?");

		UnaryOperator<TextFormatter.Change> filter = c -> {
			String text = c.getControlNewText();
			if (validEditingState.matcher(text).matches()) {
				return c;
			} else {
				return null;
			}
		};

		StringConverter<Integer> converterInt = new StringConverter<Integer>() {

			@Override
			public Integer fromString(String s) {
				if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
					return 0;
				} else {
					return Integer.valueOf(s);
				}
			}

			@Override
			public String toString(Integer d) {
				return d.toString();
			}
		};

		TextFormatter<String> textFormatter = new TextFormatter<>(filter);
		TextFormatter<String> textFormatter2 = new TextFormatter<>(filter);
		TextFormatter<String> textFormatter3 = new TextFormatter<>(filter);
		TextFormatter<String> textFormatter4 = new TextFormatter<>(filter);
		TextFormatter<String> textFormatter5 = new TextFormatter<>(filter);
		TextFormatter<String> textFormatter6 = new TextFormatter<>(filter);
		TextFormatter<String> textFormatter7 = new TextFormatter<>(filter);

		HBox wholeContent = new HBox(10);
		wholeContent.setTranslateY(10);
		VBox namesOfFields = new VBox(40);
		VBox valuesOfFieldsMin = new VBox(30);
		VBox valuesOfFieldsMax = new VBox(38);

		wholeContent.getChildren().addAll(namesOfFields, valuesOfFieldsMin, valuesOfFieldsMax);

		// LEWA KOLUMNA ssda
		Label textNumberOfNodes = new Label("Podaj iloœæ wêz³ów:");
		Label textMaxNumberOfConections = new Label("Podaj zakres iloœci po³¹czeñ:");
		Label textRangeOfValue = new Label("Podaj zakres wagi po³¹czenia:");
		Label textTypeOfGraph = new Label("Wybierz typ grafu:");
		Button buttonGenerateTable = new Button("Generuj Graf");
		Button buttonGeneratePath = new Button("Generuj Œcie¿kê");

		namesOfFields.getChildren().addAll(textNumberOfNodes, textMaxNumberOfConections, textRangeOfValue,
				textTypeOfGraph, buttonGenerateTable);
		// ŒRODKOWA KOLUMNA
		TextField tfNumberOfNodes = new TextField();
		tfNumberOfNodes.setTextFormatter(textFormatter);
		TextField tfMinNumberOfConections = new TextField();
		tfMinNumberOfConections.setTextFormatter(textFormatter2);
		TextField tfMinValue = new TextField();
		tfMinValue.setTextFormatter(textFormatter3);
		ComboBox<String> cbNameOfStartedNode = new ComboBox<String>();
		cbNameOfStartedNode.setTranslateY(25);
		ComboBox<String> cbNameOfEndedNode = new ComboBox<String>();
		cbNameOfEndedNode.setTranslateY(25);

		ComboBox<String> cbTypeOfGraph = new ComboBox<String>(FXCollections.observableArrayList("Graf Nieskierowany",
				"Graf Skierowany", "Nieskierowany - Ró¿ne wagi"));
		cbTypeOfGraph.setValue("Graf Nieskierowany");

		/*
		 * tfMaxNumberOfConections.setTextFormatter(intTextFormater);
		 * tfMinValue.setTextFormatter(intTextFormater);
		 * tfNameOfStartedNode.setTextFormatter(intTextFormater);
		 * tfMaxNumberOfConections.setTextFormatter(intTextFormater);
		 */

		valuesOfFieldsMin.getChildren().addAll(tfNumberOfNodes, tfMinNumberOfConections, tfMinValue, cbTypeOfGraph);

		// PRAWA KOLUMNA
		Label null1 = new Label();
		null1.setVisible(false);
		TextField tfMaxNumberOfConections = new TextField();
		tfMaxNumberOfConections.setLayoutY(tfMinNumberOfConections.getLayoutY());
		tfMaxNumberOfConections.setTextFormatter(textFormatter6);
		TextField tfMaxValue = new TextField();
		tfMaxValue.setTranslateY(-8);
		tfMaxValue.setTextFormatter(textFormatter7);
		tfMaxValue.setLayoutY(tfMinValue.getLayoutY());
		// tfMaxValue.setTextFormatter(intTextFormater);
		valuesOfFieldsMax.getChildren().addAll(null1, tfMaxNumberOfConections, tfMaxValue);

		root.getChildren().add(wholeContent);
		Scene scene=new Scene(root, 500, 500);

		scene.getStylesheets().add("application/application.css");
		stage.setScene(scene);
		stage.show();

		buttonGenerateTable.setOnMouseClicked((MouseEvent e) -> { // Po kliknieciu wykonaj
			System.out.println("clicccck");
			if (tfNumberOfNodes.getText().equals("") || tfMinNumberOfConections.getText().equals("")
					|| tfMinValue.getText().equals("") || tfMaxNumberOfConections.getText().equals("")
					|| tfMaxValue.getText().equals("")) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Puste Pole");
				alert.setHeaderText("Puste Pole");
				alert.setContentText("Uzupe³nij wszystkie pola!");
				alert.showAndWait();
			} else {
				System.out.println("Wszystkie Pola s¹ pe³ne ");
				if (Integer.parseInt(tfMinNumberOfConections.getText().toString()) < Integer
						.parseInt(tfMaxNumberOfConections.getText().toString())
						&& Integer.parseInt(tfNumberOfNodes.getText().toString()) > 0
						&& Integer.parseInt(tfMaxValue.getText().toString()) < 100
						&& Integer.parseInt(tfMinValue.getText().toString()) > 0
						&& Integer.parseInt(tfMaxValue.getText().toString()) > 0
						&& Integer.parseInt(tfMinNumberOfConections.getText().toString()) >0
						&& Integer.parseInt(tfMaxNumberOfConections.getText().toString()) >0
						&& Integer.parseInt(tfNumberOfNodes.getText().toString()) > Integer.parseInt(tfMaxNumberOfConections.getText().toString()) )
				// Mo¿e byæ >= 0 bo Dijkstra dzia³a dla nieujemnych
				{
					graph.startWhole(Integer.parseInt(tfNumberOfNodes.getText()),
							Integer.parseInt(tfMinNumberOfConections.getText()),
							Integer.parseInt(tfMaxNumberOfConections.getText()), Integer.parseInt(tfMinValue.getText()),
							Integer.parseInt(tfMaxValue.getText()), cbTypeOfGraph.getValue()); // ILOŒÆ WEZLOW, MIN
																								// POLACZEN, MAX
																								// POLACZEN,
																								// MIN WAGA, MAX WAGA,
																								// TYP
																								// GRAPHU
					ObservableList<String> listNode = FXCollections.observableArrayList();

					for (int i = 0; i < Integer.parseInt(tfNumberOfNodes.getText()); i++) {
						listNode.add(String.valueOf(i));
					}

					Label null2 = new Label();
					null2.setVisible(false);

					cbNameOfStartedNode.setItems(listNode);
					cbNameOfStartedNode.setValue("0");

					cbNameOfEndedNode.setValue("1");
					cbNameOfEndedNode.setItems(listNode);
					valuesOfFieldsMin.getChildren().addAll(null2, cbNameOfStartedNode, cbNameOfEndedNode);
					Label textNameOfStartedNode = new Label("Wybierz wêze³ pocz¹tkowy:");
					Label textNameOfEndedNode = new Label("Wybierz wêze³ koñcowy:");
					namesOfFields.getChildren().addAll(textNameOfStartedNode, textNameOfEndedNode);
					buttonGenerateTable.setVisible(false);
					tfMaxNumberOfConections.setEditable(false);
					tfMaxValue.setEditable(false);
					tfMinNumberOfConections.setEditable(false);
					tfMinValue.setEditable(false);
					tfNumberOfNodes.setEditable(false);
					cbTypeOfGraph.setDisable(false);

					namesOfFields.getChildren().add(buttonGeneratePath);
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("B³¹d");
					alert.setHeaderText("Wyst¹pi³ b³¹d!");
					alert.setContentText("Upewnij siê, ¿e: \n"
							+ "Pola nie posiadaj¹ wartoœci ujemnych i zerowych, \n"
							+ "Wartoœci pól z oznaczniem 'max' posiadaj¹ wartoœci wiêksze ni¿ pola 'min' \n"
							+ "Maxymalna iloœæ po³¹czeñ jest mniejsza ni¿ iloœæ wêz³ów \n"
							+ "");

					alert.showAndWait();
				}
			}
		});

		buttonGeneratePath.setOnMouseClicked((MouseEvent e) -> { // Po kliknieciu wykonaj

			System.out.println("Generuje Œcie¿kê");

			graph.path(Integer.parseInt(cbNameOfStartedNode.getValue()),
					Integer.parseInt(cbNameOfEndedNode.getValue()));

		});
	}
}
