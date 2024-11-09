package decisions.generators;

import decisions.nodes.Allow;
import decisions.nodes.DecisionEffect;
import decisions.nodes.Picture;
import decisions.nodes.Potential;
import map.regions.Region;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.EffectScope;

import java.util.List;

public class ClaimsCBViewerGenerator extends AbstractDecisionGenerator {

    protected List<Region> regions;

    public ClaimsCBViewerGenerator(String filename, String headerName) throws Exception {
        super(filename, headerName);
        throw new Exception("Needs to receive list of regions too");
    }

    public ClaimsCBViewerGenerator(String filename, String headerName, List<Region> regions) throws Exception {
        super(filename, headerName);

        /* Get the regions in ascending order by the id */
        this.regions = regions.stream().filter(region -> region.getSortingId() != 0).sorted((o1, o2) -> {
            if (o1.getSortingId() > o2.getSortingId()) {
                return 1;
            } else if (o2.getSortingId() > o1.getSortingId()) {
                return -1;
            }

            return 0;
        }).toList();
    }

    @Override
    protected String getSectionName() {
        return "Claims";
    }

    @Override
    protected String getName() {
        return "View Claimed Regions";
    }

    @Override
    protected Picture getPicture() {
        return null;
    }

    @Override
    protected Potential getPotential() {
        Potential potential = new Potential();

        potential.addCondition("ai", "no");
        potential.addCondition("has_country_flag", "should_apply_acquire_claim_cb");

        return potential;
    }

    @Override
    protected Allow getAllow() {
        return new Allow();
    }

    @Override
    protected DecisionEffect getEffect() {

        DecisionEffect effect = new DecisionEffect();

        effect.addEffect("country_event", "2900");

        for (Region region: regions) {
            EffectScope randomOwned1 = ScriptingUtils.getEffectScope("random_owned");
            ConditionScope limit1 = ScriptingUtils.getConditionScope("limit");
            ConditionScope owner1 = ScriptingUtils.getConditionScope("owner");

            owner1.addCondition("has_country_flag", "claimed_" + region.getCode());

            for (int provinceId: region.getProvinces()) {
                owner1.addCondition("owns", Integer.toString(provinceId));
            }

            limit1.addCondition(owner1);
            randomOwned1.setLimit(limit1);
            randomOwned1.addEffect(ScriptingUtils.getEffectScope("empty_line_decisions_loc"));
            randomOwned1.addEffect(ScriptingUtils.getEffectScope("claim_status_owns_" + region.getCode() + "_loc"));
            effect.addEffect(randomOwned1);

            EffectScope randomOwned2 = ScriptingUtils.getEffectScope("random_owned");
            ConditionScope limit2 = ScriptingUtils.getConditionScope("limit");
            ConditionScope owner2 = ScriptingUtils.getConditionScope("owner");

            owner2.addCondition("has_country_flag", "claimed_" + region.getCode());

            ConditionScope or = ScriptingUtils.getORCondition();

            for (int provinceId: region.getProvinces()) {
                or.addCondition("owns", Integer.toString(provinceId), ScriptingUtils.getNOTCondition());
            }

            owner2.addCondition(or);
            limit2.addCondition(owner2);
            randomOwned2.setLimit(limit2);
            randomOwned2.addEffect(ScriptingUtils.getEffectScope("empty_line_decisions_loc"));
            randomOwned2.addEffect(ScriptingUtils.getEffectScope("claim_status_doesnt_own_" + region.getCode() + "_loc"));
            effect.addEffect(randomOwned2);

        }

        return effect;
    }
}
