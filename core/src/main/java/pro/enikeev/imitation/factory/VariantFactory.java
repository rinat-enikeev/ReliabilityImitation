package pro.enikeev.imitation.factory;

import pro.enikeev.imitation.*;
import pro.enikeev.imitation.Scheme;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class VariantFactory {

    public Scheme variant16() {
        FunctionFactory ff = new FunctionFactory();
        DistributionFactory df = new DistributionFactory();
        Unit unit1 = new Unit(ff.fifth(), df.fifth());
        Unit unit2 = new Unit(ff.fourth(), df.fourth());
        Unit unit3 = new Unit(ff.third(), df.third());
        Unit unit4 = new Unit(ff.first(), df.first());
        Unit unit5 = new Unit(ff.seventh(), df.seventh());
        Unit unit6 = new Unit(ff.second(), df.second());
        Unit unit7 = new Unit(ff.sixth(), df.sixth());

        Scheme scheme = new Scheme(unit1, unit2, unit3, unit4, unit5, unit6, unit7);
        scheme.addFailureBlockCombination(unit1, unit2);
        scheme.addFailureBlockCombination(unit3, unit7);
        scheme.addFailureBlockCombination(unit4, unit5, unit6, unit7);
        return scheme;
    }
}
