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
import java.util.HashSet;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.activity.FriendChatActivity;
import ya.haojun.roadtoadventure.model.Friend;


public class InviteMemberRVAdapter extends CommonRVAdapter {


    // data
    private int w;
    private ArrayList<Friend> list;
    private ArrayList<Integer> list_selected;

    public InviteMemberRVAdapter(Context context, ArrayList<Friend> list) {
        super(context);
        this.w = (int) getResources().getDimension(R.dimen.imageview_list_picture);
        this.list = list;
        this.list_selected = new ArrayList<>();
    }

    public ArrayList<Integer> getSelected(){
        return list_selected;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_invite_member, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            final ViewHolder h = (ViewHolder) holder;
            Friend item = list.get(position);
            Picasso.with(getContext())
                    .load(item.getUserPicture())
                    .resize(w, w)
                    .centerCrop()
                    .into(h.picture);
            h.name.setText(item.getUserName());
            h.check.setImageResource(list_selected.contains(h.getAdapterPosition()) ? R.drawable.ic_check_on : R.drawable.ic_check_off);
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (list_selected.contains(h.getAdapterPosition())) {
                        list_selected.remove(Integer.valueOf(h.getAdapterPosition()));
                    } else {
                        list_selected.add(h.getAdapterPosition());
                    }
                    notifyItemChanged(h.getAdapterPosition());
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
        ImageView check;

        ViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_invite_member_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_invite_member_name);
            check = (ImageView) v.findViewById(R.id.iv_item_rv_invite_member_check);
        }
    }

}
