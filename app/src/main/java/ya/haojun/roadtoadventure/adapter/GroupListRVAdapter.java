package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.FriendChatActivity;
import ya.haojun.roadtoadventure.activity.GroupInfoActivity;
import ya.haojun.roadtoadventure.activity.GroupListActivity;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.Group;


public class GroupListRVAdapter extends CommonRVAdapter {


    // data
    private int pictureWidth;
    private ArrayList<Group> list;

    public GroupListRVAdapter(Context context, ArrayList<Group> list) {
        super(context);
        this.pictureWidth = (int) getResources().getDimension(R.dimen.imageview_list_picture);
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_group_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            final Group item = list.get(position);
            Picasso.with(getContext())
                    .load(item.getPicturePath())
                    .resize(pictureWidth, pictureWidth)
                    .centerCrop()
                    .into(h.picture);
            h.name.setText(item.getName());
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), GroupInfoActivity.class);
                    Bundle b = new Bundle();
                    b.putInt("groupID",item.getGroupId());
                    intent.putExtras(b);
                    getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;

        ViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_group_list_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_group_list_name);
        }
    }

}
