package pro.enikeev.imitation;

import junit.framework.TestCase;
import org.junit.Test;
import pro.enikeev.imitation.factory.VariantFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class ExcelExportTest extends TestCase {

    @Test
    public void testExcelExport() throws IOException {
        VariantFactory variantFactory = new VariantFactory();
        Scheme scheme = variantFactory.variant16();
        Path path = Paths.get(System.getProperty("user.home"), "Desktop", "imitationTestResult.xls");
        Exporter exporter = new ExcelExporter.Builder().setIterationsPerT(1000).settStart(0).settStep(0.5).settEnd(30).setFitDegree(3).build();
        exporter.export(scheme, path);
    }
}
