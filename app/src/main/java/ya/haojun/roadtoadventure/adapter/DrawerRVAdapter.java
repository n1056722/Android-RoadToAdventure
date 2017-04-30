package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.MainActivity;
import ya.haojun.roadtoadventure.model.DrawerItem;
import ya.haojun.roadtoadventure.model.User;


public class DrawerRVAdapter extends CommonRVAdapter {

    // type
    private static final int HEAD = 0;
    private static final int BODY_GROUP = 1;
    private static final int BODY_CHILD = 2;

    // data
    private ArrayList<DrawerItem> list;

    public DrawerRVAdapter(Context context, ArrayList<DrawerItem> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return HEAD;
        return list.get(position - 1).getIcon() != 0 ? BODY_GROUP : BODY_CHILD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD) {
            return new HeadViewHolder(getInflater().inflate(R.layout.item_rv_drawer_head, parent, false));
        } else if (viewType == BODY_GROUP) {
            return new BodyGroupViewHolder(getInflater().inflate(R.layout.item_rv_drawer_body_group, parent, false));
        } else if (viewType == BODY_CHILD) {
            return new BodyChildViewHolder(getInflater().inflate(R.layout.item_rv_drawer_body_child, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeadViewHolder) {
            HeadViewHolder h = (HeadViewHolder) holder;
            User user = User.getInstance();
            int w = (int) getResources().getDimension(R.dimen.imageview_drawer_width);
            Picasso.with(getContext()).load(user.getUserPicture()).resize(w, w).centerCrop().into(h.picture);
            h.name.setText(user.getUserName());
            h.id.setText(user.getUserID());
        } else if (holder instanceof BodyGroupViewHolder) {
            BodyGroupViewHolder h = (BodyGroupViewHolder) holder;
            final DrawerItem item = list.get(position - 1);
            h.icon.setImageResource(item.getIcon());
            h.name.setText(item.getName());
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getContext()).onDrawerItemClick(item.getName());
                }
            });
        } else if (holder instanceof BodyChildViewHolder) {
            BodyChildViewHolder h = (BodyChildViewHolder) holder;
            final DrawerItem item = list.get(position - 1);
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
        TextView id;

        HeadViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_drawer_head_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_drawer_head_name);
            id = (TextView) v.findViewById(R.id.tv_item_rv_drawer_head_name2);
        }
    }

    private class BodyGroupViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView name;

        BodyGroupViewHolder(View v) {
            super(v);
            icon = (ImageView) v.findViewById(R.id.iv_item_rv_drawer_body_group_icon);
            name = (TextView) v.findViewById(R.id.tv_item_rv_drawer_body_group_name);
        }
    }

    private class BodyChildViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        BodyChildViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_item_rv_drawer_body_child_name);
        }
    }
}
