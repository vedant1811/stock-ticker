package samples.stockticker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import samples.stockticker.models.StockPrices;

/**
 * Created by vedant on 31/10/17.
 */

public interface Api {
    // https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&apikey=TE0ARFPBN00QDMRV&symbol=MSFT&interval=60min
    @GET("?function=TIME_SERIES_DAILY&apikey=TE0ARFPBN00QDMRV")
    Call<StockPrices> daily(@Query("symbol") String symbol);
}
