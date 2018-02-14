package com.apporelbotna.asgame.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporelbotna.asgame.R;
import com.apporelbotna.asgame.model.Game;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jandol on 02/13/2018.
 */

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.ViewHolder>
{
    private List<Game> games;
    private OnItemClickListener listener;

    public GamesAdapter(List<Game> games)
    {
        super();
        this.games = games;
    }

    public interface OnItemClickListener
    {
        void itemClicked(View view, Game game);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        public ViewHolder(View view) { super(view); }
    }

    class GamesViewHolder extends ViewHolder
    {
        Game game;
        ImageView gameImg;
        TextView gameName, gameDescription;

        public GamesViewHolder(View view)
        {
            super(view);
            gameImg = view.findViewById(R.id.gameImg);
            gameName = view.findViewById(R.id.gameName);
            gameDescription = view.findViewById(R.id.gameDescription);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (game == null) return;
                    if (listener != null) listener.itemClicked(view, game);
                }
            });
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (games == null) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_game, parent, false);
            return new ViewHolder(itemView);
        }
        else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_game, parent, false);
            return new GamesViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (games == null) return;
        GamesViewHolder vh = (GamesViewHolder) holder;
        Game game = games.get(position);
        Context context = vh.gameName.getContext();
        vh.game = game;
        vh.gameName.setText(game.getName());
        vh.gameDescription.setText(game.getDescription());
        Picasso.with(context).load(game.getImagePath()).into(vh.gameImg);
    }

    @Override
    public int getItemCount() {
        return (games == null) ? 1 : games.size();
    }
}
