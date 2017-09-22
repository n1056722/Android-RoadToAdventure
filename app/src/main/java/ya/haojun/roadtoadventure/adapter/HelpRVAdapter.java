package ya.haojun.roadtoadventure.adapter;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.model.HelpShop;


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
            final HelpShop item = list.get(position);
            h.name.setText(item.getName());
            h.address.setText(item.getAddress());
            h.distance.setText(item.getDistance() + "公尺");
            // listener
            h.name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), item.getName(), Toast.LENGTH_SHORT).show();
                }
            });
            h.address.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(String.format("geo:0,0?q=%f,%f(%s)", item.getLat(), item.getLng(), item.getName()));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.google.android.apps.maps");
                    getContext().startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView address;
        TextView distance;

        ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.tv_item_rv_help_name);
            address = (TextView) v.findViewById(R.id.tv_item_rv_help_address);
            distance = (TextView) v.findViewById(R.id.tv_item_rv_help_distance);
        }
    }


}
