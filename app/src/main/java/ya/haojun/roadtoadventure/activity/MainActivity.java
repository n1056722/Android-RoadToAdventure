package ya.haojun.roadtoadventure.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
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

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.DrawerRVAdapter;
import ya.haojun.roadtoadventure.adapter.MainRVAdapter;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.DrawerItem;
import ya.haojun.roadtoadventure.model.MainItem;
import zh.wang.android.yweathergetter4a.WeatherInfo;
import zh.wang.android.yweathergetter4a.YahooWeather;
import zh.wang.android.yweathergetter4a.YahooWeatherInfoListener;

import static ya.haojun.roadtoadventure.activity.PermissionActivity.PERMISSION;

public class MainActivity extends CommonActivity implements YahooWeatherInfoListener {

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
        initToolbarDrawer();
        initRecyclerView();
        // get Weather
        getWeather();
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

    public void onMainItemClick(String name) {
        switch (name) {
            case MainItem.RECORD:
                openActivity(PersonalJourneyListActivity.class);
                break;
            case MainItem.GROUP:
                openActivity(GroupListActivity.class);
                break;
        }
    }

    private void getWeather() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION);
        } else {
            findViewById(R.id.ll_main_weather_loading).setVisibility(View.VISIBLE);
            findViewById(R.id.ll_main_weather_result).setVisibility(View.GONE);
            YahooWeather mYahooWeather = YahooWeather.getInstance();
            mYahooWeather.queryYahooWeatherByGPS(this, this);
        }
    }

    @Override
    public void gotWeatherInfo(WeatherInfo weatherInfo, YahooWeather.ErrorType errorType) {
        findViewById(R.id.ll_main_weather_loading).setVisibility(View.GONE);
        findViewById(R.id.ll_main_weather_result).setVisibility(View.VISIBLE);
        if (weatherInfo != null) {
            iv_weather_image.setImageResource(getWeatherImage(weatherInfo.getCurrentText()));
            tv_weather_date.setText(TimeHelper.convertToNoYearSecond(TimeHelper.now()));
            tv_weather_name.setText(getWeatherName(weatherInfo.getCurrentText()));
            tv_weather_temperature.setText(weatherInfo.getCurrentTemp() + "ºC");
        } else {
            t("取得天氣失敗");
        }
    }

    private int getWeatherImage(String weather) {
        switch (weather) {
            case "Light Rain":
                return R.drawable.wh_rain;
            case "Showers":
                return R.drawable.wh_rain;
            case "Scattered Showers":
                return R.drawable.wh_rain;
            case "Windy":
                return R.drawable.wh_rain;
            case "Mostly Cloudy":
                return R.drawable.wh_double_cloud;
            case "Partly Cloudy":
                return R.drawable.wh_cloud;
            case "Cloudy":
                return R.drawable.wh_cloud;
            case "Sunny":
                return R.drawable.wh_sun;
            case "Mostly Sunny":
                return R.drawable.wh_sun;
            case "Haze":
                return R.drawable.wh_haze;
            case "Fog":
                return R.drawable.wh_haze;
            case "Thunderstorms":
                return R.drawable.wh_thunder;
            case "Breezy":
                return R.drawable.wh_wind;
            default:
                return R.drawable.ic_clear_w;
        }
    }

    private String getWeatherName(String weather) {
        switch (weather) {
            case "Light Rain":
                return "小雨";
            case "Showers":
                return "陣雨";
            case "Scattered Showers":
                return "零星陣雨";
            case "Windy":
                return "刮風";
            case "Mostly Cloudy":
                return "大多時間多雲";
            case "Partly Cloudy":
                return "部份多雲";
            case "Cloudy":
                return "多雲";
            case "Sunny":
                return "晴朗";
            case "Mostly Sunny":
                return "大多時間晴朗";
            case "Haze":
                return "陰霾";
            case "Fog":
                return "多霧";
            case "Thunderstorms":
                return "雷雨";
            case "Breezy":
                return "微風";
            default:
                return "未知";
        }
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
