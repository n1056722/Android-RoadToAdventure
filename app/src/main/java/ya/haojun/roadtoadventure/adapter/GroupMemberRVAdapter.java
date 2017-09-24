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
import ya.haojun.roadtoadventure.activity.AddGroupActivity;
import ya.haojun.roadtoadventure.activity.FriendChatActivity;
import ya.haojun.roadtoadventure.activity.InviteMemberActivity;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.GroupMember;


public class GroupMemberRVAdapter extends CommonRVAdapter {

    // type
    public static final int FIRST = 0;
    public static final int OTHER = 1;
    // data
    private int w;
    private ArrayList<GroupMember> list;

    public GroupMemberRVAdapter(Context context, ArrayList<GroupMember> list) {
        super(context);
        this.w = (int) getResources().getDimension(R.dimen.imageview_list_picture);
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? FIRST : OTHER;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FIRST) {
            return new FirstViewHolder(getInflater().inflate(R.layout.item_rv_add_group_first, parent, false));
        } else if (viewType == OTHER) {
            return new OtherViewHolder(getInflater().inflate(R.layout.item_rv_group_member, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FirstViewHolder) {
            FirstViewHolder h = (FirstViewHolder) holder;
            h.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), InviteMemberActivity.class);
                    ((AddGroupActivity)getContext()).startActivityForResult(intent, AddGroupActivity.REQUEST_INVITE_MEMBER);
                }
            });
        } else if (holder instanceof OtherViewHolder) {
            OtherViewHolder h = (OtherViewHolder) holder;
            GroupMember item = list.get(position - 1);
            Picasso.with(getContext())
                    .load(item.getUserPicture())
                    .resize(w, w)
                    .centerCrop()
                    .into(h.picture);
            h.name.setText(item.getUserName());
        }
    }

    @Override
    public int getItemCount() {
        return 1 + list.size();
    }

    private class FirstViewHolder extends RecyclerView.ViewHolder {

        View add;

        FirstViewHolder(View v) {
            super(v);
            add = v.findViewById(R.id.fab_item_rv_add_group_first);
        }
    }

    private class OtherViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView name;

        OtherViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_group_member_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_group_member_name);
        }
    }

}
