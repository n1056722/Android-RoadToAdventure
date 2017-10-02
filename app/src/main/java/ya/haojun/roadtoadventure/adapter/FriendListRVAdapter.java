package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.FriendChatActivity;
import ya.haojun.roadtoadventure.activity.FriendListActivity;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.Friend;


public class FriendListRVAdapter extends CommonRVAdapter {


    // data
    private int w;
    private ArrayList<Friend> list;

    public FriendListRVAdapter(Context context, ArrayList<Friend> list) {
        super(context);
        this.w = (int) getResources().getDimension(R.dimen.imageview_list_picture);
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            final Friend item = list.get(position);
            Picasso.with(getContext())
                    .load(item.getUserPicture())
                    .resize(w, w)
                    .centerCrop()
                    .into(h.picture);
            h.name.setText(item.getUserName());
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("friend", item);
                    Intent intent = new Intent(getContext(), FriendChatActivity.class);
                    intent.putExtras(bundle);
                    getContext().startActivity(intent);
                }
            });
            h.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ((FriendListActivity) getContext()).deleteFriendDialog(item.getUserId());
                    return false;
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
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_friend_list_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_friend_list_name);
        }
    }

}
