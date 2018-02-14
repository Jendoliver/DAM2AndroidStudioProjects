package com.apporelbotna.asgame.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.model.Player;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jandol on 02/14/2018.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder>
{
    private List<Player> players;

    public UserAdapter(List<Player> players)
    {
        super();
        this.players = players;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View view) { super(view); }
    }

    class UserViewHolder extends ViewHolder
    {
        Player player;
        ImageView gameImg;
        TextView gameName, playerScore;

        public UserViewHolder(View view)
        {
            super(view);
            gameImg = view.findViewById(R.id.img);
            gameName = view.findViewById(R.id.tvName);
            playerScore = view.findViewById(R.id.tvScore);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (players == null) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ranking, parent, false);
            return new ViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ranking, parent, false);
            return new UserViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (players == null) return;
        UserViewHolder vh = (UserViewHolder) holder;
        Player player = players.get(position);
        Context context = vh.gameName.getContext();
        vh.player = player;
        vh.gameName.setText(player.getGame().getName());
        vh.playerScore.setText(String.valueOf(player.getScore()));
        Picasso.with(context).load(player.getGame().getImagePath()).into(vh.gameImg);
    }

    @Override
    public int getItemCount() {
        return (players == null) ? 1 : players.size();
    }
}
