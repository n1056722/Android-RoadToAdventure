package ya.haojun.roadtoadventure.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
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
        list.add(new DrawerItem(R.drawable.ic_nav_personal, DrawerItem.PERSONAL));
        list.add(new DrawerItem(0, DrawerItem.PERSONAL_RECORD));
        list.add(new DrawerItem(0, DrawerItem.PERSONAL_FIREND));
        list.add(new DrawerItem(R.drawable.ic_nav_group, DrawerItem.GROUP));
        list.add(new DrawerItem(0, DrawerItem.GROUP_MY));
        list.add(new DrawerItem(R.drawable.ic_nav_challenge, DrawerItem.CHALLENGE));
        list.add(new DrawerItem(0, DrawerItem.CHALLENGE_MY));
        list.add(new DrawerItem(0, DrawerItem.CHALLENGE_GROUP));
        rv.setAdapter(new DrawerRVAdapter(this, list));


    }

    public void onDrawerItemClick(String name) {
        switch (name) {
            case DrawerItem.PERSONAL_RECORD:
                break;
            case DrawerItem.PERSONAL_FIREND:
                openActivity(FriendListActivity.class);
                break;
            case DrawerItem.GROUP_MY:
                break;
            case DrawerItem.CHALLENGE_MY:
                break;
            case DrawerItem.CHALLENGE_GROUP:
                break;
        }
    }

    private void initRecyclerView() {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_main);
        ArrayList<MainItem> list = new ArrayList<>();
        list.add(new MainItem(R.drawable.ic_record, MainItem.RECORD, ContextCompat.getColor(this, R.color.main_record), Color.BLACK));
        list.add(new MainItem(R.drawable.ic_position, MainItem.POSITION, ContextCompat.getColor(this, R.color.main_position), Color.BLACK));
        list.add(new MainItem(R.drawable.ic_tip, MainItem.TIP, ContextCompat.getColor(this, R.color.main_tip), Color.BLACK));
        list.add(new MainItem(R.drawable.ic_together, MainItem.TOGETHER, ContextCompat.getColor(this, R.color.main_together), Color.BLACK));
        list.add(new MainItem(R.drawable.ic_chat, MainItem.CHAT, ContextCompat.getColor(this, R.color.main_chat), Color.WHITE));
        list.add(new MainItem(R.drawable.ic_help, MainItem.HELP, ContextCompat.getColor(this, R.color.main_help), Color.BLACK));
        list.add(new MainItem(R.drawable.ic_challenge, MainItem.CHALLENGE, ContextCompat.getColor(this, R.color.main_challenge), Color.BLACK));
        list.add(new MainItem(R.drawable.ic_road_query, MainItem.ROAD_QUERY, ContextCompat.getColor(this, R.color.main_road_query), Color.WHITE));
        list.add(new MainItem(R.drawable.ic_group, MainItem.GROUP, ContextCompat.getColor(this, R.color.main_group), Color.BLACK));

        rv.setLayoutManager(new GridLayoutManager(this, 3));
        rv.setAdapter(new MainRVAdapter(this, list));
    }


    private long lastBackPressedTime = 0;

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }

        if (System.currentTimeMillis() - lastBackPressedTime < 2000) {
            super.onBackPressed();
        } else {
            lastBackPressedTime = System.currentTimeMillis();
            t("在按一次離開");
        }
    }
}
