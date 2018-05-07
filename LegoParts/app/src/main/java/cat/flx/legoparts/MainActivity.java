package cat.flx.legoparts;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import cat.flx.legoparts.model.LegoSet;
import cat.flx.legoparts.model.LegoSetList;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, LegoSetSearchTask.WeakReference, LegoSetListAdapter.OnItemClickListener, View.OnKeyListener {

    EditText edSearch;
    ProgressBar pbSearch;
    RecyclerView rvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edSearch = findViewById(R.id.edSearch);
        pbSearch = findViewById(R.id.pbSearch);
        rvSearch = findViewById(R.id.rvSearch);
        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
        btnSearch.setOnKeyListener(this);
    }

    @Override
    public void onClick(View view) {
        search();
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
            Log.d("flx", "EVENT TRIGGERED");
            search();
            return true;
        }
        return false;
    }

    private void search() {
        String search = edSearch.getText().toString();
        Log.d("flx", "SEARCHING " + search);
        rvSearch.setAdapter(null);
        LegoSetSearchTask task = new LegoSetSearchTask(this);
        task.execute(search);
    }

    @Override public Context getContext() { return this; }
    @Override public ProgressBar getProgressBar() { return this.pbSearch; }

    @Override
    public void finished(LegoSetList list)
    {
        LegoSetListAdapter adapter = new LegoSetListAdapter(list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvSearch.setLayoutManager(layoutManager);
        rvSearch.setItemAnimator(new DefaultItemAnimator());
        rvSearch.setAdapter(adapter);
        adapter.setOnClickListener(this);
    }

    @Override
    public void itemClicked(View view, LegoSet legoSet) {
        String setNum = legoSet.getSetNum();
        Intent intent = new Intent(this, LegoSetDetailsActivity.class);
        Gson gson = new Gson();
        String legoSetJson = gson.toJson(legoSet);
        intent.putExtra("legoSetJson", legoSetJson);
        startActivity(intent);
    }
}
