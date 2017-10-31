package samples.stockticker;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends Activity {
    public static final String KEY_SYMBOL = "SYMBOL";

    @BindView(R.id.editText) EditText editText;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        editText.setText(PreferenceManager.getDefaultSharedPreferences(this).getString(SettingsActivity.KEY_SYMBOL, "GOOG"));
    }
    
    @OnClick(R.id.save_button)
    public void saveClicked() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit()
                .putString(KEY_SYMBOL, editText.getText().toString())
                .apply();
        finish();
    }
}
