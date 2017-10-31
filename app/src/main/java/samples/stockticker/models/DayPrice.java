package samples.stockticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vedant on 31/10/17.
 */

public class DayPrice {

    @SerializedName("1. open")
    @Expose
    public double open;
    @SerializedName("2. high")
    @Expose
    public double high;
    @SerializedName("3. low")
    @Expose
    public double low;
    @SerializedName("4. close")
    @Expose
    public double close;
    @SerializedName("5. volume")
    @Expose
    public int volume;
}
