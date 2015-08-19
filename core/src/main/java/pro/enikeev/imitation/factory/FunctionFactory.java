package pro.enikeev.imitation.factory;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.function.Gaussian;
import org.apache.commons.math3.util.FastMath;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class FunctionFactory {

    public UnivariateFunction first() {

        return new UnivariateFunction() {

            private double lambda = 0.1;

            public double value(double v) {
                return lambda * FastMath.exp(-lambda * v);
            }
        };
    }

    public UnivariateFunction second() {

        return new UnivariateFunction() {

            private double lambda = 0.05;

            public double value(double v) {
                return lambda * FastMath.exp(-lambda * v);
            }
        };
    }

    public UnivariateFunction third() {
        return new Gaussian(12, 2);
    }

    public UnivariateFunction fourth() {
        return new UnivariateFunction() {

            private double a = 2;
            private double b = 14;

            public double value(double v) {
                if (v < a) {
                    return 0;
                } else if ((a <= v) && (v <= b)) {
                    return 1 / (b - a);
                } else { // v > b
                    return 0;
                }
            }
        };
    }

    public UnivariateFunction fifth() {
        return new Gaussian(16, 2);
    }

    public UnivariateFunction sixth() {
        return new UnivariateFunction() {

            private double a = 1;
            private double b = 29;

            public double value(double v) {
                if (v < a) {
                    return 0;
                } else if ((a <= v) && (v <= b)) {
                    return 1 / (b - a);
                } else { // v > b
                    return 0;
                }
            }
        };
    }

    public UnivariateFunction seventh() {
        return new UnivariateFunction() {

            private double c = 2;
            private double d = 18;

            public double value(double v) {
                if ((c <= v) && (v <= d)) {
                    return (2/(d-c)) - Math.abs(c+d-2*v)*(2/((d-c)*(d-c)));
                } else { // v < b || v > d
                    return 0;
                }
            }
        };
    }
}
