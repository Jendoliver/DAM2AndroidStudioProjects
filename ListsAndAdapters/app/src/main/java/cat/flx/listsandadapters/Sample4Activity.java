package cat.flx.listsandadapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sample4Activity extends AppCompatActivity {

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[4]);

        Studies studies = new Studies();
        studies.loadData(this, R.raw.studies);

        ArrayAdapter<Study> adapter = new ArrayAdapter<Study>(this, 0, studies) {
            @Override
            public long getItemId(int position) {
                Study study = this.getItem(position);
                return study.id;
            }
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater inflater = LayoutInflater.from(Sample4Activity.this);
                View view = inflater.inflate(R.layout.listview_item, null);
                TextView txtTitle = view.findViewById(R.id.txtShortname);
                TextView txtFullTitle = view.findViewById(R.id.txtFullname);
                TextView txtTeacher = view.findViewById(R.id.txtTeacher);
                TextView txtHours = view.findViewById(R.id.txtHours);
                ImageView imgView = view.findViewById(R.id.imgView);
                Study study = this.getItem(position);
                txtTitle.setText(study.title);
                txtFullTitle.setText(study.fullTitle);
                txtTeacher.setText(study.teacher);
                txtHours.setText(getString(R.string.hours, study.hours));
                imgView.setImageDrawable(study.photo);
                return view;
            }
        };
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
