package cat.flx.listsandadapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class Sample3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[3]);

        Studies studies = new Studies();
        studies.loadData(this, R.raw.studies);
        List<Map<String, Object>> data = studies.toListOfMaps();
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                R.layout.listview_item,
                new String[] { "title", "fullTitle", "hours", "teacher", "photo" },
                new int[] { R.id.txtShortname, R.id.txtFullname, R.id.txtHours,
                        R.id.txtTeacher, R.id.imgView });
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
