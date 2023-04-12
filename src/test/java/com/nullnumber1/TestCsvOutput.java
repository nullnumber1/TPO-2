package com.nullnumber1;

import com.nullnumber1.logarithmic.Ln;
import com.nullnumber1.logarithmic.Log;
import com.nullnumber1.trigonometric.Cos;
import com.nullnumber1.trigonometric.Sec;
import com.nullnumber1.trigonometric.Sin;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class TestCsvOutput {

    static double functionEps = 0.3;

    @Test
    void testSinWithCSVOutput() throws IOException {
        Sin sin = new Sin();
        sin.writeResultToCsv("src/main/resources/output/sinOutput.csv", -10, 10, 0.1, functionEps);
    }

    @Test
    void testCosWithCSVOutput() throws IOException {
        Cos cos = new Cos();
        cos.writeResultToCsv("src/main/resources/output/cosOutput.csv", -10, 10, 0.1, functionEps);
    }

    @Test
    void testSecWithCSVOutput() throws IOException {
        Sec sec = new Sec();
        sec.writeResultToCsv("src/main/resources/output/secOutput.csv", -10, 10, 0.1, functionEps);
    }

    @Test
    void testLnWithCSVOutput() throws IOException {
        Ln ln = new Ln();
        ln.writeResultToCsv("src/main/resources/output/lnOutput.csv", -10, 10, 0.1, functionEps);
    }

    @Test
    void testLog2WithCSVOutput() throws IOException {
        Log log = new Log();
        log.writeResultToCsv("src/main/resources/output/logOutput.csv", -10, 10, 0.1, functionEps, 2);
    }

    @Test
    void testLog5WithCSVOutput() throws IOException {
        Log log = new Log();
        log.writeResultToCsv("src/main/resources/output/logOutput.csv", -10, 10, 0.1, functionEps, 5);
    }

    @Test
    void testLog10WithCSVOutput() throws IOException {
        Log log = new Log();
        log.writeResultToCsv("src/main/resources/output/logOutput.csv", -10, 10, 0.1, functionEps, 10);
    }

    @Test
    void testAllWithCSVOutput() throws IOException {
        SystemExpression system = new SystemExpression(new Sin(), new Cos(new Sin()), new Sec(new Cos(new Sin())), new Ln(), new Log());
        system.writeResultToCsv("src/main/resources/output/systemOutput.csv", -10, 10, 0.1, functionEps);
    }
}
