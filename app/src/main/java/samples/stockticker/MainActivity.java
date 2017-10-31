package samples.stockticker;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samples.stockticker.models.StockPrices;

class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.current_price) TextView currentPrice;

    private Api api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        api = ApiProvider.createApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        api.daily("GOOGL")
                .enqueue(new Callback<StockPrices>() {
                    @Override
                    public void onResponse(@NonNull Call<StockPrices> call, @NonNull Response<StockPrices> response) {
                        Log.d(TAG, "response: " + response.body());
                    }

                    @Override
                    public void onFailure(@NonNull Call<StockPrices> call, @NonNull Throwable t) {

                    }
                });
    }
}
