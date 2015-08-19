package pro.enikeev.imitation;

import java.util.*;

/**
 * @author Rinat Enikeev
 * @since 0.1
 */
public class Scheme {
    private LinkedHashSet<Unit> units;
    private Set<Set<Unit>> failureUnitCombo;

    public Scheme(Unit... units) {
        this.units = new LinkedHashSet<Unit>(Arrays.asList(units));
        this.failureUnitCombo = new HashSet<Set<Unit>>();
    }

    public void addFailureBlockCombination(Unit... units) {
        Set<Unit> failureUnits = new HashSet<Unit>(Arrays.asList(units));
        failureUnitCombo.add(failureUnits);
    }

    public Set<Unit> getUnits() {
        return units;
    }

    public Collection<Set<Unit>> getFailureUnitCombo() {
        return failureUnitCombo;
    }
}
