package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.JourneyOpenItem;

public class JourneyOpenListAdapter extends CommonBaseAdapter {

    private ArrayList<JourneyOpenItem> list;

    public JourneyOpenListAdapter(Context context) {
        super(context);
        this.list = new ArrayList<>();
        list.add(new JourneyOpenItem(R.drawable.ic_public_b, "公開"));
        list.add(new JourneyOpenItem(R.drawable.ic_lock_b, "不公開"));
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
            v = getInflater().inflate(R.layout.item_list_journey_open, null);
            tag = new ViewHolder(v);
            v.setTag(tag);
        } else {
            tag = (ViewHolder) v.getTag();
        }
        final JourneyOpenItem item = (JourneyOpenItem) getItem(position);
        tag.icon.setImageResource(item.getIcon());
        tag.name.setText(item.getName());
        return v;
    }

    private class ViewHolder {
        ImageView icon;
        TextView name;

        ViewHolder(View v) {
            icon = (ImageView) v.findViewById(R.id.iv_item_list_journey_open_icon);
            name = (TextView) v.findViewById(R.id.tv_item_list_journey_open_name);
        }
    }
}
