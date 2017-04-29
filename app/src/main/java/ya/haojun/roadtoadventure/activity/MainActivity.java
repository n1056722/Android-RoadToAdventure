package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.DrawerRVAdapter;
import ya.haojun.roadtoadventure.adapter.MainRVAdapter;
import ya.haojun.roadtoadventure.model.DrawerItem;
import ya.haojun.roadtoadventure.model.MainItem;

public class MainActivity extends CommonActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbarDrawer();
        initRecyclerView();
    }

    private void initToolbarDrawer() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // drawer connect
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // RecyclerView
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_main_navigation);
        rv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<DrawerItem> list = new ArrayList<>();
        list.add(new DrawerItem(R.drawable.ic_bike_w, DrawerItem.SIGN_OUT));
        rv.setAdapter(new DrawerRVAdapter(this, list));
    }

    private void initRecyclerView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_main);
        ArrayList<MainItem> list = new ArrayList<>();
        list.add(new MainItem(R.drawable.ic_record, MainItem.RECORD, ContextCompat.getColor(this, R.color.main_record)));
        list.add(new MainItem(R.drawable.ic_position, MainItem.POSITION, ContextCompat.getColor(this, R.color.main_position)));
        list.add(new MainItem(R.drawable.ic_tip, MainItem.TIP, ContextCompat.getColor(this, R.color.main_tip)));
        list.add(new MainItem(R.drawable.ic_together, MainItem.TOGETHER, ContextCompat.getColor(this, R.color.main_together)));
        list.add(new MainItem(R.drawable.ic_chat, MainItem.CHAT, ContextCompat.getColor(this, R.color.main_chat)));
        list.add(new MainItem(R.drawable.ic_help, MainItem.HELP, ContextCompat.getColor(this, R.color.main_help)));
        list.add(new MainItem(R.drawable.ic_challenge, MainItem.CHALLENGE, ContextCompat.getColor(this, R.color.main_challenge)));
        list.add(new MainItem(R.drawable.ic_road_query, MainItem.ROAD_QUERY, ContextCompat.getColor(this, R.color.main_road_query)));

        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(new MainRVAdapter(this, list));
    }

    public void onDrawerItemClick(String name) {
        switch (name) {
            case DrawerItem.SIGN_OUT:
                finish();
                break;
        }
    }

    private long lastBackPressedTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = System.currentTimeMillis();
            t("在按一次離開");
        }
    }
}
