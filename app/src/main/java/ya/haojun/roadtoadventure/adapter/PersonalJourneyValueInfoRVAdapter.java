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
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.model.Friend;
import ya.haojun.roadtoadventure.model.ValueInfo;


public class PersonalJourneyValueInfoRVAdapter extends CommonRVAdapter {

    // data
    private ArrayList<ValueInfo> list;

    public PersonalJourneyValueInfoRVAdapter(Context context, ArrayList<ValueInfo> list) {
        super(context);
        this.list = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_personal_journey_value_info, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            ValueInfo item = list.get(position);
            h.title.setText(item.getTitle());
            h.value.setText(item.getValue());
            LogHelper.d("p:" + position);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView value;

        ViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_value_info_title);
            value = (TextView) v.findViewById(R.id.tv_item_rv_personal_journey_value_info_value);
        }
    }

}
