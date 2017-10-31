package samples.stockticker.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vedant on 31/10/17.
 */

class DayPrice {

    @SerializedName("1. open")
    @Expose
    public String open;
    @SerializedName("2. high")
    @Expose
    public String high;
    @SerializedName("3. low")
    @Expose
    public String low;
    @SerializedName("4. close")
    @Expose
    public String close;
    @SerializedName("5. volume")
    @Expose
    public String volume;
}
