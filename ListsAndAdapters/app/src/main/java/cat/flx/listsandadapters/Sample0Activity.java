package cat.flx.listsandadapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Sample0Activity extends AppCompatActivity {
    public static final String[] items = {
            "DAM-M01 SIN",
            "DAM-M02 GBD",
            "DAM-M03 PRO",
            "DAM-M04 LMS",
            "DAM-M05 EDE",
            "DAM-M06 ADA",
            "DAM-M07 INT",
            "DAM-M08 PMM",
            "DAM-M09 PSP",
            "DAM-M10 SGE",
            "DAM-M11 FOL",
            "DAM-M12 EIE",
            "DAM-M13 PRJ",
            "DAM-M14 FCT",
            "DAM-M15 ANG"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[0]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        ListView listView = findViewById(R.id.listView);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Sample0Activity.this,
                        "Position: " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
