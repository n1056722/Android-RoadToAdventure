package ya.haojun.roadtoadventure.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

/**
 * Created by asus on 2016/6/27.
 */
public abstract class CommonRVAdapter extends RecyclerView.Adapter {

    private final Context context;
    private final LayoutInflater inflater;
    private final Resources res;

    public CommonRVAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.res = context.getResources();
    }

    public Context getContext() {
        return context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public Resources getResources() {
        return res;
    }
}
