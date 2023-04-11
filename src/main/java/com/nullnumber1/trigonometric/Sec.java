package com.nullnumber1.trigonometric;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Sec {

    private final Cos cos;

    public Sec(Cos cos) {
        this.cos = cos;
    }

    public Sec() {
        this.cos = new Cos();
    }

    public double sec(double x, double eps) {
        double cosValue = cos.cos(x, eps);
        if (Double.isNaN(cosValue) || Math.abs(cosValue) < eps) {
            return Double.NaN;
        }
        return 1 / cosValue;
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
                printWriter.println(current + "," + this.sec(current, eps));
            }
        }
    }
}
