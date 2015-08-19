package pro.enikeev.imitation.imitator;

import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import pro.enikeev.imitation.Scheme;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public interface Imitator {
    UnivariateDifferentiableFunction reliability(Scheme scheme);
}
