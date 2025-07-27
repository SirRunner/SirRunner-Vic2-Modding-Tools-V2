package test;

import common.Culture;
import common.readers.CultureReader;
import events.generators.MilitaryLeaderRecruitmentGenerator;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;

public class Testing {
    public static void main(String[] args) throws Exception {
        List<Culture.CultureGroup> cultureGroups = new CultureReader("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\common\\cultures.txt").readFile();
        cultureGroups.removeIf(cultureGroup -> StringUtils.equalsIgnoreCase(cultureGroup.getName(), "Banker") || StringUtils.equalsIgnoreCase(cultureGroup.getName(), "Religions") || StringUtils.equalsIgnoreCase(cultureGroup.getName(), "good_evil") || StringUtils.endsWith(cultureGroup.getName(), "_culture"));

        LinkedHashMap<String, Culture.CultureGroup> usableCultureGroups = new LinkedHashMap<>();

        for (Culture.CultureGroup group : cultureGroups) {
            /* Northern Dunedain/Gondor split + Druedain/Wildmen split  */
            if (group.getName().equalsIgnoreCase("dunedain")) {
                usableCultureGroups.put(MilitaryLeaderRecruitmentGenerator.GONDORIAN, group);
            }
            if (group.getName().equalsIgnoreCase("wildmen")) {
                usableCultureGroups.put(MilitaryLeaderRecruitmentGenerator.DRUEDAIN, group);
            }

            /* Northmen/Gramavuld Northmen + Dwarves/Wicked Dwarves + Uruk Hai/Mordorian/Northern Goblins use the same names  */
            if (!group.getName().equalsIgnoreCase("gramavuld_northmen") && !group.getName().equalsIgnoreCase("wicked_dwarves") && !group.getName().equalsIgnoreCase("uruk_hai") && !group.getName().equalsIgnoreCase("northern_goblins")) {
                usableCultureGroups.put(group.getName(), group);
            }
        }

        MilitaryLeaderRecruitmentGenerator generator = new MilitaryLeaderRecruitmentGenerator("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Victoria 2\\mod\\TTA\\events\\Military Leader Recruitment.txt", "Military Leader Events", 40000, 40000 + ((usableCultureGroups.size() - 1) * 2) + 1, usableCultureGroups);
        generator.generate();
    }
}
