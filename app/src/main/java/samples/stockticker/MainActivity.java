package samples.stockticker;

import android.app.Activity;
import android.os.Bundle;

import org.jetbrains.annotations.Nullable;

class MainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
