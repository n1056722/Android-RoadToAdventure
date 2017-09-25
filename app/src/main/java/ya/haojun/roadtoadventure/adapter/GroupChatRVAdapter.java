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
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.FriendChat;
import ya.haojun.roadtoadventure.model.GroupChat;
import ya.haojun.roadtoadventure.model.User;


public class GroupChatRVAdapter extends CommonRVAdapter {

    // type
    private static final int SELF = 0;
    private static final int OTHER = 1;

    // data
    private int w;
    private String selfUserID;
    private ArrayList<GroupChat> list;

    public GroupChatRVAdapter(Context context, ArrayList<GroupChat> list) {
        super(context);
        this.w = (int) (getResources().getDisplayMetrics().density * 40);
        this.selfUserID = User.getInstance().getUserId();
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).getUserId().equals(selfUserID) ? SELF : OTHER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == SELF) {
            return new SelfViewHolder(getInflater().inflate(R.layout.item_rv_chat_self, parent, false));
        } else if (viewType == OTHER) {
            return new OtherGroupViewHolder(getInflater().inflate(R.layout.item_rv_chat_other, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GroupChat item = list.get(position);
        if (holder instanceof SelfViewHolder) {
            SelfViewHolder h = (SelfViewHolder) holder;
            h.content.setText(item.getContent());
            h.createDate.setText(TimeHelper.toChatFormat(item.getCreateDate()));
        } else if (holder instanceof OtherGroupViewHolder) {
            OtherGroupViewHolder h = (OtherGroupViewHolder) holder;
            Picasso.with(getContext())
                    .load(item.getUserPicture())
                    .resize(w, w)
                    .centerCrop()
                    .into(h.picture);
            h.name.setText(item.getUserName());
            h.content.setText(item.getContent());
            h.createDate.setText(TimeHelper.toChatFormat(item.getCreateDate()));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class SelfViewHolder extends RecyclerView.ViewHolder {

        TextView content;
        TextView createDate;

        SelfViewHolder(View v) {
            super(v);
            content = (TextView) v.findViewById(R.id.tv_item_rv_chat_self_content);
            createDate = (TextView) v.findViewById(R.id.tv_item_rv_chat_self_create_date);
        }
    }

    private class OtherGroupViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;
        TextView content;
        TextView createDate;

        OtherGroupViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_chat_other_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_chat_other_name);
            content = (TextView) v.findViewById(R.id.tv_item_rv_chat_other_content);
            createDate = (TextView) v.findViewById(R.id.tv_item_rv_chat_other_create_date);
        }
    }
}
