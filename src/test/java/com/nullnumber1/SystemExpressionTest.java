package com.nullnumber1;

import com.nullnumber1.logarithmic.Ln;
import com.nullnumber1.logarithmic.Log;
import com.nullnumber1.trigonometric.Cos;
import com.nullnumber1.trigonometric.Sec;
import com.nullnumber1.trigonometric.Sin;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class SystemExpressionTest {

    static double functionEps = 0.3;
    double eps = 0.6;

    static Sin sinMock;
    static Cos cosMock;

    static Sec secMock;
    static Ln lnMock;
    static Log logMock;

    static Reader sinIn;
    static Reader cosIn;
    static Reader secIn;

    static Reader lnIn;
    static Reader log2In;
    static Reader log5In;
    static Reader log10In;

    static FileWriter file;

    @BeforeAll
    static void setUp() {
        sinMock = Mockito.mock(Sin.class);
        cosMock = Mockito.mock(Cos.class);
        secMock = Mockito.mock(Sec.class);
        lnMock = Mockito.mock(Ln.class);
        logMock = Mockito.mock(Log.class);

        try {
            sinIn = new FileReader("src/main/resources/input/sin.csv");
            cosIn = new FileReader("src/main/resources/input/cos.csv");
            secIn = new FileReader("src/main/resources/input/sec.csv");

            lnIn = new FileReader("src/main/resources/input/ln.csv");
            log2In = new FileReader("src/main/resources/input/log2.csv");
            log5In = new FileReader("src/main/resources/input/log5.csv");
            log10In = new FileReader("src/main/resources/input/log10.csv");

            for (Reader reader : new Reader[]{sinIn, cosIn, secIn, lnIn, log2In, log5In, log10In}) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(reader);
                for (CSVRecord record : records) {
                    double x = Double.parseDouble(record.get(0));
                    double result = Double.parseDouble(record.get(1));

                    if (reader == sinIn) {
                        Mockito.when(sinMock.sin(x, functionEps)).thenReturn(result);
                    } else if (reader == cosIn) {
                        Mockito.when(cosMock.cos(x, functionEps)).thenReturn(result);
                    } else if (reader == secIn) {
                        Mockito.when(secMock.sec(x, functionEps)).thenReturn(result);
                    } else if (reader == lnIn) {
                        Mockito.when(lnMock.ln(x, functionEps)).thenReturn(result);
                    } else if (reader == log2In || reader == log5In || reader == log10In) {
                        int base = (reader == log2In) ? 2 : (reader == log5In) ? 5 : 10;
                        Mockito.when(logMock.log(base, x, functionEps)).thenReturn(result);
                    }
                }
            }

        } catch (IOException ex) {
            fail("Data input file not found:\n" + ex.getMessage());
        }
    }

    @BeforeEach
    void initOutputFile() {
        try {
            file = new FileWriter("src/main/resources/output/output.csv");
        } catch (IOException ex) {
            fail("Output file not found:\n" + ex.getMessage());
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testSystemMocksOnly(double x, double expected) {
        SystemExpression system = new SystemExpression(sinMock, cosMock, secMock, lnMock, logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testSin(double x, double expected) {
        SystemExpression system = new SystemExpression(new Sin(), cosMock, secMock, lnMock, logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testCos(double x, double expected) {
        SystemExpression system = new SystemExpression(sinMock, new Cos(sinMock), secMock, lnMock, logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testSinCos(double x, double expected) {
        SystemExpression system = new SystemExpression(new Sin(), new Cos(new Sin()), secMock, lnMock, logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testSec(double x, double expected) {
        SystemExpression system = new SystemExpression(sinMock, cosMock, new Sec(cosMock), lnMock, logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testSinCosSec(double x, double expected) {
        SystemExpression system = new SystemExpression(new Sin(), new Cos(new Sin()), new Sec(new Cos(new Sin())), lnMock, logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testLn(double x, double expected) {
        SystemExpression system = new SystemExpression(sinMock, cosMock, secMock, new Ln(), logMock);
        assertEquals(expected, system.systemSolve(x, functionEps), eps * 100);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testLog(double x, double expected) {
        SystemExpression system = new SystemExpression(sinMock, cosMock, secMock, new Ln(), new Log());
        assertEquals(expected, system.systemSolve(x, functionEps), eps * 100);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/input/system.csv")
    void testAll(double x, double expected) {
        SystemExpression system = new SystemExpression(new Sin(), new Cos(new Sin()), new Sec(new Cos(new Sin())), new Ln(), new Log());
        assertEquals(expected, system.systemSolve(x, functionEps), eps * 100);
    }
}
