package cat.flx.legoparts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import cat.flx.legoparts.model.LegoSet;
import cat.flx.legoparts.model.LegoSetList;
import cat.flx.legoparts.model.LegoSetPartList;

public class SetDetailsActivity extends AppCompatActivity
implements LegoSetPartListTask.WeakReference {

    ImageView imgSet;
    TextView tvSetNum, tvName, tvYear, tvNumParts;
    RecyclerView rvParts;
    LinearLayout progressBarContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_details);
        tvSetNum = findViewById(R.id.tvSetNum);
        imgSet = findViewById(R.id.imgSet);
        tvName = findViewById(R.id.tvName);
        tvYear = findViewById(R.id.tvYear);
        tvNumParts = findViewById(R.id.tvNumParts);
        rvParts = findViewById(R.id.rvParts);
        progressBarContainer = findViewById(R.id.progressBarContainer);
        Intent intent = getIntent();
        String json = intent.getStringExtra("set");
        Gson gson = new Gson();
        LegoSet set = gson.fromJson(json, LegoSet.class);
        tvSetNum.setText(set.getSetNum());
        tvName.setText(set.getName());
        tvYear.setText("" + set.getYear());
        tvNumParts.setText(getString(R.string.numParts, set.getNumParts()));
        Picasso.with(this).load(set.getSetImgUrl()).into(imgSet);

        // DOWNLOAD PART INFO
        LegoSetPartListTask task = new LegoSetPartListTask(this);
        progressBarContainer.setVisibility(View.VISIBLE);
        task.execute(set.getSetNum());
    }

    @Override public Context getContext() { return this; }
    @Override public void finished(LegoSetPartList list) {
        progressBarContainer.setVisibility(View.GONE);
        LegoSetPartListAdapter adapter =
                new LegoSetPartListAdapter(list);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        rvParts.setLayoutManager(layoutManager);
        rvParts.setItemAnimator(new DefaultItemAnimator());
        rvParts.setAdapter(adapter);
    }
}
