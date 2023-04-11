package com.nullnumber1.logarithmic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Log {

    private final Ln ln;

    public Log(Ln ln) {
        this.ln = ln;
    }

    public Log() {
        this.ln = new Ln();
    }


    // a is base
    // b is argument
    public double log(double a, double b, double eps) {
        return ln.ln(b, eps) / ln.ln(a, eps);
    }

    public void writeResultToCsv(final String filename, final double from, final double to, final double step, final double eps, final double a) throws IOException {
        final Path path = Paths.get(filename);
        final File file = new File(path.toUri());
        if (file.exists()) {
            Files.delete(path);
        }
        file.createNewFile();

        try (PrintWriter printWriter = new PrintWriter(file)) {
            for (double current = from; current <= to; current += step) {
                printWriter.println(current + "," + this.log(a, current, eps));
            }
        }
    }
}