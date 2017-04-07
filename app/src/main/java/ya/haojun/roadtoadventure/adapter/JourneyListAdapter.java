package ya.haojun.roadtoadventure.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.JourneyModel;

/**
 * Created by asus on 2017/4/6.
 */

public class JourneyListAdapter extends CommonBaseAdapter {
    private ArrayList<JourneyModel> list;

    public JourneyListAdapter(Context context, ArrayList<JourneyModel> list) {
        super(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder tag;
        if (v == null) {
            v = getInflater().inflate(R.layout.item_list_journey, null);
            tag = new ViewHolder(v);
            v.setTag(tag);
        } else {
            tag = (ViewHolder) v.getTag();
        }
        final JourneyModel item = (JourneyModel) getItem(position);
        // set text
        tag.name.setText(item.getJourneyName());
        tag.content.setText(item.getJourneyContent());
        tag.startTime.setText(item.getStartTime());
        tag.stopTime.setText(item.getStopTime());
        return v;
    }

    private class ViewHolder {
        TextView name;
        TextView content;
        TextView startTime;
        TextView stopTime;

        ViewHolder(View v) {
            name = (TextView) v.findViewById(R.id.tv_item_list_journey_name);
            content = (TextView) v.findViewById(R.id.tv_item_list_journey_content);
            startTime = (TextView) v.findViewById(R.id.tv_item_list_journey_start_time);
            stopTime = (TextView) v.findViewById(R.id.tv_item_list_journey_stop_time);
        }
    }
}
