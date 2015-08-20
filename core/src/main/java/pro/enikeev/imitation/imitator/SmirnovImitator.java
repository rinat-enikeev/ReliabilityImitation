package pro.enikeev.imitation.imitator;

import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.integration.IterativeLegendreGaussIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomGenerator;
import pro.enikeev.imitation.Scheme;
import pro.enikeev.imitation.Unit;

import java.util.*;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class SmirnovImitator extends AbstractImitator implements Imitator {

    public UnivariateIntegrator integrator = new IterativeLegendreGaussIntegrator(30, 0.01, 0.01);
    public int integratorMaxEval = 1000;
    public RandomGenerator random = new JDKRandomGenerator();

    public SmirnovImitator(Integer N, double tStart, double tEnd, double tStep)
            throws NumberIsTooLargeException, NullArgumentException, ZeroException, NotPositiveException {
        super(N, tStart, tEnd, tStep);
    }

    public UnivariateDifferentiableFunction reliability(Scheme scheme) {

        List<Double> xs = new ArrayList<Double>();
        List<Double> ys = new ArrayList<Double>();

        double t = tStart;
        do {
            Map<Unit, Double> yStars = new LinkedHashMap<Unit, Double>(scheme.getUnits().size());
            for (Unit unit : scheme.getUnits()) {
                Double yStar;
                if (t > 0) {
                    yStar = integrator.integrate(integratorMaxEval, unit.getReliabilityFunction(), 0, t);
                } else {
                    yStar = 0.0d;
                }
                yStars.put(unit, yStar);
            }

            int n = 0; // imitation counter (on one step)
            int k = 0; // failure counter

            while (n < N) {

                boolean systemFails = false;
                for (Set<Unit> failureUnitCombination : scheme.getFailureUnitCombo()) {
                    boolean comboFails = true;
                    for (Unit unit : failureUnitCombination) {
                        Double yJ = random.nextDouble();
                        Double yStar = yStars.get(unit);
                        boolean unitFails = yJ <= yStar;
                        comboFails = comboFails && unitFails;
                    }

                    if (comboFails) {
                        systemFails = true;
                        break;
                    }
                }

                if (systemFails) {
                    k++;
                }

                n++;
            }

            xs.add(t);
            ys.add(1 - ((double) k / (double) N));

            t +=  tStep;
        } while (t <= tEnd);

        return getSplineFunction(xs, ys);
    }
}
