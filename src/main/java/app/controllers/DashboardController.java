package app.controllers;

import app.api.utils.Connector;
import app.models.Measurement;
import app.models.measurementComponents.Date;
import app.util.DialogUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.BinaryOperator;
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
    private final static String apiRequestFollow = "&parameter=";

    @FXML
    public BarChart<String, Number> barPlot;

    @FXML
    public JFXComboBox<Measurement.Parameter> parameterBox;

    @FXML
    public CategoryAxis xAxis;

    @FXML
    public NumberAxis yAxis;

    @FXML
    public Label localdateText;

    @FXML
    public JFXButton getResponseBtn;

    @FXML
    public Label meanText;

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

//        observableParams = FXCollections.observableMap(params);

        configureParameterBox();

//        parameterBox.disableProperty().bind(Bindings.isEmpty(observableParams));

        getResponseBtn.disableProperty().bind(Bindings.isEmpty(chosenCity));

    }


    public void getAPIResponse(ActionEvent actionEvent) {
        System.out.println(chosenParameter);
        try {
            String q = chosenCity.get();
            String url = apiRequest + URLEncoder.encode(q, "UTF-8")+apiRequestFollow+chosenParameter.getKey();

            measurements = Connector.getResponse(url);

            //get date of measurements
            String dateobj = measurements.get(0).getDate().getUtc();
            String[] tokens = dateobj.split("T");
            String date = tokens[0];
            String time = tokens[1].substring(0,8);
            dateText.setText("UTC: "+date+" "+time);

            String dateObjLocal = measurements.get(0).getDate().getLocal();
            String[] tokensL = dateObjLocal.split("T");
            String dateL = tokensL[0];
            String timeL = tokensL[1].substring(0,8);
            localdateText.setText("Local: "+dateL+" "+timeL);


            int mSize = measurements.size();
            measurementsNumberText.setText(String.valueOf(mSize));

            //find basic statistics about measurements
            Double average = measurements.stream().collect(Collectors.averagingDouble(x -> x.getValue()));
            double min = measurements.stream().min(Comparator.comparing(Measurement::getValue)).get().getValue();
            double max = measurements.stream().max(Comparator.comparing(Measurement::getValue)).get().getValue();

            double variance = measurements.stream()
                    .map(i -> i.getValue() - average)
                    .map(i -> i*i)
                    .mapToDouble(i -> i).average().getAsDouble();

            double std = Math.sqrt(variance);

            maxValueText.setText(String.format("%.3f",max));
            minValueText.setText(String.format("%.3f",min));
            meanText.setText(String.format("%.3f",average));
            stdText.setText(String.format("%.3f",std));

            //group measurements by params
//            params = measurements.stream().collect(Collectors.groupingBy(x -> x.getParameter()));

//            observableParams.clear();
//            observableParams.putAll(params);


            XYChart.Series<String,Number> series = new XYChart.Series();
            series.getData().add(new XYChart.Data<>("min",min));
            series.getData().add(new XYChart.Data<>("max",max));
            series.getData().add(new XYChart.Data<>("mean",average));
            series.getData().add(new XYChart.Data<>("std",std));

            barPlot.getData().clear();
            barPlot.getData().add(series);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InvalidParameterException e) {
            DialogUtils.popupWindow("Invalid City", 2);
        }

    }


    private void configureParameterBox() {
//        parameterBox.getItems().add(Measurement.Parameter.BC);
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

//
//            Double x = params.get(chosenParameter.getKey()).stream().collect(Collectors.averagingDouble(s -> s.getValue()));
//            System.out.println(x);

        });
    }
}
