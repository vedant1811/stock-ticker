package samples.stockticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.LinkedHashMap;

/**
 * Created by vedant on 31/10/17.
 */

public class StockPrices {
    @SerializedName("Time Series (Daily)")
    @Expose
    public LinkedHashMap<String, DayPrice> timeSeriesDaily;
}
