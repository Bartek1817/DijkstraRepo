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
		TextFormatter<Integer> intTextFormater = new TextFormatter<>(converterInt, 10 , filter);
		
		HBox wholeContent=new HBox(10);
		wholeContent.setTranslateY(10);
		VBox namesOfFields=new VBox(40);
		VBox valuesOfFieldsMin=new VBox(30);
		VBox valuesOfFieldsMax=new VBox(38);
		
		wholeContent.getChildren().addAll(namesOfFields, valuesOfFieldsMin, valuesOfFieldsMax);
		
		//LEWA KOLUMNA
		Label textNumberOfNodes=new Label("Podaj iloœæ wêz³ów:");
		Label textMaxNumberOfConections=new Label("Podaj maksymaln¹ iloœæ po³¹czeñ:");
		Label textRangeOfValue=new Label("Podaj zakres wagi po³¹czenia:");
		Label textNameOfStartedNode=new Label("Wybierz wêze³ pocz¹tkowy:");
		Label textNameOfEndedNode=new Label("Wybierz wêze³ koñcowy:");
		Label textTypeOfGraph=new Label("Wybierz typ grafu:");
		Button buttonGenerateTable=new Button("Generuj Tabelê Wag");
		namesOfFields.getChildren().addAll(textNumberOfNodes, textMaxNumberOfConections, textRangeOfValue, textNameOfStartedNode, textNameOfEndedNode, textTypeOfGraph);
		//ŒRODKOWA KOLUMNA
		TextField tfNumberOfNodes=new TextField();
		TextField tfMaxNumberOfConections=new TextField();
		TextField tfMinValue=new TextField();
		TextField tfNameOfStartedNode=new TextField(); //mo¿na zmieniæ na ChoiceBox czy coœ
		TextField tfNameOfEndedNode=new TextField();		
		ComboBox<String> cbTypeOfGraph = new ComboBox<String>(
				FXCollections.observableArrayList("Graf Nieskierowany", "Graf Skierowany", "Nieskierowany - Ró¿ne wagi"));
		

		tfNumberOfNodes.setTextFormatter(intTextFormater);
		/*
		 * tfMaxNumberOfConections.setTextFormatter(intTextFormater);
		 * tfMinValue.setTextFormatter(intTextFormater);
		 * tfNameOfStartedNode.setTextFormatter(intTextFormater);
		 * tfMaxNumberOfConections.setTextFormatter(intTextFormater);
		 */
	
		valuesOfFieldsMin.getChildren().addAll(tfNumberOfNodes, tfMaxNumberOfConections, tfMinValue, tfNameOfStartedNode, tfNameOfEndedNode, cbTypeOfGraph);

		//PRAWA KOLUMNA
		Label null1=new Label();
		null1.setVisible(false);
		Label null2=new Label();
		null2.setVisible(false);
		TextField tfMaxValue=new TextField();
		//tfMaxValue.setTextFormatter(intTextFormater);
		valuesOfFieldsMax.getChildren().addAll(null1, null2, tfMaxValue);
		
		
		root.getChildren().add(wholeContent);
		stage.setScene(new Scene(root, 500, 500));
		stage.show();
	}
	
}
