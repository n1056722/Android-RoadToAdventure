package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.MainActivity;
import ya.haojun.roadtoadventure.model.DrawerItem;


public class DrawerRVAdapter extends CommonRVAdapter {

    // type
    private static final int HEAD = 0;
    private static final int BODY = 1;

    // data
    private ArrayList<DrawerItem> list;

    public DrawerRVAdapter(Context context, ArrayList<DrawerItem> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? HEAD : BODY;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            return new HeadViewHolder(getInflater().inflate(R.layout.item_rv_drawer_head, parent, false));
        } else if (viewType == BODY) {
            return new BodyViewHolder(getInflater().inflate(R.layout.item_rv_drawer_body, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            HeadViewHolder h = (HeadViewHolder) holder;
//            h.picture.setImageResource();
//            h.name.setText();
        } else if (holder instanceof BodyViewHolder) {
            BodyViewHolder h = (BodyViewHolder) holder;
            final DrawerItem item = list.get(position - 1);
            h.icon.setImageResource(item.getIcon());
            h.name.setText(item.getName());
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).onDrawerItemClick(item.getName());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 1 + list.size();
    }

    private class HeadViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;

        HeadViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_drawer_head_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_drawer_head_name);
        }
    }

    private class BodyViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        BodyViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.iv_item_rv_drawer_body_icon);
            name = (TextView) v.findViewById(R.id.tv_item_rv_drawer_body_name);
        }
    }
}
