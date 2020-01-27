package app.controllers;

import app.api.utils.Connector;
import app.models.Measurement;
import app.util.DialogUtils;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;


public class DashboardController {

    private final static String apiRequest = "https://api.openaq.org/v1/measurements?city=";

    @FXML
    public BarChart<String, Number> barPlot;

    @FXML
    public JFXComboBox<Measurement.Parameter> parameterBox;

    @FXML
    public CategoryAxis xAxis;

    @FXML
    public NumberAxis yAxis;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private JFXTextField cityText;

    @FXML
    private Label measurementsNumberText;

    @FXML
    private Label maxValueText;

    @FXML
    private Label minValueText;

    @FXML
    private Label stdText;

    @FXML
    public Label dateText;

    @FXML
    private Pane skyBox;

    private StringProperty chosenCity;

    private List<Measurement> measurements;

    private Map<String, List<Measurement>> params;
    private ObservableMap<String, List<Measurement>> observableParams;

    private Measurement.Parameter chosenParameter;


    @FXML
    void initialize() {
        assert cityText != null : "fx:id=\"cityText\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert measurementsNumberText != null : "fx:id=\"measurementsNumberText\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert maxValueText != null : "fx:id=\"maxValueText\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert minValueText != null : "fx:id=\"minValueText\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert stdText != null : "fx:id=\"stdText\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert skyBox != null : "fx:id=\"skyBox\" was not injected: check your FXML file 'dashboard.fxml'.";


        chosenCity = new SimpleStringProperty();

        chosenCity.bind(cityText.textProperty());

        measurements = new ArrayList<>();
        params = new HashMap<>();

        observableParams = FXCollections.observableMap(params);

        configureParameterBox();

        parameterBox.disableProperty().bind(Bindings.isEmpty(observableParams));


    }


    public void getAPIResponse(ActionEvent actionEvent) {
        System.out.println(chosenParameter);
        try {
            String q = chosenCity.get();
            String url = apiRequest + URLEncoder.encode(q, "UTF-8");

            measurements = Connector.getResponse(url);
            //group measurements by params
            params = measurements.stream().collect(Collectors.groupingBy(x -> x.getParameter()));

            observableParams.clear();
            observableParams.putAll(params);


            System.out.println(params);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            DialogUtils.popupWindow("Invalid City", 2);
        }

    }


    private void configureParameterBox() {
        parameterBox.getItems().add(Measurement.Parameter.BC);
        parameterBox.getItems().add(Measurement.Parameter.CO);
        parameterBox.getItems().add(Measurement.Parameter.NO2);
        parameterBox.getItems().add(Measurement.Parameter.O3);
        parameterBox.getItems().add(Measurement.Parameter.PM10);
        parameterBox.getItems().add(Measurement.Parameter.PM25);
        parameterBox.getItems().add(Measurement.Parameter.SO2);
        parameterBox.getSelectionModel().selectFirst();


        chosenParameter = parameterBox.getValue();

        parameterBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            chosenParameter = newValue;


            Double x = params.get(chosenParameter.getKey()).stream().collect(Collectors.averagingDouble(s -> s.getValue()));
            System.out.println(x);

        });
    }
}
