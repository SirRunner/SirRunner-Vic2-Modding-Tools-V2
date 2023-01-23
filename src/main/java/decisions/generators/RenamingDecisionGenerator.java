package decisions.generators;

import decisions.nodes.Decision;
import decisions.nodes.Picture;
import decisions.nodes.PoliticalDecisions;
import decisions.nodes.Potential;
import decisions.reader.ProvinceRenamingCSVReader;
import decisions.reader.RegionRenamingCSVReader;
import decisions.renaming.ProvinceRenaming;
import decisions.renaming.RegionRenaming;
import decisions.writer.RenamingDecisionWriter;
import map.reader.RegionReader;
import map.regions.Region;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.paradox.scripting.ScriptingUtils;
import utils.paradox.scripting.conditions.BasicCondition;
import utils.paradox.scripting.conditions.Condition;
import utils.paradox.scripting.conditions.ConditionScope;
import utils.paradox.scripting.effects.BasicEffect;
import utils.paradox.scripting.effects.Effect;
import utils.paradox.scripting.effects.EffectScope;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class RenamingDecisionGenerator {
    protected String provinceCSVFilename;
    protected String regionsFilename;
    protected String regionsCSVFilename;
    protected String outputFilename;

    protected String forStartingDecisionEffectsOutfile;
    protected String decisionLocalizationOutfile;

    protected List<EffectScope> variableStartingValues = new ArrayList<>();
    protected Map<String, List<String>> decisionRegionToLocalization = new LinkedHashMap<>();

    public void setProvinceCSVFilename(String csvFilename) {
        this.provinceCSVFilename = csvFilename;
    }

    public void setRegionsFilename(String filename) {
        this.regionsFilename = filename;
    }

    public void setRegionsCSVFilename(String filename) {
        this.regionsCSVFilename = filename;
    }

    public void setOutputFilename(String filename) {
        this.outputFilename = filename;
    }

    public void setForStartingDecisionEffectsOutfile(String forStartingDecisionEffectsOutfile) {
        this.forStartingDecisionEffectsOutfile = forStartingDecisionEffectsOutfile;
    }

    public void setDecisionLocalizationOutfile(String decisionLocalizationOutfile) {
        this.decisionLocalizationOutfile = decisionLocalizationOutfile;
    }

    protected Decision getDecisionForRegionRenaming(PoliticalDecisions politicalDecisions, RegionRenaming regionRenaming) {
        for (Decision decision : politicalDecisions.getDecisions()) {
            if (StringUtils.equals(regionRenaming.getDecisionName(), decision.getName())) {
                return decision;
            }
        }

        Decision decision = new Decision();
        decision.setName(regionRenaming.getDecisionName());

        Picture picture = new Picture("renaming");
        decision.setPicture(picture);

        Condition allowCondition = ScriptingUtils.getCondition("war", "no");
        decision.getAllow().addCondition(allowCondition);

        politicalDecisions.addDecision(decision);

        return decision;
    }

    protected void updateCultureGroupings(Map<String, Set<String>> cultureToCultureGroups, Map<String, Set<String>> provinceCultureToCultureGroups) {
        for (String culture : provinceCultureToCultureGroups.keySet()) {
            /* If the "final" culture groups doesn't contain this culture, add it to the "final" cultures. */
            if (cultureToCultureGroups.containsKey(culture)) {
                Set<String> regionCultureGrouping = cultureToCultureGroups.get(culture);
                Set<String> provinceCultureGrouping = provinceCultureToCultureGroups.get(culture);

                /* If the cultures in the "final" mapping are the same as the province mapping, nothing to be done */
                if (!regionCultureGrouping.equals(provinceCultureGrouping)) {
                    /* Find the intersection and union without intersection. Update the current culture for both the region
                       and province to be the intersection, and remove the current culture from the sets of each culture
                       in the union without intersection */
                    Set<String> intersection = new HashSet<>(regionCultureGrouping);
                    intersection.retainAll(provinceCultureGrouping);

                    Set<String> unionWithoutIntersection = new HashSet<>(regionCultureGrouping);
                    unionWithoutIntersection.addAll(provinceCultureGrouping);
                    unionWithoutIntersection.removeAll(intersection);

                    cultureToCultureGroups.put(culture, intersection);
                    provinceCultureToCultureGroups.put(culture, intersection);

                    for (String otherCulture : unionWithoutIntersection) {
                        if (cultureToCultureGroups.containsKey(otherCulture)) {
                            cultureToCultureGroups.get(otherCulture).remove(culture);
                        }

                        if (provinceCultureToCultureGroups.containsKey(otherCulture)) {
                            provinceCultureToCultureGroups.get(otherCulture).remove(culture);
                        }
                    }
                }
            } else {
                cultureToCultureGroups.put(culture, provinceCultureToCultureGroups.get(culture));
            }
        }
    }

    protected Set<Set<String>> getFinalCultureGrouping(Map<String, Set<String>> regionNamesToCultureGroups, Map<Integer, ProvinceRenaming> idToProvinceRenamings, Set<Integer> provincesInRegion) {
        Map<String, Set<String>> cultureToCultureGroups = new HashMap<>();

        for (String name : regionNamesToCultureGroups.keySet()) {
            Set<String> cultureGrouping = regionNamesToCultureGroups.get(name);
            for (String culture : cultureGrouping) {
                cultureToCultureGroups.put(culture, new HashSet<>(cultureGrouping));
            }
        }

        for (int provinceId : provincesInRegion) {
            if (idToProvinceRenamings.containsKey(provinceId)) {
                Map<String, Set<String>> provinceNamesToCultureGroups = idToProvinceRenamings.get(provinceId).getNamesToCultureGroups();

                Map<String, Set<String>> provinceCultureToCultureGroups = new HashMap<>();

                for (String name : provinceNamesToCultureGroups.keySet()) {
                    Set<String> cultureGrouping = provinceNamesToCultureGroups.get(name);
                    for (String culture : cultureGrouping) {
                        provinceCultureToCultureGroups.put(culture, new HashSet<>(cultureGrouping));
                    }
                }

                updateCultureGroupings(cultureToCultureGroups, provinceCultureToCultureGroups);
            }
        }

        Set<Set<String>> cultureGroups = new HashSet<>();

        for (String cultureName : cultureToCultureGroups.keySet()) {
            cultureGroups.add(cultureToCultureGroups.get(cultureName));
        }

        return cultureGroups;
    }

    protected ConditionScope getCultureGroupAnd(Set<String> cultureGroups, int count, RegionRenaming regionRenaming) {
        ConditionScope cultureGroupAnd = ScriptingUtils.getANDCondition();
        cultureGroupAnd.setComment(cultureGroups.toString() + " - " + regionRenaming.getStartingName());

        /* Adds the is_culture_group parts of the condition */
        if (cultureGroups.size() == 1) {
            String name = cultureGroups.stream().findAny().get();
            Condition cultureGroup = ScriptingUtils.getCondition("is_culture_group", name);

            cultureGroupAnd.addCondition(cultureGroup);
        } else {
            ConditionScope orCultureGroup = ScriptingUtils.getORCondition();

            for (String cultureGroupName : cultureGroups) {
                Condition cultureGroup = ScriptingUtils.getCondition("is_culture_group", cultureGroupName);
                orCultureGroup.addCondition(cultureGroup);
            }

            cultureGroupAnd.addCondition(orCultureGroup);
        }

        /* Condition for the variable to not be this culture groups' */
        ConditionScope variableValueOr = ScriptingUtils.getORCondition();
        String variableName = regionRenaming.getVariableName();

        variableValueOr.addCondition(getLowerVariableCheck(variableName, count));
        variableValueOr.addCondition(getUpperVariableCheck(variableName, count));

        cultureGroupAnd.addCondition(variableValueOr);

        return cultureGroupAnd;
    }

    protected ConditionScope getLowerVariableCheck(String variableName, double value) {
        ConditionScope tagScope = ScriptingUtils.getConditionScope("FOR");
        ConditionScope notScope = ScriptingUtils.getNOTCondition();
        ConditionScope variableScope = ScriptingUtils.getConditionScope("check_variable");
        Condition whichCondition = ScriptingUtils.getCondition("which", variableName);
        Condition variableValue = ScriptingUtils.getCondition("value", Double.toString(value - 0.1));

        variableScope.addCondition(whichCondition);
        variableScope.addCondition(variableValue);
        notScope.addCondition(variableScope);
        tagScope.addCondition(notScope);

        return tagScope;
    }

    protected ConditionScope getUpperVariableCheck(String variableName, double value) {
        ConditionScope tagScope = ScriptingUtils.getConditionScope("FOR");
        ConditionScope variableScope = ScriptingUtils.getConditionScope("check_variable");
        Condition whichCondition = ScriptingUtils.getCondition("which", variableName);
        Condition variableValue = ScriptingUtils.getCondition("value", Double.toString(value + 0.1));

        variableScope.addCondition(whichCondition);
        variableScope.addCondition(variableValue);
        tagScope.addCondition(variableScope);

        return tagScope;
    }

    protected BasicCondition getOuterOrBlock(Decision decision) {
        Potential potential = decision.getPotential();
        if (potential.getConditions().isEmpty()) {
            ConditionScope outerOrBlock = ScriptingUtils.getORCondition();

            potential.addCondition(outerOrBlock);
        }
        // TODO: Throw a tantrum if there are multiple conditions directly in the potential

        return potential.getConditions().get(0);
    }

    protected EffectScope getEffectsForRegion(Set<String> cultureGroups, int count, RegionRenaming regionRenaming, List<ProvinceRenaming> provinceRenamings, Region region) {
        EffectScope randomOwnedScope = ScriptingUtils.getEffectScope("random_owned");
        randomOwnedScope.setComment(cultureGroups + " - " + regionRenaming.getStartingName());

        ConditionScope limitScope = getLimit(cultureGroups, region, regionRenaming, count);
        List<BasicEffect> renamingScopes = getRenamingScopes(cultureGroups, regionRenaming, provinceRenamings);
        EffectScope variableUpdateScope = getVariableUpdateScope(regionRenaming, count);

        randomOwnedScope.setLimit(limitScope);
        randomOwnedScope.setEffects(renamingScopes);
        randomOwnedScope.addEffect(variableUpdateScope);

        if (cultureGroups.contains(regionRenaming.getStartingCulture())) {
            EffectScope startingVariableScope = ScriptingUtils.getEffectScope("set_variable");
            startingVariableScope.setIndent(3);
            Effect startingVariableWhich = ScriptingUtils.getEffect("which", regionRenaming.getVariableName());
            Effect startingVariableValue = ScriptingUtils.getEffect("value", Integer.toString(count));

            startingVariableScope.addEffect(startingVariableWhich);
            startingVariableScope.addEffect(startingVariableValue);

            variableStartingValues.add(startingVariableScope);
        }

        if (!decisionRegionToLocalization.containsKey(regionRenaming.getDecisionRegion())) {
            List<String> localization = new ArrayList<>();
            localization.add(regionRenaming.getLocalizationName());
            localization.add(regionRenaming.getLocalizationDesc());

            decisionRegionToLocalization.put(regionRenaming.getDecisionRegion(), localization);
        }

        return randomOwnedScope;
    }

    protected EffectScope getVariableUpdateScope(RegionRenaming regionRenaming, int count) {
        EffectScope ownerScope = ScriptingUtils.getEffectScope("owner");
        EffectScope tagScope = ScriptingUtils.getEffectScope("FOR");
        EffectScope setVariableScope = ScriptingUtils.getEffectScope("set_variable");

        Effect whichEffect = ScriptingUtils.getEffect("which", regionRenaming.getVariableName());
        Effect valueEffect = ScriptingUtils.getEffect("value", Integer.toString(count));

        setVariableScope.addEffect(whichEffect);
        setVariableScope.addEffect(valueEffect);

        tagScope.addEffect(setVariableScope);

        ownerScope.addEffect(tagScope);

        return ownerScope;
    }

    protected List<BasicEffect> getRenamingScopes(Set<String> cultureGroups, RegionRenaming regionRenaming, List<ProvinceRenaming> provinceRenamings) {
        List<BasicEffect> renamingScopes = new ArrayList<>();

        String cultureName = cultureGroups.stream().findAny().get();

        if (StringUtils.isNotEmpty(regionRenaming.getNameForCultureGroup(cultureName))) {
            renamingScopes.add(getRegionRenameEffectScope(provinceRenamings.get(0).getProvinceId(), cultureName, regionRenaming));
        }

        for (ProvinceRenaming provinceRenaming : provinceRenamings) {
            if (StringUtils.isNotEmpty(provinceRenaming.getNameForCultureGroup(cultureName))) {
                renamingScopes.add(getProvinceRenamingEffectScope(provinceRenaming.getProvinceId(), cultureName, provinceRenaming));
            }
        }

        return renamingScopes;
    }

    protected EffectScope getRegionRenameEffectScope(int minProvinceId, String cultureName, RegionRenaming regionRenaming) {
        EffectScope regionRename = ScriptingUtils.getEffectScope(Integer.toString(minProvinceId));
        EffectScope stateScope = ScriptingUtils.getEffectScope("state_scope");
        Effect changeRegionNameScope = ScriptingUtils.getEffect("change_region_name", "\"" + regionRenaming.getNameForCultureGroup(cultureName) + "\"");

        stateScope.addEffect(changeRegionNameScope);
        regionRename.addEffect(stateScope);

        return regionRename;
    }

    protected EffectScope getProvinceRenamingEffectScope(int provinceId, String cultureName, ProvinceRenaming provinceRenaming) {
        EffectScope idScope = ScriptingUtils.getEffectScope(Integer.toString(provinceId));
        Effect changeProvinceNameScope = ScriptingUtils.getEffect("change_province_name", "\"" + provinceRenaming.getNameForCultureGroup(cultureName) + "\"");

        idScope.addEffect(changeProvinceNameScope);

        return idScope;
    }

    protected ConditionScope getLimit(Set<String> cultureGroups, Region region, RegionRenaming regionRenaming, int count) {
        ConditionScope limitScope = ScriptingUtils.getConditionScope("limit");

        ConditionScope ownerScope = ScriptingUtils.getConditionScope("owner");

        /* Adds the is_culture_group parts of the limit */
        if (cultureGroups.size() == 1) {
            String name = cultureGroups.stream().findAny().get();

            Condition isCultureGroupCondition = ScriptingUtils.getCondition("is_culture_group", name);
            ownerScope.addCondition(isCultureGroupCondition);
        } else {
            ConditionScope cultureGroupOr = ScriptingUtils.getORCondition();

            for (String name : cultureGroups) {
                Condition isCultureGroupCondition = ScriptingUtils.getCondition("is_culture_group", name);

                cultureGroupOr.addCondition(isCultureGroupCondition);
            }

            ownerScope.addCondition(cultureGroupOr);
        }

        Condition provinceIdCondition = ScriptingUtils.getCondition("province_id", Integer.toString(region.getLowestProvinceId()));

        ConditionScope variableCheckOr = ScriptingUtils.getORCondition();

        ConditionScope lowerVariableCheckOwnerScope = ScriptingUtils.getConditionScope("owner");
        String variableName = regionRenaming.getVariableName();
        lowerVariableCheckOwnerScope.addCondition(getLowerVariableCheck(variableName, count));

        ConditionScope upperVariableCheckOwnerScope = ScriptingUtils.getConditionScope("owner");
        upperVariableCheckOwnerScope.addCondition(getUpperVariableCheck(variableName, count));

        variableCheckOr.addCondition(lowerVariableCheckOwnerScope);
        variableCheckOr.addCondition(upperVariableCheckOwnerScope);

        limitScope.addCondition(ownerScope);
        limitScope.addCondition(provinceIdCondition);
        limitScope.addCondition(variableCheckOr);

        return limitScope;
    }

    protected void addRenamingForRegion(PoliticalDecisions politicalDecisions, RegionRenaming regionRenaming, Map<Integer, ProvinceRenaming> idToProvinceRenamings, Region region) {
        ConditionScope outerAndCondition = ScriptingUtils.getANDCondition();
        List<EffectScope> randomOwnedScopes = new ArrayList<>();

        Set<Set<String>> cultureGroupings = getFinalCultureGrouping(regionRenaming.getNamesToCultureGroups(), idToProvinceRenamings, region.getProvinces());

        int count = 0;

        // MAKING THE CONDITIONS FOR THE POTENTIAL AND THE EFFECTS
        ConditionScope middleOrCondition = ScriptingUtils.getORCondition();

        /* Generates the conditions for the culture group */
        for (Set<String> cultureGrouping : cultureGroupings) {
            middleOrCondition.addCondition(getCultureGroupAnd(cultureGrouping, count, regionRenaming));

            // MAKING THE EFFECTS

            List<Integer> provinceIds = region.getProvincesInOrder();
            List<ProvinceRenaming> provinceRenamings = new ArrayList<>();

            for (int provinceId : region.getProvinces()) {
                provinceRenamings.add(idToProvinceRenamings.get(provinceId));
            }

            randomOwnedScopes.add(getEffectsForRegion(cultureGrouping, count, regionRenaming, provinceRenamings, region));

            count++;
        }

        outerAndCondition.addCondition(middleOrCondition);

        /* Adds the owns conditions for the region */
        for (int provinceId : region.getProvinces()) {
            Condition ownsProvince = ScriptingUtils.getCondition("owns", Integer.toString(provinceId));

            outerAndCondition.addCondition(ownsProvince);
        }

        /* If we have no changes, don't make the decision */
        if (randomOwnedScopes.isEmpty()) {
            return;
        }

        Decision decision = getDecisionForRegionRenaming(politicalDecisions, regionRenaming);

        BasicCondition outerOrBlock = getOuterOrBlock(decision);

        if (outerOrBlock instanceof ConditionScope) {
            ConditionScope outerOrBlockScope = (ConditionScope) outerOrBlock;

            outerOrBlockScope.addCondition(outerAndCondition);
        } else {
            Logger.error("Outer or block is not a condition scope");
        }

        for (BasicEffect effect : randomOwnedScopes) {
            if (effect instanceof EffectScope) {
                decision.getDecisionEffect().addEffect(effect);
            } else {
                Logger.error("\"random_owned block\" is not an EffectScope");
                Logger.print(effect.toString());
            }
        }
    }

    protected void run() throws Exception {
        ProvinceRenamingCSVReader provinceRenamingReader = new ProvinceRenamingCSVReader(provinceCSVFilename);
        RegionReader regionReader = new RegionReader(regionsFilename);
        RegionRenamingCSVReader regionRenamingCSVReader = new RegionRenamingCSVReader(regionsCSVFilename);

        Map<Integer, ProvinceRenaming> idToProvinceRenamings = provinceRenamingReader.readFile();
        Logger.info("Read " + idToProvinceRenamings.size() + " province renamings");

        Map<String, Region> codeToRegion = regionReader.readFile();
        Set<String> allRegionCodes = codeToRegion.keySet();
        Logger.info("Read " + allRegionCodes.size() + " regions");

        Map<String, RegionRenaming> codeToRegionRenaming = regionRenamingCSVReader.readFile();
        Logger.info("Read " + codeToRegionRenaming.size() + " region renamings groups");

        Map<String, PoliticalDecisions> pdRegionToPoliticalDecisions = new HashMap<>();

        // TODO: Make sure all provinces are handled by looping over the region renamings

        for (String regionCode : codeToRegionRenaming.keySet()) {
            if (!allRegionCodes.contains(regionCode)) {
                Logger.error(regionCode + " is in the region renaming file, but is not a defined region");
                continue;
            }

            RegionRenaming regionRenaming = codeToRegionRenaming.get(regionCode);
            String pdRegion = regionRenaming.getPoliticalDecisionsRegion();

            if (!pdRegionToPoliticalDecisions.containsKey(pdRegion)) {
                PoliticalDecisions pd = new PoliticalDecisions();
                pd.setComment(pdRegion);
                pdRegionToPoliticalDecisions.put(pdRegion, pd);
            }

            PoliticalDecisions politicalDecisions = pdRegionToPoliticalDecisions.get(pdRegion);

            addRenamingForRegion(politicalDecisions, regionRenaming, idToProvinceRenamings, codeToRegion.get(regionCode));
        }

        RenamingDecisionWriter writer = new RenamingDecisionWriter(outputFilename, pdRegionToPoliticalDecisions);
        writer.writeFile();

        /* Write the starting effects output */
        try (BufferedWriter startingEffectsWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(forStartingDecisionEffectsOutfile), StandardCharsets.UTF_8))) {
            for (EffectScope startingEffect : variableStartingValues) {
                startingEffectsWriter.write(startingEffect.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Write the decision localisation file */
        try (BufferedWriter localisationWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(decisionLocalizationOutfile), StandardCharsets.UTF_8))) {
            for (String decisionName : decisionRegionToLocalization.keySet()) {
                for (String localization : decisionRegionToLocalization.get(decisionName)) {
                    localisationWriter.write(localization);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            RenamingDecisionGenerator generator = new RenamingDecisionGenerator();

            generator.setProvinceCSVFilename("C:\\Users\\Harrison Greene\\Downloads\\Provinces - Renaming (Province).csv");
            generator.setRegionsFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/map/region.txt");
            generator.setRegionsCSVFilename("C:\\Users\\Harrison Greene\\Downloads\\Provinces - Renaming (Region).csv");
            generator.setOutputFilename("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/decisions/Renaming.txt");

            Path source = Paths.get(Paths.get(System.getProperty("user.dir")).toString(), "src", "main", "java", "decisions", "generators");

            String startingEffectsFilename = Paths.get(source.toString(), "Starting Effects.txt").toString();
            String localizationFilename = Paths.get(source.toString(), "Localization.txt").toString();

            generator.setForStartingDecisionEffectsOutfile(startingEffectsFilename);
            generator.setDecisionLocalizationOutfile(localizationFilename);

            generator.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
