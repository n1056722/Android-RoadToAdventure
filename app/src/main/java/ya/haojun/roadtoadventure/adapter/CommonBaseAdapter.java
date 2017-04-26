package ya.haojun.roadtoadventure.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Created by asus on 2016/5/17.
 */
public abstract class CommonBaseAdapter extends BaseAdapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final Resources resources;

    public CommonBaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.resources = context.getResources();
    }


    public Resources getResources() {
        return resources;
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public abstract int getCount();

    public abstract Object getItem(int position);

    public abstract long getItemId(int position);

    public abstract View getView(int position, View convertView, ViewGroup parent);

    boolean isBetween(int a, int x, int b){
        return a < x && x < b;
    }
}
