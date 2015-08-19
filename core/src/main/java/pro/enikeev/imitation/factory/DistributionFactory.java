package pro.enikeev.imitation.factory;

import org.apache.commons.math3.distribution.*;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class DistributionFactory {

    public RealDistribution first() {

        return new ExponentialDistribution(10);
    }

    public RealDistribution second() {

        return new ExponentialDistribution(20);
    }

    public RealDistribution third() {
        return new NormalDistribution(12, 2);
    }

    public RealDistribution fourth() {
        return new UniformRealDistribution(2, 14);
    }

    public RealDistribution fifth() {
        return new NormalDistribution(16, 2);
    }

    public RealDistribution sixth() {
        return new UniformRealDistribution(1, 29);
    }

    public RealDistribution seventh() {
        return new TriangularDistribution(2, 10, 18);
    }
}
