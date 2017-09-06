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
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.PersonalJourney;


public class PersonalJourneyListRVAdapter extends CommonRVAdapter {

    // data
    private ArrayList<PersonalJourney> list;
    private int pictureWidth;

    public PersonalJourneyListRVAdapter(Context context, ArrayList<PersonalJourney> list) {
        super(context);
        this.list = list;
        this.pictureWidth = (int) getResources().getDimension(R.dimen.imageview_list_picture_big);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_personal_journey_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            PersonalJourney item = list.get(position);
            Picasso.with(getContext()).load(item.getPicture()).resize(pictureWidth, pictureWidth).centerCrop().into(h.picture);
//            h.starttime.setText(TimeHelper.convertToNoYearSecond(item.getStartTime()));
            h.name.setText(item.getName());
            h.content.setText(item.getContent());
            h.status.setVisibility(item.getStatus().equals("1") ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;
        TextView status;
        TextView starttime;
        TextView name;
        TextView content;

        ViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_personal_journey_list_picture);
            status = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_status);
            starttime = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_create_date);
            name = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_name);
            content = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_list_content);
        }
    }

}
