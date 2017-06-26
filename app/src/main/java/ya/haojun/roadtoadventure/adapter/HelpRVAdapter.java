package ya.haojun.roadtoadventure.adapter;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
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

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        TextView address;
        TextView phone;

        ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_item_rv_help_name);
            address = (TextView) v.findViewById(R.id.tv_item_rv_help_address);
            phone = (TextView) v.findViewById(R.id.tv_item_rv_help_phone);

            address.setOnClickListener(this);
            phone.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv_item_rv_help_address:
                    Uri uri2=Uri.parse("geo:38.899533,-77.036476");
                    Intent intent2=new Intent(Intent.ACTION_VIEW,uri2);
                    getContext().startActivity(intent2);
                    break;
                case R.id.tv_item_rv_help_phone:
                    Uri uri=Uri.parse("tel:0999123456");
                    Intent intent=new Intent(Intent.ACTION_DIAL,uri);
                    getContext().startActivity(intent);
                    break;

            }
        }
    }


}
