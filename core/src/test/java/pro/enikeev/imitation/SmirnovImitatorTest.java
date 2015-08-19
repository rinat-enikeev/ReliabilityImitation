package pro.enikeev.imitation;

import junit.framework.TestCase;
import org.junit.Test;
import pro.enikeev.imitation.factory.VariantFactory;
import pro.enikeev.imitation.imitator.SmirnovImitator;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class SmirnovImitatorTest extends TestCase {

    @Test
    public void testVariant16() {
        VariantFactory variantFactory = new VariantFactory();
        Scheme scheme = variantFactory.variant16();
        SmirnovImitator smirnovImitator = new SmirnovImitator(10, 0.1, 10, 0.1);
//        SortedMap<Double, Double> systemFailureMap = smirnovImitator.reliability(scheme);

    }
}
