package ya.haojun.roadtoadventure.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;

/**
 * Created by asus on 2017/1/4.
 */

public abstract class CommonPagerAdapter extends PagerAdapter {
    private final Context context;
    private final Resources resources;
    private final LayoutInflater inflater;

    protected CommonPagerAdapter(Context context) {
        this.context = context;
        this.resources = context.getResources();
        this.inflater = LayoutInflater.from(context);
    }

    public Context getContext() {
        return context;
    }

    public Resources getResources() {
        return resources;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }
}
