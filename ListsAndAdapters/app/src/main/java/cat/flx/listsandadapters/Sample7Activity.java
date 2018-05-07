package cat.flx.listsandadapters;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Sample7Activity extends AppCompatActivity {

    public class StudyAdapter extends RecyclerView.Adapter<StudyAdapter.ViewHolder> {

        private Studies studies;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle;
            TextView txtFullTitle;
            TextView txtTeacher;
            TextView txtHours;
            ImageView imgView;

            ViewHolder(View view) {
                super(view);
                txtTitle = view.findViewById(R.id.txtShortname);
                txtFullTitle = view.findViewById(R.id.txtFullname);
                txtTeacher = view.findViewById(R.id.txtTeacher);
                txtHours = view.findViewById(R.id.txtHours);
                imgView = view.findViewById(R.id.imgView);
            }
        }

        StudyAdapter(Studies studies) { this.studies = studies; }

        @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listview_item, parent, false);
            return new ViewHolder(itemView);
        }

        @Override public void onBindViewHolder(ViewHolder holder, int position) {
            Study study = studies.get(position);
            holder.txtTitle.setText(study.title);
            holder.txtFullTitle.setText(study.fullTitle);
            holder.txtHours.setText(getString(R.string.hours, study.hours));
            holder.txtTeacher.setText(study.teacher);
            holder.imgView.setImageDrawable(study.photo);
        }

        @Override public int getItemCount() { return studies.size(); }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample7);
        TextView textView = findViewById(R.id.textView);
        textView.setText(getResources().getStringArray(R.array.samples)[7]);

        Studies studies = new Studies();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        StudyAdapter adapter = new StudyAdapter(studies);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        studies.loadData(this, R.raw.studies);
        adapter.notifyDataSetChanged();
    }
}
