package savefile;

import savefile.objects.*;

import java.time.LocalDate;
import java.util.List;

public class V2SaveFile {
    private LocalDate date;
    private String playerTag;
    private int governmentId;
    private boolean automateTrade;
    private int automateSliders;
    private int rebel;
    private int unit;
    private int state;
    private Flags globalFlags;
    private GamePlaySettings gamePlaySettings;
    private LocalDate startdate;
    private int startPopIndex;
    private WorldMarket worldMarket;
    private boolean greatWarsEnabled;
    private boolean worldWarsEnabled;
    private GoodsPool overseasPenalty;
    private GoodsPool unitCost;
    private DoubleList budgetBalance;
    private PlayerMonthlyPopGrowth playerMonthlyPopGrowth;
    private String playerMonthlyPopGrowthTag;
    private LocalDate playerMonthlyPopGrowthDate;
    private List<IdeologyEnabledDate> ideologyEnabledDates;
    private ID id; // Absolutely no idea
    private FiredEvents firedEvents;
    private List<ProvinceSaveState> provinceStates;
    private List<CountrySaveState> countryStates;
    private List<RebelFaction> rebelFactions;
    private Diplomacy activeRelationships;
    private Combat activeCombats;
    private List<War> activeWars;
    private List<War> previousWars;
    private IntegerList inventions; // Absolutely no idea
    private IntegerList greatNations;
    private IntegerList outliner;
    private NewsCollector newsCollector;
    private CrisisManager crisisManager;
    private List<RegionSaveState> regionStates;
}
