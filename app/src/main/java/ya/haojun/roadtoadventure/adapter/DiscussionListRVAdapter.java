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
import ya.haojun.roadtoadventure.activity.DiscussionListActivity;
import ya.haojun.roadtoadventure.activity.PersonalJourneyListActivity;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.PersonalJourney;


public class DiscussionListRVAdapter extends CommonRVAdapter {

    // data
    private ArrayList<PersonalJourney> list;
    private int w;

    public DiscussionListRVAdapter(Context context, ArrayList<PersonalJourney> list) {
        super(context);
        this.list = list;
        this.w = (int) (getResources().getDisplayMetrics().density * 100);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_discussion_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            final PersonalJourney item = list.get(position);
            String picturePath = !item.getPictures().isEmpty() ? item.getPictures().get(0) : "";
            if (!picturePath.isEmpty()) {
                Picasso.with(getContext())
                        .load(picturePath)
                        .resize(w, w)
                        .centerCrop()
                        .placeholder(R.drawable.icon)
                        .error(R.drawable.icon)
                        .into(h.picture);
            } else {
                Picasso.with(getContext())
                        .load(R.drawable.icon)
                        .resize(w, w)
                        .centerCrop()
                        .into(h.picture);
            }
            h.name.setText(item.getName());
            h.userName.setText(item.getUserName());
            h.modifyDate.setText(TimeHelper.toDate(item.getModifyDate()));
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((DiscussionListActivity)getContext()).onItemClick(item);
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
        TextView userName;
        TextView modifyDate;

        ViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_discussion_list_picture);
            name = (TextView) v.findViewById(R.id.tv_item_rv_discussion_list_name);
            userName = (TextView) v.findViewById(R.id.tv_item_rv_discussion_list_user_name);
            modifyDate = (TextView) v.findViewById(R.id.tv_item_rv_discussion_list_modify_date);
        }
    }

}
