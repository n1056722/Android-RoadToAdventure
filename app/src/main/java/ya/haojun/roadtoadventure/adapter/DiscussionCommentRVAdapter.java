package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.FriendChatActivity;
import ya.haojun.roadtoadventure.activity.FriendListActivity;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.PersonalJourneyComment;


public class DiscussionCommentRVAdapter extends CommonRVAdapter {


    // data
    private int w;
    private ArrayList<PersonalJourneyComment> list;

    public DiscussionCommentRVAdapter(Context context, ArrayList<PersonalJourneyComment> list) {
        super(context);
        this.w = (int) getResources().getDimension(R.dimen.imageview_list_picture);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_discussion_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            final PersonalJourneyComment item = list.get(position);
            Picasso.with(getContext())
                    .load(item.getUserPicture())
                    .resize(w, w)
                    .centerCrop()
                    .into(h.userPicture);
            h.userName.setText(item.getUserName());
            h.comment.setText(item.getComment());
            h.createDate.setText(item.getCreateDate());
        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userPicture;
        TextView userName;
        TextView comment;
        TextView createDate;

        ViewHolder(View v) {
            super(v);
            userPicture = (ImageView) v.findViewById(R.id.iv_item_rv_discussion_comment_user_picture);
            userName = (TextView) v.findViewById(R.id.tv_item_rv_discussion_comment_user_name);
            comment = (TextView) v.findViewById(R.id.tv_item_rv_discussion_comment_comment);
            createDate = (TextView) v.findViewById(R.id.tv_item_rv_discussion_comment_create_date);
        }
    }

}
