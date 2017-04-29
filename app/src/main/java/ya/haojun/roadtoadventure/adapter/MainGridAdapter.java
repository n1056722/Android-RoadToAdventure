package ya.haojun.roadtoadventure.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.MainItem;


public class MainGridAdapter extends CommonBaseAdapter {
    private ArrayList<MainItem> list;

    public MainGridAdapter(Context context, ArrayList<MainItem> list) {
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
            v = getInflater().inflate(R.layout.item_rv_main, null);
            tag = new ViewHolder(v);
            v.setTag(tag);
        } else {
            tag = (ViewHolder) v.getTag();
        }
        final MainItem item = (MainItem) getItem(position);
        // set text
        tag.icon.setImageResource(item.getIcon());
        tag.name.setText(item.getName());
        tag.background.setCardBackgroundColor(item.getColor());
        return v;
    }

    private class ViewHolder {
        ImageView icon;
        TextView name;
        CardView background;

        ViewHolder(View v) {
            icon = (ImageView) v.findViewById(R.id.iv_item_grid_main_icon);
            name = (TextView) v.findViewById(R.id.tv_item_grid_main_name);
            background = (CardView) v.findViewById(R.id.cv_item_grid_main_background);
        }
    }
}
