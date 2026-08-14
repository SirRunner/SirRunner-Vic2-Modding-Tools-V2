package savefile.objects;

import java.util.HashMap;
import java.util.Map;

public class GoodsPool {
    Map<String, Double> goodsToValue;

    public GoodsPool() {
        init();
    }

    public void init() {
        if (goodsToValue == null) {
            goodsToValue = new HashMap<>();
        }
    }

    public Map<String, Double> getGoodsToValue() {
        return goodsToValue;
    }

    public void setGoodsToValue(Map<String, Double> goodsToValue) {
        this.goodsToValue = goodsToValue;
    }

    public void addGoodsToValue(String good, double value) {
        this.goodsToValue.put(good, value);
    }
}
