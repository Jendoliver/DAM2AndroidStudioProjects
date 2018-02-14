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
 * Created by Jandol on 02/13/2018.
 */

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.ViewHolder>
{
    private List<Player> players;
    private OnItemClickListener listener;

    public PlayersAdapter(List<Player> players)
    {
        super();
        this.players = players;
    }

    public interface OnItemClickListener
    {
        void itemClicked(View view, Player player);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View view) { super(view); }
    }

    class PlayersViewHolder extends ViewHolder
    {
        Player player;
        ImageView userImg;
        TextView playerName, playerScore;

        public PlayersViewHolder(View view)
        {
            super(view);
            userImg = view.findViewById(R.id.img);
            playerName = view.findViewById(R.id.tvName);
            playerScore = view.findViewById(R.id.tvScore);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player == null) return;
                    if (listener != null) listener.itemClicked(view, player);
                }
            });
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
            return new PlayersViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (players == null) return;
        PlayersViewHolder vh = (PlayersViewHolder) holder;
        Player player = players.get(position);
        Context context = vh.playerName.getContext();
        vh.player = player;
        vh.playerName.setText(player.getName());
        vh.playerScore.setText(String.valueOf(player.getScore()));
        Picasso.with(context).load(player.getUser().getAvatarPath()).into(vh.userImg);
    }

    @Override
    public int getItemCount() {
        return (players == null) ? 1 : players.size();
    }
}
