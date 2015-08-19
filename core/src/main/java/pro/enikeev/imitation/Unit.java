package pro.enikeev.imitation;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.distribution.RealDistribution;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class Unit {
    private UnivariateFunction reliabilityFunction;
    private RealDistribution reliabilityDistribution;

    public Unit(UnivariateFunction reliabilityFunction) {
        this.reliabilityFunction = reliabilityFunction;
    }

    public Unit(UnivariateFunction reliabilityFunction, RealDistribution reliabilityDistribution) {
        this.reliabilityFunction = reliabilityFunction;
        this.reliabilityDistribution = reliabilityDistribution;
    }


    public UnivariateFunction getReliabilityFunction() {
        return reliabilityFunction;
    }

    public RealDistribution getReliabilityDistribution() {
        return reliabilityDistribution;
    }
}
