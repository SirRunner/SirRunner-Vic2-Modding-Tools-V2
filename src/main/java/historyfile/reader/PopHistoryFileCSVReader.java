package historyfile.reader;

import com.opencsv.CSVReaderHeaderAware;
import historyfile.pops.BasePopHistoryFile;
import historyfile.pops.PopHistoryFile;
import historyfile.pops.religions.CultureToReligion;
import org.apache.commons.lang3.StringUtils;
import utils.Logger;
import utils.baseclasses.BaseReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PopHistoryFileCSVReader extends BaseReader {
    protected final int TOTAL_POP_MULTIPLIER = 20;
    protected final double error = 0.000001;

    public PopHistoryFileCSVReader(File file) {
        super(file);
    }

    public PopHistoryFileCSVReader(String filename) {
        super(filename);
    }

    public Map<String, Map<Integer, PopHistoryFile>> readFile() throws Exception {
        if (file == null) {
            throw new Exception("file was not defined");
        }

        Map<String, Map<Integer, PopHistoryFile>> fileCodeToMapOfIdToPopHistory = new HashMap<>();

        try (CSVReaderHeaderAware reader = new CSVReaderHeaderAware(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            Logger.info("Reading pop history from " + file.getName());

            Map<String, String> line = reader.readMap();

            while (line != null) {
                PopHistoryFile popHistory = getPopHistory(line);

                if (popHistory != null) {
                    updatePopHistoryMap(line, fileCodeToMapOfIdToPopHistory, popHistory);
                }

                line = reader.readMap();
            }
        }

        return fileCodeToMapOfIdToPopHistory;
    }

    protected PopHistoryFile getPopHistory(Map<String, String> line) {
        PopHistoryFile popHistory = new PopHistoryFile();
        int totalPop = 0;
        double totalUsed = 0;
        Map<String, PopBreakdown> popTypetoPopBreakdown = new HashMap<>();

        for (String key : line.keySet()) {
            String value = line.get(key);

            if (PopHistoryFile.HANDLED_COLUMNS.contains(key)) {
                popHistory.setByName(key, value);
                continue;
            }

            if (StringUtils.equalsIgnoreCase(key, PopHistoryFile.TOTAL_POP)) {
                /* Some provinces don't have pops by design, are have the total pops marked as "-" */
                if (StringUtils.equalsIgnoreCase(value, PopHistoryFile.DEFAULT)) {
                    continue;
                }

                /* Multiply by the multiplier to have a usable number of pops in the game */
                totalPop = TOTAL_POP_MULTIPLIER * Integer.parseInt(value);
            } else if (StringUtils.equalsIgnoreCase(key, PopHistoryFile.FILENAME)) {
                /* Handled later. Unusable at this time */
                continue;
            } else if (StringUtils.endsWith(key, PopHistoryFile.BREAKDOWN_SUFFIX)) {
                /* Handled when the poptype's main column is processed */
                continue;
            } else if (!line.containsKey(key + PopHistoryFile.BREAKDOWN_SUFFIX)) {
                Logger.error("Pop " + key + " listed with no breakdown column");
            } else {
                PopBreakdown breakdown = new PopBreakdown();

                if (StringUtils.equalsIgnoreCase(value, PopHistoryFile.DEFAULT)) {
                    breakdown.setPercentageOfProvince(0.0);
                    continue;
                } else if (StringUtils.equalsIgnoreCase(value, PopHistoryFile.REST)) {
                    breakdown.setPercentageOfProvince(-1.0);
                } else {
                    try {
                        /* The csv stores 100% as 100. For the sake of later match, convert the percent to the decimal equivalent */
                        double percentage = Double.parseDouble(value) / 100;

                        breakdown.setPercentageOfProvince(percentage);
                        totalUsed += percentage;
                    } catch (Exception e) {
                        Logger.error("Given percentage is not a number: " + value);
                        continue;
                    }
                }

                String cultureBreakdown = line.get(key + PopHistoryFile.BREAKDOWN_SUFFIX);
                String[] tokens = StringUtils.split(cultureBreakdown);

                if (tokens.length % 2 != 0) {
                    Logger.error("Breakdown is malformed; odd length when it should be even for id " + line.get(PopHistoryFile.ID) + " : " + cultureBreakdown);
                    System.out.println(line.get("id"));
                    continue;
                }

                for (int i = 0; i < tokens.length; i += 2) {
                    try {
                        /* The csv stores 100% as 100. For the sake of later match, convert the percent to the decimal equivalent */
                        double percentage = Double.parseDouble(tokens[i]) / 100;
                        String culture = tokens[i + 1];

                        breakdown.addCulturePercentOfPop(culture, percentage);
                    } catch (Exception e) {
                        Logger.error("Issue parsing value " + cultureBreakdown);
                    }
                }

                if (Math.abs(breakdown.getPercentageSumOfBreakdown() - 1.0) > error) {
                    double test = Math.abs(breakdown.getPercentageSumOfBreakdown() - 1.0);
                    Logger.error("Sum of pop culture breakdown != 100 %: " + cultureBreakdown);
                }

                popTypetoPopBreakdown.put(key, breakdown);
            }
        }

        /* Now that we have processed all of the poptypes that will be in the province, the province can be updated with the right pop numbers
         * Each poptype that exists should exist in popTypeToPopBreakdown, and any that don't (marked with "-") should not */

        for (String poptype : popTypetoPopBreakdown.keySet()) {
            PopBreakdown breakdown = popTypetoPopBreakdown.get(poptype);

            double provincePercent = breakdown.getPercentageOfProvince();

            /* For poptypes that hit the "rest" keyword, set their province percentage now*/
            if (Math.abs(provincePercent + 1.0) < error) {
                breakdown.setPercentageOfProvince(1 - totalUsed);
            }

            double percentageOfProvince = breakdown.getPercentageOfProvince();

            for (String culture : breakdown.getCulturePercentOfPop().keySet()) {
                String religion = CultureToReligion.getRaceForCulture(culture);

                /* If the culture is not in CultureToReligion, let the user know and default to men */
                if (StringUtils.isEmpty(religion)) {
                    Logger.error("Unable to find race (religion) for " + culture + ". Defaulting to 'men'");
                    religion = "men";
                }

                double culturePercentageOfPop = breakdown.getCulturePercentOfPop().get(culture);

                double totalInPop = totalPop * percentageOfProvince * culturePercentageOfPop;

                BasePopHistoryFile.PopHistoryPop pop = new BasePopHistoryFile.PopHistoryPop();

                pop.setType(poptype);
                pop.setCulture(culture);
                pop.setReligion(religion);
                pop.setSize(totalInPop);

                popHistory.addPop(pop);
            }
        }

        return popHistory;
    }

    protected void updatePopHistoryMap(Map<String, String> line, Map<String, Map<Integer, PopHistoryFile>> fileCodeToMapOfIdToPopHistory, PopHistoryFile popHistory) {
        String filename = line.get(PopHistoryFile.FILENAME);
        int provinceId = popHistory.getProvinceId();

        if (filename == null) {
            Logger.error("No filename listed in " + line);
            return;
        }

        if (provinceId == 0) {
            Logger.error("Province id is 0 in " + line);
        }

        if (fileCodeToMapOfIdToPopHistory.containsKey(filename)) {
            Map<Integer, PopHistoryFile> idToPopHistory = fileCodeToMapOfIdToPopHistory.get(filename);

            if (idToPopHistory.containsKey(provinceId)) {
                Logger.error(filename + " contains multiple entries for id " + provinceId);
                return;
            }

            idToPopHistory.put(provinceId, popHistory);
        } else {
            Map<Integer, PopHistoryFile> idToPopHistory = new HashMap<>();

            idToPopHistory.put(provinceId, popHistory);

            fileCodeToMapOfIdToPopHistory.put(filename, idToPopHistory);
        }
    }

    public static class PopBreakdown {
        protected Double percentageOfProvince;
        protected Map<String, Double> culturePercentOfPop;

        public PopBreakdown() {
            this.culturePercentOfPop = new HashMap<>();
        }

        public Double getPercentageOfProvince() {
            return percentageOfProvince;
        }

        public void setPercentageOfProvince(Double percentageOfProvince) {
            this.percentageOfProvince = percentageOfProvince;
        }

        public Map<String, Double> getCulturePercentOfPop() {
            return culturePercentOfPop;
        }

        public void setCulturePercentOfPop(Map<String, Double> culturePercentOfPop) {
            this.culturePercentOfPop = culturePercentOfPop;
        }

        public void addCulturePercentOfPop(String culture, double percentage) {
            if (culturePercentOfPop == null) {
                culturePercentOfPop = new HashMap<>();
            }

            culturePercentOfPop.put(culture, percentage);
        }

        public double getPercentageSumOfBreakdown() {
            double sum = 0.0;

            for (String culture: culturePercentOfPop.keySet()) {
                sum += culturePercentOfPop.get(culture);
            }

            return sum;
        }
    }
}
