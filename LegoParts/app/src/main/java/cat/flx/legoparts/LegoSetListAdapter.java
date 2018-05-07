package cat.flx.legoparts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cat.flx.legoparts.model.LegoSet;
import cat.flx.legoparts.model.LegoSetList;

public class LegoSetListAdapter extends RecyclerView.Adapter<LegoSetListAdapter.ViewHolder>
{
    private LegoSetList list;

    public LegoSetListAdapter(LegoSetList list)
    {
        super();
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) { super(view); }
    }

    class LegoSetViewHolder extends ViewHolder
    {
        LegoSet legoSet;
        ImageView imgSet;
        TextView tvSetNum, tvName, tvYear, tvNumParts;

        public LegoSetViewHolder(View view) {
            super(view);
            imgSet = view.findViewById(R.id.imgSet);
            tvSetNum = view.findViewById(R.id.tvSetNum);
            tvName = view.findViewById(R.id.tvName);
            tvYear = view.findViewById(R.id.tvYear);
            tvNumParts = view.findViewById(R.id.tvNumParts);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(legoSet == null) return;
                    if(listener != null) listener.itemClicked(v, legoSet);
                }
            });
        }
    }

    interface OnItemClickListener {
        void itemClicked(View view, LegoSet legoSet);
    }

    private OnItemClickListener listener;
    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (list == null) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.empty_list, parent, false);
            return new ViewHolder(itemView);
        }
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lego_set, parent, false);
        return new LegoSetViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (list == null) return;
        LegoSetViewHolder legoHolder = (LegoSetViewHolder) holder;
        LegoSet set = list.getResults()[position];
        Context context = legoHolder.tvSetNum.getContext();
        legoHolder.tvSetNum.setText(set.getSetNum());
        legoHolder.tvName.setText(set.getName());
        legoHolder.tvYear.setText(context.getString(R.string.year, set.getYear()));
        legoHolder.tvNumParts.setText(context.getString(R.string.numParts, set.getNumParts()));
        legoHolder.legoSet = set;
        Picasso.with(context).load(set.getSetImgUrl()).into(legoHolder.imgSet);
    }

    @Override
    public int getItemCount() {
        return (list == null) ? 1 : list.getCount();
    }
}
