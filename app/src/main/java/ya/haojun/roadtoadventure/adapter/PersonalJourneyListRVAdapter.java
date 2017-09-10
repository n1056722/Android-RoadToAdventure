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
import ya.haojun.roadtoadventure.activity.PersonalJourneyListActivity;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.PersonalJourney;


public class PersonalJourneyListRVAdapter extends CommonRVAdapter {

    // data
    private ArrayList<PersonalJourney> list;
    private int w;

    public PersonalJourneyListRVAdapter(Context context, ArrayList<PersonalJourney> list) {
        super(context);
        this.list = list;
        this.w = (int) (getResources().getDisplayMetrics().density * 100);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_personal_journey_list, parent, false));
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
            h.createDate.setText(TimeHelper.convertToNoYearSecond(item.getCreateDate()));
            h.name.setText(item.getName());
            h.content.setText(item.getContent());
            h.status.setVisibility(item.getStatus().equals("1") ? View.VISIBLE : View.GONE);
            h.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((PersonalJourneyListActivity) getContext()).onItemClick(item);
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
        TextView status;
        TextView createDate;
        TextView name;
        TextView content;

        ViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_personal_journey_list_picture);
            status = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_status);
            createDate = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_create_date);
            name = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_name);
            content = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_content);
        }
    }

}
