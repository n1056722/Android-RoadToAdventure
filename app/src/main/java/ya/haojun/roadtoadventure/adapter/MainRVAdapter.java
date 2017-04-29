package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.MainItem;


public class MainRVAdapter extends CommonRVAdapter {

    // data
    private ArrayList<MainItem> list;

    public MainRVAdapter(Context context, ArrayList<MainItem> list) {
        super(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(getInflater().inflate(R.layout.item_rv_main, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            final MainItem item = list.get(position);
            // set text
            h.icon.setImageResource(item.getIcon());
            h.name.setText(item.getName());
            h.background.setCardBackgroundColor(item.getColor());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView name;
        CardView background;

        ViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.iv_item_grid_main_icon);
            name = (TextView) v.findViewById(R.id.tv_item_grid_main_name);
            background = (CardView) v.findViewById(R.id.cv_item_grid_main_background);
        }
    }
}
