package pro.enikeev.imitation;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import pro.enikeev.imitation.analysis.ReliabilityAnalyzer;
import pro.enikeev.imitation.imitator.Imitator;
import pro.enikeev.imitation.imitator.SampleImitator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class ExcelExporter implements Exporter {

    private int iterationsPerT;
    private double tStart;
    private double tEnd;
    private double tStep;
    private int fitDegree;

    public static class Builder {

        private int iterationsPerT = 100;
        private double tStart = 0;
        private double tEnd = 30;
        private double tStep = 1;
        private int fitDegree = 3;

        public ExcelExporter build() {
            return new ExcelExporter(iterationsPerT, tStart, tEnd, tStep, fitDegree);
        }

        public Builder setIterationsPerT(int iterationsPerT) {
            this.iterationsPerT = iterationsPerT;
            return this;
        }

        public Builder settStart(double tStart) {
            this.tStart = tStart;
            return this;
        }

        public Builder settEnd(double tEnd) {
            this.tEnd = tEnd;
            return this;
        }

        public Builder settStep(double tStep) {
            this.tStep = tStep;
            return this;
        }

        public Builder setFitDegree(int fitDegree) {
            this.fitDegree = fitDegree;
            return this;
        }
    }

    public ExcelExporter(int iterationsPerT, double tStart, double tEnd, double tStep, int fitDegree) {
        this.iterationsPerT = iterationsPerT;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.tStep = tStep;
        this.fitDegree = fitDegree;
    }

    public File export(Scheme scheme, Path path) throws IOException {

        if (!path.toFile().createNewFile()) {
            throw new FileAlreadyExistsException(path.toString());
        }

        Imitator imitator = new SampleImitator(iterationsPerT, tStart, tEnd, tStep);
        UnivariateFunction reliability = imitator.reliability(scheme);
        ReliabilityAnalyzer analyzer = new ReliabilityAnalyzer();
        Double mean = analyzer.mean(reliability, tEnd);
        double[] fit = analyzer.fit(reliability, fitDegree, tStart, tEnd, tStep);

        HSSFWorkbook workbook = new HSSFWorkbook();
        addSchemeSheet(workbook, mean, reliability);
        addUnitsSheet(workbook, scheme);

        FileOutputStream fileOut = new FileOutputStream(path.toFile());
        workbook.write(fileOut);
        fileOut.close();

        return path.toFile();
    }

    private void addUnitsSheet(HSSFWorkbook workbook, Scheme scheme) {
        HSSFSheet sheet = workbook.createSheet("Units");
        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

        int col = 0;
        for (Unit unit : scheme.getUnits()) {
            int rowIndex = 0;
            HSSFRow row =  sheet.getRow(rowIndex) != null ? sheet.getRow(rowIndex) : sheet.createRow(rowIndex);
            row.createCell(col).setCellValue("t");
            row.createCell(col + 1).setCellValue("f(t)");

            double t = tStart;
            rowIndex = 1;
            while (t <= tEnd) {
                row = sheet.getRow(rowIndex) != null ? sheet.getRow(rowIndex) : sheet.createRow(rowIndex);
                row.createCell(col).setCellValue(t);
                row.createCell(col + 1).setCellValue(unit.getReliabilityFunction().value(t));

                rowIndex++;
                t += tStep;
            }

            col += 3;
        }
    }

    private void addSchemeSheet(HSSFWorkbook workbook, double mean, UnivariateFunction reliability) {
        HSSFSheet sheet = workbook.createSheet("Scheme");
        DecimalFormat df = new DecimalFormat("0.00", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("Iterations per t");
        row.createCell(1).setCellValue(Integer.toString(iterationsPerT));

        row = sheet.createRow(1);
        row.createCell(0).setCellValue("tStart");
        row.createCell(1).setCellValue(df.format(tStart));

        row = sheet.createRow(2);
        row.createCell(0).setCellValue("tEnd");
        row.createCell(1).setCellValue(df.format(tEnd));

        row = sheet.createRow(3);
        row.createCell(0).setCellValue("tStep");
        row.createCell(1).setCellValue(df.format(tStep));

        row = sheet.createRow(4);
        row.createCell(0).setCellValue("Fit polynomial degree");
        row.createCell(1).setCellValue(Integer.toString(fitDegree));

        row = sheet.createRow(5);
        row.createCell(0).setCellValue("Reliable mean time");
        row.createCell(1).setCellValue(df.format(mean));


        row = sheet.getRow(0);
        row.createCell(3).setCellValue("t");
        row.createCell(4).setCellValue("P(t)");

        double t = tStart;
        int rowIndex = 1;
        while (t <= tEnd) {
            row = sheet.getRow(rowIndex) != null ? sheet.getRow(rowIndex) : sheet.createRow(rowIndex);
            row.createCell(3).setCellValue(t);
            row.createCell(4).setCellValue(reliability.value(t));

            rowIndex++;
            t += tStep;
        }

    }
}
