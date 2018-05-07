package com.apporelbotna.lego;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apporelbotna.lego.model.LegoSet;
import com.apporelbotna.lego.model.LegoSetList;

/**
 * Created by Jandol on 11/23/2017.
 */

public class LegoSetListAdapter extends RecyclerView.Adapter<LegoSetListAdapter.ViewHolder>
{
    LegoSetList legoSetList;

    class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgUrl;
        TextView tvId, tvName, tvYear, tvNumParts;

        public ViewHolder(View view) {
            super(view);
            imgUrl = view.findViewById(R.id.imgUrl);
            tvId = view.findViewById(R.id.tvId);
            tvName = view.findViewById(R.id.tvName);
            tvYear = view.findViewById(R.id.tvYear);
            tvNumParts = view.findViewById(R.id.tvNumParts);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.)
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LegoSet set = legoSetList.getResults()[position];
        Context context = holder.tvId.getContext();
        holder.tvId.setText(set.id);
        holder.tvName.setText(set.name);
        holder.tvYear.setText(context.getString(R.string.legoSetYear, set.year));
        holder.tvNumParts.setText(context.getString(R.string.legoSetNumParts, set.numParts));
        // holder.imgSet ...
    }

    @Override
    public int getItemCount() {
        return legoSetList.getCount();
    }

}
