package pro.enikeev.imitation.analysis;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.IterativeLegendreGaussIntegrator;
import org.apache.commons.math3.analysis.integration.UnivariateIntegrator;
import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class ReliabilityAnalyzer {

    private UnivariateIntegrator integrator = new IterativeLegendreGaussIntegrator(30, 0.01, 0.01);
    private int integratorMaxEval = 1000;

    public Double mean(UnivariateFunction reliabilityFunction, double endTime) {
        return integrator.integrate(integratorMaxEval, reliabilityFunction, 0, endTime);
    }

    public double[] fit(UnivariateFunction function, int degree, double tStart, double tEnd, double tStep)
            throws NumberIsTooLargeException, ZeroException, NotPositiveException {

        UnivariateSolverUtils.verifyInterval(tStart, tEnd);
        if (tStep == 0) {
            throw new ZeroException();
        }
        if (tStep < 0) {
            throw new NotPositiveException(tStep);
        }
        if (tStart < 0) {
            throw new NotPositiveException(tStart);
        }

        // Collect data.
        final WeightedObservedPoints obs = new WeightedObservedPoints();
        double t = tStart;
        while (t <= tEnd) {
            obs.add(t, function.value(t));
            t += tStep;
        }

        final PolynomialCurveFitter fitter = PolynomialCurveFitter.create(degree);

        return fitter.fit(obs.toList());
    }

}
