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
import ya.haojun.roadtoadventure.activity.MainActivity;
import ya.haojun.roadtoadventure.model.DrawerItem;
import ya.haojun.roadtoadventure.model.HelpShop;
import ya.haojun.roadtoadventure.model.User;


public class HelpRVAdapter extends CommonRVAdapter {

    // data
    private ArrayList<HelpShop> list;

    public HelpRVAdapter(Context context, ArrayList<HelpShop> list) {
        super(context);
        this.list = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_help, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            HelpShop item = list.get(position);
            h.name.setText(item.getName());
            h.address.setText(item.getAddress());
            h.phone.setText(item.getPhone());
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        TextView phone;

        ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_item_rv_help_name);
            address = (TextView) v.findViewById(R.id.tv_item_rv_help_address);
            phone = (TextView) v.findViewById(R.id.tv_item_rv_help_phone);
        }
    }
}
