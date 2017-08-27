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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.DrawerRVAdapter;
import ya.haojun.roadtoadventure.adapter.MainRVAdapter;
import ya.haojun.roadtoadventure.helper.LogHelper;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.helper.YahooWeatherHelper;
import ya.haojun.roadtoadventure.model.DrawerItem;
import ya.haojun.roadtoadventure.model.LocationRecordModel;
import ya.haojun.roadtoadventure.model.MainItem;
import ya.haojun.roadtoadventure.retrofit.YahooWeatherService;
import ya.haojun.roadtoadventure.sqlite.DAOLocationRecord;

public class MainActivity extends CommonActivity {

    // ui
    private ImageView iv_weather_image;
    private TextView tv_weather_date, tv_weather_name, tv_weather_temperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ui reference
        iv_weather_image = (ImageView) findViewById(R.id.iv_main_weather_image);
        tv_weather_date = (TextView) findViewById(R.id.tv_main_weather_date);
        tv_weather_name = (TextView) findViewById(R.id.tv_main_weather_name);
        tv_weather_temperature = (TextView) findViewById(R.id.tv_main_weather_temperature);

        // init
        initToolbar();
        initDrawer();
        initMainGrid();
        // get Weather
        getWeather();

        LogHelper.d(new DAOLocationRecord(this).getCount() + "");
    }

    private void initToolbar() {
        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        // drawer connect
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.app_name, R.string.app_name);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initDrawer() {
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
        list.add(new DrawerItem(R.drawable.ic_nav_road_query, DrawerItem.ROAD_QUERY));
        list.add(new DrawerItem(R.drawable.ic_nav_tip, DrawerItem.TIP));
        list.add(new DrawerItem(R.drawable.ic_nav_help, DrawerItem.HELP));
        rv.setAdapter(new DrawerRVAdapter(this, list));
    }

    public void onDrawerItemClick(String name) {
        switch (name) {
            case DrawerItem.PERSONAL_RECORD:
                openActivity(PersonalJourneyListActivity.class);
                break;
            case DrawerItem.PERSONAL_FIREND:
                openActivity(FriendListActivity.class);
                break;
            case DrawerItem.GROUP_MY:
                openActivity(GroupListActivity.class);
                break;
            case DrawerItem.CHALLENGE_MY:
                break;
            case DrawerItem.CHALLENGE_GROUP:
                break;
        }
    }

    private void initMainGrid() {
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

    public void onMainItemClick(String name) {
        switch (name) {
            case MainItem.RECORD:
                openActivity(PersonalJourneyListActivity.class);
                break;
            case MainItem.POSITION:
                break;
            case MainItem.TIP:
                break;
            case MainItem.TOGETHER:
                break;
            case MainItem.CHAT:
                openActivity(FriendListActivity.class);
                break;
            case MainItem.HELP:
                openActivity(HelpActivity.class);
                break;
            case MainItem.CHALLENGE:
                break;
            case MainItem.ROAD_QUERY:
                break;
            case MainItem.GROUP:
                openActivity(GroupListActivity.class);
                break;

        }
    }

    private void getWeather() {
        findViewById(R.id.ll_main_weather_loading).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_main_weather_result).setVisibility(View.GONE);

        LocationRecordModel l = new DAOLocationRecord(this).getLast();
        Call<String> call = YahooWeatherService.service.getWeather(YahooWeatherHelper.getWeatherQuery(l.getLatitude(), l.getLongitude()));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String result = response.body();
                try {
                    JSONObject j = new JSONObject(result);
                    JSONObject j_condition = j.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition");
                    String code = j_condition.getString("code");
                    String temp = j_condition.getString("temp");

                    findViewById(R.id.ll_main_weather_loading).setVisibility(View.GONE);
                    findViewById(R.id.ll_main_weather_result).setVisibility(View.VISIBLE);
                    iv_weather_image.setImageResource(YahooWeatherHelper.getWeatherPicture(code));
                    tv_weather_date.setText(TimeHelper.convertToNoYearSecond(TimeHelper.now()));
                    tv_weather_name.setText(YahooWeatherHelper.getWeatherName(code));
                    tv_weather_temperature.setText(YahooWeatherHelper.getWeatherTemp(temp));
                } catch (JSONException e) {
                    e.printStackTrace();
                    t("取得天氣失敗(parser)");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t("取得天氣失敗(" + t.toString() + ")");
            }
        });
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
