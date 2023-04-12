package com.nullnumber1;

import com.nullnumber1.logarithmic.Ln;
import com.nullnumber1.logarithmic.Log;
import com.nullnumber1.trigonometric.Cos;
import com.nullnumber1.trigonometric.Sec;
import com.nullnumber1.trigonometric.Sin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.SeriesMarkers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TestCsvOutput {

    static double functionEps = 0.3;

    static double startValue = -30;
    static double endValue = 30;

    static double step = 0.01;

    @Test
    void testSinWithCSVOutput() throws IOException {
        Sin sin = new Sin();
        sin.writeResultToCsv("src/main/resources/output/sinOutput.csv", startValue, endValue, step, functionEps);
    }

    @Test
    void testCosWithCSVOutput() throws IOException {
        Cos cos = new Cos();
        cos.writeResultToCsv("src/main/resources/output/cosOutput.csv", startValue, endValue, step, functionEps);
    }

    @Test
    void testSecWithCSVOutput() throws IOException {
        Sec sec = new Sec();
        sec.writeResultToCsv("src/main/resources/output/secOutput.csv", startValue, endValue, step, functionEps);
    }

    @Test
    void testLnWithCSVOutput() throws IOException {
        Ln ln = new Ln();
        ln.writeResultToCsv("src/main/resources/output/lnOutput.csv", startValue, endValue, step, functionEps);
    }

    @Test
    void testLog2WithCSVOutput() throws IOException {
        Log log = new Log();
        log.writeResultToCsv("src/main/resources/output/log2Output.csv", startValue, endValue, step, functionEps, 2);
    }

    @Test
    void testLog5WithCSVOutput() throws IOException {
        Log log = new Log();
        log.writeResultToCsv("src/main/resources/output/log5Output.csv", startValue, endValue, step, functionEps, 5);
    }

    @Test
    void testLog10WithCSVOutput() throws IOException {
        Log log = new Log();
        log.writeResultToCsv("src/main/resources/output/log10Output.csv", startValue, endValue, step, functionEps, 10);
    }

    @Test
    void testAllWithCSVOutput() throws IOException {
        SystemExpression system = new SystemExpression(new Sin(), new Cos(new Sin()), new Sec(new Cos(new Sin())), new Ln(), new Log());
        system.writeResultToCsv("src/main/resources/output/systemOutput.csv", startValue, endValue, step, functionEps);
    }

    @AfterAll
    static void plotAllGraphs() throws IOException {
        plotGraph("Sin Function", "Sin(X)", "src/main/resources/output/sinOutput.csv");
        plotGraph("Cos Function", "Cos(X)", "src/main/resources/output/cosOutput.csv");
        plotGraph("Sec Function", "Sec(X)", "src/main/resources/output/secOutput.csv");
        plotGraph("Ln Function", "Ln(X)", "src/main/resources/output/lnOutput.csv");
        plotGraph("Log2 Function", "Log2(X)", "src/main/resources/output/log2Output.csv");
        plotGraph("Log5 Function", "Log5(X)", "src/main/resources/output/log5Output.csv");
        plotGraph("Log10 Function", "Log10(X)", "src/main/resources/output/log10Output.csv");
        plotGraph("System Function", "System(X)", "src/main/resources/output/systemOutput.csv");
    }

    private static void plotGraph(String title, String yAxisTitle, String csvFilePath) throws IOException {
        List<Double> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(Paths.get(csvFilePath).toFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                xData.add(Double.parseDouble(values[0]));
                yData.add(Double.parseDouble(values[1]));
            }
        }

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title(title)
                .xAxisTitle("X")
                .yAxisTitle(yAxisTitle)
                .build();

        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);

        XYSeries series = chart.addSeries(title, xData, yData);
        series.setMarker(SeriesMarkers.NONE);

        BitmapEncoder.saveBitmap(chart, "src/main/resources/output/images/" + title, BitmapEncoder.BitmapFormat.PNG);
    }
}
