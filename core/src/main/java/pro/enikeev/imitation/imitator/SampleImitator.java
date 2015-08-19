package pro.enikeev.imitation.imitator;

import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.ZeroException;
import pro.enikeev.imitation.Scheme;
import pro.enikeev.imitation.Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class SampleImitator extends AbstractImitator implements Imitator {

    public SampleImitator(Integer N, double tStart, double tEnd, double tStep)
            throws NumberIsTooLargeException, NullArgumentException, ZeroException, NotPositiveException {
        super(N, tStart, tEnd, tStep);
    }

    public UnivariateDifferentiableFunction reliability(Scheme scheme) {

        List<Double> xs = new ArrayList<Double>();
        List<Double> ys = new ArrayList<Double>();

        double t = tStart;
        do {
            int n = 0; // imitation counter (on one step)
            int k = 0; // failure counter

            while (n < N) {
                boolean schemeFails = false;
                for (Set<Unit> failureUnitCombination : scheme.getFailureUnitCombo()) {
                    boolean comboFails = true;
                    for (Unit unit : failureUnitCombination) {
                        comboFails = comboFails && unitFailes(unit, t);
                    }

                    if (comboFails) {
                        schemeFails = true;
                        break;
                    }
                }

                if (schemeFails) {
                    k++;
                }

                n++;
            }

            xs.add( t);
            ys.add(1 - ((double) k / (double) N));

            t += tStep;
        } while (t <= tEnd);

        return getSplineFunction(xs, ys);
    }

    protected boolean unitFailes(Unit unit, double time) {
        double ti = unit.getReliabilityDistribution().sample();
        return ti <= time;
    }

}
