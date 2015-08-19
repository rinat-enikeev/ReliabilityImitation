package pro.enikeev.imitation;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.apache.commons.math3.analysis.UnivariateFunction;
import pro.enikeev.imitation.analysis.ReliabilityAnalyzer;
import pro.enikeev.imitation.factory.VariantFactory;
import pro.enikeev.imitation.imitator.Imitator;
import pro.enikeev.imitation.imitator.SampleImitator;
import pro.enikeev.imitation.imitator.SmirnovImitator;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class JFXImitationController implements Initializable {

    @FXML public IntegerTextField textFieldN;
    @FXML public IntegerTextField textFieldCurveFitDegree;
    @FXML public FloatTextField textFieldTStart;
    @FXML public FloatTextField textFieldEnd;
    @FXML public FloatTextField textFieldStep;
    @FXML public FloatTextField textFieldReliabilityTill;
    @FXML public LineChart<Number,Number> chartUnit1;
    @FXML public LineChart<Number,Number> chartUnit2;
    @FXML public LineChart<Number,Number> chartUnit3;
    @FXML public LineChart<Number,Number> chartUnit4;
    @FXML public LineChart<Number,Number> chartUnit5;
    @FXML public LineChart<Number,Number> chartUnit6;
    @FXML public LineChart<Number,Number> chartUnit7;
    @FXML public LineChart<Number,Number> chartSystem;
    @FXML public LineChart<Number,Number> chartSystemSmall;
    @FXML public Label labelMeanTime;
    @FXML public Label labelReliableTillProbability;
    @FXML public Label labelCurveFitCoefficients;
    @FXML public ImageView imageViewScheme;
    @FXML public Pane paneImageViewInGrid;

    private Scheme scheme;
    private ReliabilityAnalyzer analyzer = new ReliabilityAnalyzer();
    private double[] curveCoefficients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        imageViewScheme.fitWidthProperty().bind(paneImageViewInGrid.widthProperty());
        imageViewScheme.fitHeightProperty().bind(paneImageViewInGrid.heightProperty());

        VariantFactory variantFactory = new VariantFactory();
        scheme = variantFactory.variant16();
        Iterator<Unit> unitIterator = scheme.getUnits().iterator();

        double xStart = 0;
        double xEnd = 30;
        double xStep = 0.1;

        chartUnit1.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
        chartUnit2.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
        chartUnit3.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
        chartUnit4.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
        chartUnit5.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
        chartUnit6.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
        chartUnit7.getData().add(seriesFromFunction(unitIterator.next().getReliabilityFunction(), xStart, xEnd, xStep));
    }

    private XYChart.Series seriesFromFunction(UnivariateFunction function, double xStart, double xEnd, double xStep) {
        XYChart.Series result = new XYChart.Series();
        double x = xStart;
        while (x <= xEnd) {
            double y = function.value(x);
            result.getData().add(new XYChart.Data(x, y));
            x += xStep;
        }
        return result;
    }

    @FXML
    void buttonClearOnMouseClicked() {
        chartSystem.getData().clear();
        chartSystemSmall.getData().clear();
        labelMeanTime.setText("Unknown");
        labelReliableTillProbability.setText("Unknown");
        labelCurveFitCoefficients.setText("Unknown");
        curveCoefficients = null;
    }

    @FXML
    void buttonSmirnovOnMouseClicked() {

        Float tStart = textFieldTStart.getValue();
        Float tEnd = textFieldEnd.getValue();
        Float tStep = textFieldStep.getValue();
        Imitator imitator = new SmirnovImitator(textFieldN.getValue(), tStart, tEnd, tStep);
        UnivariateFunction reliability = imitator.reliability(scheme);
        drawSystemFunction(reliability, tStart, tEnd, tStep);

        updateCalculatedValues(reliability);
    }

    @FXML
    void buttonSampleOnMouseClicked() {
        Float tStart = textFieldTStart.getValue();
        Float tEnd = textFieldEnd.getValue();
        Float tStep = textFieldStep.getValue();

        Imitator imitator = new SampleImitator(textFieldN.getValue(), tStart, tEnd, tStep);
        UnivariateFunction reliability = imitator.reliability(scheme);
        drawSystemFunction(reliability, tStart, tEnd, tStep);

        updateCalculatedValues(reliability);
    }

    private void updateCalculatedValues(UnivariateFunction reliability) {
        updateMeanTime(reliability);
        updateReliableTillTimeProbability(reliability);
        updateCurveFitCoefficientsy(reliability);
    }

    @FXML
    void buttonDrawCurveOnMouseClicked() {

        if (curveCoefficients == null) {
            return;
        }

        Float tStart = textFieldTStart.getValue();
        Float tEnd = textFieldEnd.getValue();
        Float tStep = textFieldStep.getValue();

        XYChart.Series mainSeries = new XYChart.Series();
        double t = tStart;
        while (t <= tEnd) {
            double y = 0;
            for (int i = 0; i < curveCoefficients.length; i++) {
                y += Math.pow(t, i) * curveCoefficients[i];
            }
            mainSeries.getData().add(new XYChart.Data(t, y));
            t += tStep;
        }
        chartSystem.getData().add(mainSeries);
    }

    private void updateReliableTillTimeProbability(UnivariateFunction reliability) {
        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        labelReliableTillProbability.setText(df.format(reliability.value(textFieldReliabilityTill.getValue())));
    }

    private void updateMeanTime(UnivariateFunction reliability) {
        Float tEnd = textFieldEnd.getValue();
        Double mean = analyzer.mean(reliability, tEnd);
        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        labelMeanTime.setText(df.format(mean));
    }

    private void updateCurveFitCoefficientsy(UnivariateFunction reliability) {

        Float tStart = textFieldTStart.getValue();
        Float tEnd = textFieldEnd.getValue();
        Float tStep = textFieldStep.getValue();

        curveCoefficients = analyzer.fit(reliability, textFieldCurveFitDegree.getValue(), tStart, tEnd, tStep);

        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        String result = "";
        for (int i = 0; i < curveCoefficients.length - 1; i++) {
            result += df.format(curveCoefficients[i]) + "; ";
        }
        result += df.format(curveCoefficients[curveCoefficients.length - 1]);

        labelCurveFitCoefficients.setText(result);
    }

    private void drawSystemFunction(UnivariateFunction reliability, double tStart, double tEnd, double tStep) {
        XYChart.Series mainSeries = new XYChart.Series();
        XYChart.Series smallSeries = new XYChart.Series();
        double t = tStart;
        while (t <= tEnd) {
            Double p = reliability.value(t);
            mainSeries.getData().add(new XYChart.Data(t, p));
            smallSeries.getData().add(new XYChart.Data(t, p));
            t += tStep;
        }
        chartSystem.getData().add(mainSeries);
        chartSystemSmall.getData().add(smallSeries);
    }

}
