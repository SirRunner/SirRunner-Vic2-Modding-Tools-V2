package savefile.objects;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WorldMarket {
    private GoodsPool worldMarketPool;
    private GoodsPool pricePool;
    private GoodsPool lastPriceHistory;
    private GoodsPool supplyPool;
    private GoodsPool lastSupplyPool;
    private List<GoodsPool> priceHistories;
    private LocalDate priceHistoryLastUpdate;
    private GoodsPool priceChange;
    private GoodsPool discoveredGoods;
    private GoodsPool actualSold;
    private GoodsPool actualSoldWorld;
    private GoodsPool realDemand;
    private GoodsPool demand;
    private GoodsPool playerBalance;
    private List<GoodsPool> playerPopsConsumptionCache;

    public WorldMarket() {
        init();
    }

    public void init() {
        if (worldMarketPool == null) {
            worldMarketPool = new GoodsPool();
        }
        if (pricePool == null) {
            pricePool = new GoodsPool();
        }
        if (lastPriceHistory == null) {
            lastPriceHistory = new GoodsPool();
        }
        if (supplyPool == null) {
            supplyPool = new GoodsPool();
        }
        if (lastSupplyPool == null) {
            lastSupplyPool = new GoodsPool();
        }
        if (priceHistories == null) {
            priceHistories = new ArrayList<>();
        }
        if (priceChange == null) {
            priceChange = new GoodsPool();
        }
        if (discoveredGoods == null) {
            discoveredGoods = new GoodsPool();
        }
        if (actualSold == null) {
            actualSold = new GoodsPool();
        }
        if (actualSoldWorld == null) {
            actualSoldWorld = new GoodsPool();
        }
        if (realDemand == null) {
            realDemand = new GoodsPool();
        }
        if (demand == null) {
            demand = new GoodsPool();
        }
        if (playerBalance == null) {
            playerBalance = new GoodsPool();
        }
        if (playerPopsConsumptionCache == null) {
            playerPopsConsumptionCache = new ArrayList<>();
        }
    }

    public GoodsPool getWorldMarketPool() {
        return worldMarketPool;
    }

    public void setWorldMarketPool(GoodsPool worldMarketPool) {
        this.worldMarketPool = worldMarketPool;
    }

    public GoodsPool getPricePool() {
        return pricePool;
    }

    public void setPricePool(GoodsPool pricePool) {
        this.pricePool = pricePool;
    }

    public GoodsPool getLastPriceHistory() {
        return lastPriceHistory;
    }

    public void setLastPriceHistory(GoodsPool lastPriceHistory) {
        this.lastPriceHistory = lastPriceHistory;
    }

    public GoodsPool getSupplyPool() {
        return supplyPool;
    }

    public void setSupplyPool(GoodsPool supplyPool) {
        this.supplyPool = supplyPool;
    }

    public GoodsPool getLastSupplyPool() {
        return lastSupplyPool;
    }

    public void setLastSupplyPool(GoodsPool lastSupplyPool) {
        this.lastSupplyPool = lastSupplyPool;
    }

    public List<GoodsPool> getPriceHistories() {
        return priceHistories;
    }

    public void setPriceHistories(List<GoodsPool> priceHistories) {
        this.priceHistories = priceHistories;
    }

    public void addPriceHistory(GoodsPool priceHistory) {
        this.priceHistories.add(priceHistory);
    }

    public LocalDate getPriceHistoryLastUpdate() {
        return priceHistoryLastUpdate;
    }

    public void setPriceHistoryLastUpdate(LocalDate priceHistoryLastUpdate) {
        this.priceHistoryLastUpdate = priceHistoryLastUpdate;
    }

    public GoodsPool getPriceChange() {
        return priceChange;
    }

    public void setPriceChange(GoodsPool priceChange) {
        this.priceChange = priceChange;
    }

    public GoodsPool getDiscoveredGoods() {
        return discoveredGoods;
    }

    public void setDiscoveredGoods(GoodsPool discoveredGoods) {
        this.discoveredGoods = discoveredGoods;
    }

    public GoodsPool getActualSold() {
        return actualSold;
    }

    public void setActualSold(GoodsPool actualSold) {
        this.actualSold = actualSold;
    }

    public GoodsPool getActualSoldWorld() {
        return actualSoldWorld;
    }

    public void setActualSoldWorld(GoodsPool actualSoldWorld) {
        this.actualSoldWorld = actualSoldWorld;
    }

    public GoodsPool getRealDemand() {
        return realDemand;
    }

    public void setRealDemand(GoodsPool realDemand) {
        this.realDemand = realDemand;
    }

    public GoodsPool getDemand() {
        return demand;
    }

    public void setDemand(GoodsPool demand) {
        this.demand = demand;
    }

    public GoodsPool getPlayerBalance() {
        return playerBalance;
    }

    public void setPlayerBalance(GoodsPool playerBalance) {
        this.playerBalance = playerBalance;
    }

    public List<GoodsPool> getPlayerPopsConsumptionCache() {
        return playerPopsConsumptionCache;
    }

    public void setPlayerPopsConsumptionCache(List<GoodsPool> playerPopsConsumptionCache) {
        this.playerPopsConsumptionCache = playerPopsConsumptionCache;
    }

    public void addPlayerPopsConsumptionCache(GoodsPool playerPopsConsumption) {
        this.playerPopsConsumptionCache.add(playerPopsConsumption);
    }
}
