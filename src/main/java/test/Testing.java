package test;

import common.Ideology;
import common.readers.IdeologyReader;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Testing {
    public static void main(String[] args) throws Exception {

        List<Ideology.IdeologyGroup> ideologyGroups = new IdeologyReader("C:/Program Files (x86)/Steam/steamapps/common/Victoria 2/mod/TTA/common/ideologies.txt").readFile();

        System.out.println( "promotion_ideologies;Promote Interest Groups (only affects election results)");

        for (Ideology.IdeologyGroup ideologyGroup : ideologyGroups) {
            for (Ideology ideology : ideologyGroup.getIdeologies()) {
                System.out.printf("promote_%s;Promote %s;x\n", ideology.getName(), Arrays.stream(StringUtils.split(ideology.getName(),"_")).map(word -> {
                    if (Arrays.asList("of","the").contains(word)) {
                        return word;
                    }

                    return StringUtils.capitalize(word);
                }).collect(Collectors.joining(" ")));
            }
        }

        System.out.println( "demotion_ideologies;Reduce Interest Groups (only affects election results)");

        for (Ideology.IdeologyGroup ideologyGroup : ideologyGroups) {
            for (Ideology ideology : ideologyGroup.getIdeologies()) {
                System.out.printf("demote_%s;Promote %s;x\n", ideology.getName(), Arrays.stream(StringUtils.split(ideology.getName(),"_")).map(word -> {
                    if (Arrays.asList("of","the").contains(word)) {
                        return word;
                    }

                    return StringUtils.capitalize(word);
                }).collect(Collectors.joining(" ")));
            }
        }

    }
}
