package map.parsing;

import map.regions.Region;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;
import utils.paradox.parsing.ParadoxParsingUtils;
import utils.paradox.parsing.WordParser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegionsParser extends BaseReader {

    public RegionsParser(File file) {
        super(file);
    }

    @Override
    public List<Region> readFile() throws Exception {
        if (getFile() == null) {
            throw new Exception("No definitions file set");
        }

        // Not using ParadoxParser has that is geared towards things like decisions and events. The structure of the regions file is just different enough to cause issues (due to the fact that we cannot represent the province ids as nodes)
        WordParser parser = new WordParser();
        List<String> words = parser.getWords(getFile());

        return getRegions(words);
    }

    protected List<Region> getRegions(List<String> words) {

        List<Region> regions = new ArrayList<>();
        Region region = null;

        for (String word : words) {
            if (StringUtils.startsWith(word, ParadoxParsingUtils.COMMENT_START)) {
                if (regions.isEmpty()) {
                    continue;
                }

                regions.get(regions.size() - 1).appendComment(word);
            } else if (region == null && !StringUtils.equalsAnyIgnoreCase(word, "={}")) {
                region = new Region();
                region.setCode(word);
            } else if (StringUtils.equals(word, ParadoxParsingUtils.DEFINES)) {
                // do nothing
            } else if (StringUtils.equals(word, ParadoxParsingUtils.OPEN_BLOCK)) {
                // do nothing
            } else if (region != null && StringUtils.isNumeric(word)) {
                region.addProvince(word);
            } else if (StringUtils.equals(word, ParadoxParsingUtils.CLOSE_BLOCK)) {
                if (region != null) {
                    regions.add(region);
                }

                region = null;
            } else {
                Logger.error("Failed to correctly handle word " + word);
            }
        }

        return regions;

    }

}
