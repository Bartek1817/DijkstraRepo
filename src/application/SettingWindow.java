package application;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class SettingWindow {

	public static void showSettingWindow()
	{
		final Group root = new Group();
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
		TextFormatter<String> textFormatter8 = new TextFormatter<>(filter);
		
		
	

		
		HBox wholeContent=new HBox(10);
		wholeContent.setTranslateY(10);
		VBox namesOfFields=new VBox(40);
		VBox valuesOfFieldsMin=new VBox(30);
		VBox valuesOfFieldsMax=new VBox(38);
		
		wholeContent.getChildren().addAll(namesOfFields, valuesOfFieldsMin, valuesOfFieldsMax);
		
		//LEWA KOLUMNA
		Label textNumberOfNodes=new Label("Podaj ilo�� w�z��w:");
		Label textMaxNumberOfConections=new Label("Podaj zakres ilo�ci po��cze�:");
		Label textRangeOfValue=new Label("Podaj zakres wagi po��czenia:");
		Label textNameOfStartedNode=new Label("Wybierz w�ze� pocz�tkowy:");
		Label textNameOfEndedNode=new Label("Wybierz w�ze� ko�cowy:");
		Label textTypeOfGraph=new Label("Wybierz typ grafu:");
		Button buttonGenerateTable=new Button("Generuj Graf");
		
		namesOfFields.getChildren().addAll(textNumberOfNodes, textMaxNumberOfConections, textRangeOfValue, textNameOfStartedNode, textNameOfEndedNode, textTypeOfGraph, buttonGenerateTable);
		//�RODKOWA KOLUMNA
		TextField tfNumberOfNodes=new TextField();
		tfNumberOfNodes.setTextFormatter(textFormatter);
		TextField tfMinNumberOfConections=new TextField();
		tfMinNumberOfConections.setTextFormatter(textFormatter2);
		TextField tfMinValue=new TextField();
		tfMinValue.setTextFormatter(textFormatter3);
		TextField tfNameOfStartedNode=new TextField(); //mo�na zmieni� na ChoiceBox czy co�
		tfNameOfStartedNode.setTextFormatter(textFormatter4);
		TextField tfNameOfEndedNode=new TextField();		
		tfNameOfEndedNode.setTextFormatter(textFormatter5);
		ComboBox<String> cbTypeOfGraph = new ComboBox<String>(
				FXCollections.observableArrayList("Graf Nieskierowany", "Graf Skierowany", "Nieskierowany - R�ne wagi"));
		cbTypeOfGraph.setValue("Graf Skierowany");

		/*
		 * tfMaxNumberOfConections.setTextFormatter(intTextFormater);
		 * tfMinValue.setTextFormatter(intTextFormater);
		 * tfNameOfStartedNode.setTextFormatter(intTextFormater);
		 * tfMaxNumberOfConections.setTextFormatter(intTextFormater);
		 */
	
		valuesOfFieldsMin.getChildren().addAll(tfNumberOfNodes, tfMinNumberOfConections, tfMinValue, tfNameOfStartedNode, tfNameOfEndedNode, cbTypeOfGraph);

		//PRAWA KOLUMNA
		Label null1=new Label();
		null1.setVisible(false);
		TextField tfMaxNumberOfConections=new TextField();
		tfMaxNumberOfConections.setLayoutY(tfMinNumberOfConections.getLayoutY());
		tfMaxNumberOfConections.setTextFormatter(textFormatter6);
		TextField tfMaxValue=new TextField();
		tfMaxValue.setTextFormatter(textFormatter7);
		tfMaxValue.setLayoutY(tfMinValue.getLayoutY());
		//tfMaxValue.setTextFormatter(intTextFormater);
		valuesOfFieldsMax.getChildren().addAll(null1, tfMaxNumberOfConections, tfMaxValue);
		
		
		root.getChildren().add(wholeContent);
		stage.setScene(new Scene(root, 500, 500));
		stage.show();
		
		
	
		
		buttonGenerateTable.setOnMouseClicked((MouseEvent e) -> { // Po kliknieciu wykonaj
			System.out.println("clicccck");
			if (tfNumberOfNodes.getText().equals("") ||  tfMinNumberOfConections.getText().equals("") ||  tfMinValue.getText().equals("") || 
					tfMaxNumberOfConections.getText().equals("") ||  tfMaxValue.getText().equals("") ||  tfNameOfStartedNode.getText().equals("") ||  
					tfNameOfEndedNode.getText().equals(""))
			{
				System.out.println("Kt�re� Pole jest PUSTE");
			}
			else
			{
				System.out.println("Wszystkie Pola s� pe�ne ");
				Generator graph = new Generator();
				graph.startm(Integer.parseInt(tfNumberOfNodes.getText()),Integer.parseInt(tfMinNumberOfConections.getText()),Integer.parseInt(tfMaxNumberOfConections.getText()),
						Integer.parseInt(tfMinValue.getText()),Integer.parseInt(tfMaxValue.getText())); // ILO�� WEZLOW, MIN POLACZEN, MAX POLACZEN, MIN WAGA, MAX WAGA, TYP GRAPHU
			}
		
		});
	}
	
	
	
}
