package cat.flx.legoparts;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import cat.flx.legoparts.model.LegoSetPartItem;
import cat.flx.legoparts.model.LegoSetPartList;

public class LegoSetPartListAdapter
        extends RecyclerView.Adapter<LegoSetPartListAdapter.ViewHolder> {

    private LegoSetPartList list;
    public LegoSetPartListAdapter(LegoSetPartList list) {
        super();
        this.list = list;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPart;
        TextView tvQuantity;

        public ViewHolder(View view) {
            super(view);
            imgPart = view.findViewById(R.id.imgPart);
            tvQuantity = view.findViewById(R.id.tvQuantity);
        }
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lego_part, parent, false);
        return new ViewHolder(itemView);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        if (list == null) return;
        LegoSetPartItem item = list.getResults()[position];
        Context context = holder.tvQuantity.getContext();
        holder.tvQuantity.setText(item.getQuantity() + "x");
        Picasso.with(context).load(item.getPart().getImgUrl()).into(holder.imgPart);
    }

    @Override public int getItemCount() {
        return list.getCount();
    }
}
