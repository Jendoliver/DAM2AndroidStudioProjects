package cat.flx.listsandadapters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private SparseArray<Class<?>> samples;

    public static Object[] SAMPLES = {
            R.id.btnSample0, Sample0Activity.class,
            R.id.btnSample1, Sample1Activity.class,
            R.id.btnSample2, Sample2Activity.class,
            R.id.btnSample3, Sample3Activity.class,
            R.id.btnSample4, Sample4Activity.class,
            R.id.btnSample5, Sample5Activity.class,
            R.id.btnSample6, Sample6Activity.class,
            R.id.btnSample7, Sample7Activity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        samples = new SparseArray<>();
        for(int i = 0; i < SAMPLES.length; i += 2) {
            int btnId = (int)SAMPLES[i];
            Class<?> activityClass = (Class<?>) SAMPLES[i + 1];
            Button btn = MainActivity.this.findViewById(btnId);
            btn.setOnClickListener(MainActivity.this);
            samples.put(btnId, activityClass);
        }
    }

    @Override
    public void onClick(View view) {
        Class<?> activity = samples.get(view.getId());
        if (activity == null) return;
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
