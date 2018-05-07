package cat.flx.listsandadapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Sample6Activity extends AppCompatActivity {

    class StudyAdapter extends BaseAdapter {

        Studies studies;

        class ViewHolder {
            TextView txtTitle;
            TextView txtFullTitle;
            TextView txtTeacher;
            TextView txtHours;
            ImageView imgView;

            ViewHolder(View view) {
                txtTitle = view.findViewById(R.id.txtShortname);
                txtFullTitle = view.findViewById(R.id.txtFullname);
                txtTeacher = view.findViewById(R.id.txtTeacher);
                txtHours = view.findViewById(R.id.txtHours);
                imgView = view.findViewById(R.id.imgView);
            }
        }

        StudyAdapter(Studies studies) {
            super();
            this.studies = studies;
        }
        @Override public int getCount() { return studies.size(); }
        @Override public Object getItem(int position) { return studies.get(position); }
        @Override public long getItemId(int position) {
            Study study = studies.get(position);
            return study.id;
        }
        @Override public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                view = inflater.inflate(R.layout.listview_item, null);
                ViewHolder holder = new ViewHolder(view);
                view.setTag(holder);
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            Study study = studies.get(position);
            holder.txtTitle.setText(study.title);
            holder.txtFullTitle.setText(study.fullTitle);
            holder.txtHours.setText(getString(R.string.hours, study.hours));
            holder.txtTeacher.setText(study.teacher);
            holder.imgView.setImageDrawable(study.photo);
            return view;
        }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[6]);

        Studies studies = new Studies();
        studies.loadData(this, R.raw.studies);
        StudyAdapter adapter = new StudyAdapter(studies);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
    }
}
