package cat.flx.legoparts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import cat.flx.legoparts.model.LegoSet;

public class LegoSetDetailsActivity extends AppCompatActivity
{
    private TextView tvSetNum, tvName, tvYear, tvNumParts;
    private ImageView imgSet;
    private RecyclerView rvParts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lego_set_details);
        tvSetNum = findViewById(R.id.tvSetNum);
        imgSet = findViewById(R.id.imgSet);
        tvName = findViewById(R.id.tvName);
        tvYear = findViewById(R.id.tvYear);
        tvNumParts = findViewById(R.id.tvNumParts);
        rvParts = findViewById(R.id.rvParts);

        Intent intent = getIntent();
        String legoSetJson = intent.getStringExtra("legoSetJson");
        Gson gson = new Gson();
        LegoSet legoSet = gson.fromJson(legoSetJson, LegoSet.class);

        tvSetNum.setText(legoSet.getSetNum());
        tvName.setText(legoSet.getName());
        tvYear.setText(legoSet.getYear());
        tvNumParts.setText(legoSet.getNumParts());
        Picasso.with(this).load(legoSet.getSetImgUrl()).into(imgSet);
    }
}
