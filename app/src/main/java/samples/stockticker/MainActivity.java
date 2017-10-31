package samples.stockticker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import samples.stockticker.models.DayPrice;
import samples.stockticker.models.StockPrices;

public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.current_price) TextView currentPrice;
    @BindView(R.id.price_change) TextView priceChange;
    @BindView(R.id.percentage_change) TextView percentageChange;
    @BindView(R.id.progress_bar) ProgressBar progressBar;
    @BindView(R.id.chart) LineChart chart;

    private Api api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        api = ApiProvider.createApi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchPrices();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int id, MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onMenuItemSelected(id, item);
    }

    private void fetchPrices() {
        final String symbol = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SettingsActivity.KEY_SYMBOL, "GOOG");
        getActionBar().setTitle(symbol);
        api.daily(symbol)
                .enqueue(new Callback<StockPrices>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(@NonNull Call<StockPrices> call, @NonNull Response<StockPrices> response) {
                        try {
                            NumberFormat numberFormat = NumberFormat.getNumberInstance();
                            DayPrice price = response.body().timeSeriesDaily.values().iterator().next();
                            double difference = price.close - price.open;
                            currentPrice.setText("$" + numberFormat.format(price.close));
                            int color;
                            String sign;
                            if (difference > 0) {
                                color = getResources().getColor(R.color.green_number);
                                sign = "+";
                            } else {
                                color = getResources().getColor(R.color.red_number);
                                sign = "-";
                            }
                            priceChange.setText(sign + numberFormat.format(difference));
                            percentageChange.setText(String.format("%s%.2f%%", sign, difference / price.open * 100));
                            currentPrice.setTextColor(color);
                            percentageChange.setTextColor(color);
                            priceChange.setTextColor(color);

                            // setup graph
                            List<Entry> entries = new ArrayList<Entry>();
                            int max = response.body().timeSeriesDaily.size();
                            for (Map.Entry<String, DayPrice> mapEntry : response.body().timeSeriesDaily.entrySet()) {

                                entries.add(0, new Entry(max - entries.size(), (float) mapEntry.getValue().close));
                            }
                            LineDataSet dataSet = new LineDataSet(entries, "USD");
                            LineData lineData = new LineData(dataSet);
                            chart.setData(lineData);
                            chart.invalidate(); // refresh
                            chart.setVisibility(View.VISIBLE);

                            progressBar.setVisibility(View.GONE);
                        } catch (NullPointerException e) {
                            showDialog();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<StockPrices> call, @NonNull Throwable t) {
                        showDialog();
                    }
                });
    }

    private void showDialog() {
        final String symbol = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(SettingsActivity.KEY_SYMBOL, "GOOG");

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Error")
                .setMessage("Error Processing Request for " + symbol)
                .setPositiveButton("Set Symbol", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Try Again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fetchPrices();
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
