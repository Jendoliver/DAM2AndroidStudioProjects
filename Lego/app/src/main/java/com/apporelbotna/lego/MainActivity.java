package com.apporelbotna.lego;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.apporelbotna.lego.model.LegoSetList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, LegoSetSearchTask.WeakReference
{
    EditText etSearch;
    ProgressBar pbSearch;
    RecyclerView rvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etSearch = findViewById(R.id.etSearch);
        pbSearch = findViewById(R.id.pbSearch);
        rvSearch = findViewById(R.id.rvSearch);

        ImageButton btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String search = etSearch.getText().toString();
        Log.d("Jendoliver", "SEARCHING " + search);
        LegoSetSearchTask task = new LegoSetSearchTask(this);
        task.execute(search);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public ProgressBar getProgressBar() {
        return this.pbSearch;
    }

    @Override
    public void finished(LegoSetList legoSetList) {
        // GETTING LEGO SET LIST AND MOUNT ADAPTER
        LegoSetListAdapter adapter = new LegoSetListAdapter(legoSetList);
    }
}
