package cat.flx.listsandadapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sample2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[2]);

        List<Map<String, String>> data = new ArrayList<>();
        String[] items = getResources().getStringArray(R.array.items3);
        for(int i = 0; i < items.length; i += 2) {
            Map<String, String> item = new HashMap<>();
            item.put("shortname", items[i]);
            item.put("fullname", items[i + 1]);
            data.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, data,
                android.R.layout.simple_list_item_2,
                new String[] { "shortname", "fullname" },
                new int[] { android.R.id.text1, android.R.id.text2 });
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
