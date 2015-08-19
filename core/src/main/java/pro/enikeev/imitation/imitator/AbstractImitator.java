package pro.enikeev.imitation.imitator;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math3.analysis.solvers.UnivariateSolverUtils;
import org.apache.commons.math3.exception.NotPositiveException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.ZeroException;
import org.apache.commons.math3.util.MathUtils;
import pro.enikeev.imitation.Scheme;

import java.util.List;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public abstract class AbstractImitator implements Imitator {

    public abstract UnivariateDifferentiableFunction reliability(Scheme scheme);

    protected Integer N;
    protected double tStart;
    protected double tEnd;
    protected double tStep;

    public AbstractImitator(Integer N, double tStart, double tEnd, double tStep)
            throws NumberIsTooLargeException, NullArgumentException, ZeroException, NotPositiveException {

        UnivariateSolverUtils.verifyInterval(tStart, tEnd);
        MathUtils.checkNotNull(N);
        if (tStep == 0) {
            throw new ZeroException();
        }
        if (tStep < 0) {
            throw new NotPositiveException(tStep);
        }
        if (tStart < 0) {
            throw new NotPositiveException(tStart);
        }

        this.N = N;
        this.tStart = tStart;
        this.tEnd = tEnd;
        this.tStep = tStep;
    }

    protected PolynomialSplineFunction getSplineFunction(List<Double> xs, List<Double> ys) {
        SplineInterpolator splineInterpolator = new SplineInterpolator();
        double[] xsp = ArrayUtils.toPrimitive(xs.toArray(new Double[xs.size()]));
        double[] ysp = ArrayUtils.toPrimitive(ys.toArray(new Double[ys.size()]));
        return splineInterpolator.interpolate(xsp, ysp);
    }

}
