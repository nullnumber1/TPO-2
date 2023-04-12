package com.nullnumber1;

import com.nullnumber1.logarithmic.Ln;
import com.nullnumber1.logarithmic.Log;
import com.nullnumber1.trigonometric.Cos;
import com.nullnumber1.trigonometric.Sec;
import com.nullnumber1.trigonometric.Sin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SystemExpression {
    Sin sin;
    Cos cos;
    Ln ln;
    Log log;
    Sec sec;

    public SystemExpression(Sin sin, Cos cos, Sec sec, Ln ln, Log log) {
        this.sin = sin;
        this.cos = cos;
        this.ln = ln;
        this.log = log;
        this.sec = sec;
    }

    public double systemSolve(double x, double eps) {
        if (x <= 0) {
            double secValue = sec.sec(x, eps);
            double cosValue = cos.cos(x, eps);
            return Math.pow(secValue + cosValue, 2);
        } else {
            double log10Value = log.log(10, x, eps);
            double log5Value = log.log(5, x, eps);
            double lnValue = ln.ln(x, eps);
            double log2Value = log.log(2, x, eps);

            double numerator = Math.pow(log10Value + log5Value, 2) * (lnValue + (log2Value / log5Value));
            double denominator = log5Value * (lnValue - log2Value);

            return numerator / denominator;
        }
    }

    public void writeResultToCsv(final String filename, final double from, final double to, final double step, final double eps) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();

        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (double current = from; current <= to; current += step) {
                printWriter.println(current + "," + systemSolve(current, eps));
            }
        }
    }
}