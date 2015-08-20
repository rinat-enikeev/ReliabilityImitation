package pro.enikeev.imitation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public interface Exporter {
    File export(Scheme scheme, Path path) throws IOException;
}
