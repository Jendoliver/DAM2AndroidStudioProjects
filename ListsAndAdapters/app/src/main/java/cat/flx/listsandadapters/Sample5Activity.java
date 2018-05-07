package cat.flx.listsandadapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Sample5Activity extends AppCompatActivity {

    class StudyAdapter extends BaseAdapter {

        Context context;
        Studies studies;

        StudyAdapter(Context context, Studies studies) {
            super();
            this.context = context;
            this.studies = studies;
        }
        @Override public int getCount() { return studies.size(); }
        @Override public Object getItem(int position) { return studies.get(position); }
        @Override public long getItemId(int position) {
            Study study = studies.get(position);
            return study.id;
        }
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.listview_item, null);
            TextView txtTitle = view.findViewById(R.id.txtShortname);
            TextView txtFullTitle = view.findViewById(R.id.txtFullname);
            TextView txtTeacher = view.findViewById(R.id.txtTeacher);
            TextView txtHours = view.findViewById(R.id.txtHours);
            ImageView imgView = view.findViewById(R.id.imgView);
            Study study = studies.get(position);
            txtTitle.setText(study.title);
            txtFullTitle.setText(study.fullTitle);
            txtHours.setText(getString(R.string.hours, study.hours));
            txtTeacher.setText(study.teacher);
            imgView.setImageDrawable(study.photo);
            return view;
        }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[5]);

        Studies studies = new Studies();
        studies.loadData(this, R.raw.studies);
        StudyAdapter adapter = new StudyAdapter(this, studies);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Sample5Activity.this, "ID:" + id, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
