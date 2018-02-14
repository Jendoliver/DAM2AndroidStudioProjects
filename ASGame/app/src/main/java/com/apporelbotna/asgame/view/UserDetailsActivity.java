package com.apporelbotna.asgame.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.model.Player;
import com.apporelbotna.asgame.model.User;
import com.apporelbotna.asgame.model.dao.GameAsyncDAO;
import com.apporelbotna.asgame.model.dao.StucomGamesDAOImpl;
import com.apporelbotna.asgame.view.adapter.UserAdapter;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserDetailsActivity extends AppCompatActivity implements StucomGamesDAOImpl.ResultListener
{
    private RecyclerView rvUserScores;
    private ImageView userDetailsImg;
    private TextView tvUsername, tvEmail;
    private GameAsyncDAO gameDAO;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        rvUserScores = findViewById(R.id.rvUserScores);
        userDetailsImg = findViewById(R.id.userDetailsImg);
        tvUsername = findViewById(R.id.tvUsername);
        tvEmail = findViewById(R.id.tvEmail);

        Intent intent = getIntent();
        String json = intent.getStringExtra("userJson");
        Gson gson = new Gson();
        user = gson.fromJson(json, User.class);
        Picasso.with(this).load(user.getAvatarPath()).into(userDetailsImg);
        tvUsername.setText(user.getUsername());
        tvEmail.setText(user.getEmail());

        fillRecyclerView();
    }

    private void fillRecyclerView()
    {
        rvUserScores.setAdapter(null);
        gameDAO = new StucomGamesDAOImpl(this);
        ((StucomGamesDAOImpl)gameDAO).setResultListener(this);
        gameDAO.findScores(user);
    }

    @Override
    public <T> void onResultReady(T result)
    {
        UserAdapter adapter = new UserAdapter((List<Player>) result);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvUserScores.setLayoutManager(layoutManager);
        rvUserScores.setItemAnimator(new DefaultItemAnimator());
        rvUserScores.setAdapter(adapter);
    }
}
