package com.apporelbotna.asgame.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.model.Game;
import com.apporelbotna.asgame.model.Player;
import com.apporelbotna.asgame.model.dao.GameAsyncDAO;
import com.apporelbotna.asgame.model.dao.StucomGamesDAOImpl;
import com.apporelbotna.asgame.view.adapter.GamesAdapter;
import com.apporelbotna.asgame.view.adapter.PlayersAdapter;
import com.google.gson.Gson;

import java.util.List;

public class RankingActivity extends AppCompatActivity
        implements GamesAdapter.OnItemClickListener, PlayersAdapter.OnItemClickListener, StucomGamesDAOImpl.ResultListener
{
    private RecyclerView rvRanking;
    private GameAsyncDAO gameDAO;
    private boolean isFirstRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        rvRanking = findViewById(R.id.rvRanking);
        fillRecyclerViewWithGamesList();
        isFirstRecycler = true;
    }

    @Override
    public void onBackPressed()
    {
        if(isFirstRecycler)
            finish();
        else
        {
            isFirstRecycler = true;
            fillRecyclerViewWithGamesList();
        }
    }

    private void fillRecyclerViewWithGamesList()
    {
        rvRanking.setAdapter(null);
        gameDAO = new StucomGamesDAOImpl(this);
        ((StucomGamesDAOImpl)gameDAO).setResultListener(this);
        gameDAO.findGames();
    }

    private void fillRecyclerViewWithPlayersList(Game game)
    {
        rvRanking.setAdapter(null);
        gameDAO = new StucomGamesDAOImpl(this);
        ((StucomGamesDAOImpl)gameDAO).setResultListener(this);
        gameDAO.findPlayers(game);
    }

    @Override
    public void itemClicked(View view, Game game)
    {
        isFirstRecycler = false;
        fillRecyclerViewWithPlayersList(game);
    }

    @Override
    public void itemClicked(View view, Player player)
    {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(player.getUser());
        intent.putExtra("userJson", json);
        startActivity(intent);
    }

    @Override
    public <T> void onResultReady(T result)
    {
        RecyclerView.Adapter adapter = null;
        List<?> unknownTypeResult = (List<?>) result;
        if(unknownTypeResult.isEmpty()) return;
        if( unknownTypeResult.get(0) instanceof Game) {
            List<Game> games = (List<Game>) unknownTypeResult;
            adapter = new GamesAdapter(games);
            ((GamesAdapter)adapter).setOnItemClickListener(this);
        } else {
            List<Player> players = (List<Player>) unknownTypeResult;
            adapter = new PlayersAdapter(players);
            ((PlayersAdapter)adapter).setOnItemClickListener(this);
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rvRanking.setLayoutManager(layoutManager);
        rvRanking.setItemAnimator(new DefaultItemAnimator());
        rvRanking.setAdapter(adapter);
    }
}
