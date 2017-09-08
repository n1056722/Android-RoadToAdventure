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
import ya.haojun.roadtoadventure.activity.SearchFriendActivity;
import ya.haojun.roadtoadventure.model.Friend;


/**
 * Created by bvxcx on 2017/9/7.
 */

public class SearchFriendRVAdapter extends CommonRVAdapter {
    private ArrayList<Friend> list_search;
    private int pictureWidth;



    public SearchFriendRVAdapter(Context context, ArrayList<Friend> list_search) {
        super(context);
        this.list_search = list_search;
        this.pictureWidth = (int) getResources().getDimension(R.dimen.imageview_list_picture_big);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.item_rv_friend_search, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder h = (ViewHolder) holder;
            Friend item = list_search.get(position);
//            Picasso.with(getContext()).load(item.getUserPicture()).resize(pictureWidth, pictureWidth).centerCrop().into(h.picture);
//            h.starttime.setText(TimeHelper.convertToNoYearSecond(item.getStartTime()));
            h.name.setText(item.getUserName());

        }
    }

    @Override
    public int getItemCount() {
        return list_search.size();
    }
    private class ViewHolder extends RecyclerView.ViewHolder {

        ImageView picture;

        TextView name;

        TextView add;


        ViewHolder(View v) {
            super(v);
            picture = (ImageView) v.findViewById(R.id.iv_item_rv_friend_search_picture);

            name = (TextView) v.findViewById(R.id.tv_item_rv_friend_search_name);

            add= (TextView) v.findViewById(R.id.tv_item_rv_friend_search_add);

        }
    }

}
