package ya.haojun.roadtoadventure.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.fragment.MainFragment;
import ya.haojun.roadtoadventure.helper.PicassoViewAnimator;
import ya.haojun.roadtoadventure.model.MenuItem;
import ya.haojun.roadtoadventure.model.SampleScreenShotable;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.util.ViewAnimator;

public class MainActivity extends CommonActivity implements ViewAnimator.ViewAnimatorListener {

    // request
    public static final int REQUEST_SELECT_DISTANCE = 0;

    // ui
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private PicassoViewAnimator viewAnimator;
    private LinearLayout linearLayout;

    // data
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ui reference
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        // init
        initDrawer();
        initActionBar();
        initMenu();
        switchFragment(mainFragment = MainFragment.getInstance());
    }

    private void initDrawer() {
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });
    }

    private void initActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.text_open,  /* "open drawer" description */
                R.string.text_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(drawerToggle);
    }

    private void initMenu() {
        List<MenuItem> list = new ArrayList<>();
        list.add(new MenuItem(MenuItem.CLOSE, R.drawable.ic_nav_close));
        list.add(new MenuItem(MenuItem.PERSONAL, R.drawable.ic_nav_personal));
        list.add(new MenuItem(MenuItem.GROUP, R.drawable.ic_nav_group));
        list.add(new MenuItem(MenuItem.CHALLENGE, R.drawable.ic_nav_challenge));
        list.add(new MenuItem(MenuItem.INTERACTIVE, R.drawable.ic_nav_interactive));
        list.add(new MenuItem(MenuItem.SHOPPING, R.drawable.ic_nav_shopping));
        list.add(new MenuItem(MenuItem.SETTING, R.drawable.ic_nav_setting));
        list.add(new MenuItem(MenuItem.EXIT, R.drawable.ic_nav_exit));
        viewAnimator = new PicassoViewAnimator<>(this, list, new SampleScreenShotable(), drawerLayout, this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case MenuItem.CLOSE:
                break;
            case MenuItem.PERSONAL:
                alertWithItem(new String[]{"選一段路程", "我的路程紀錄", "好友"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                openActivityForResult(SelectDistanceMapActivity.class, REQUEST_SELECT_DISTANCE);
                                break;
                            case 1:
                                openActivity(JourneyListActivity.class);
                                break;
                        }
                    }
                });
                break;
            case MenuItem.GROUP:
                alertWithItem(new String[]{"群組"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                        }
                    }
                });
                break;
            case MenuItem.CHALLENGE:
                alertWithItem(new String[]{"自我挑戰", "他人挑戰"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                        }
                    }
                });
                break;
            case MenuItem.INTERACTIVE:
                alertWithItem(new String[]{"留言板", "即時路況", "路程攻略", "緊急救助", "客棧旅社"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                break;
                        }
                    }
                });
                break;
            case MenuItem.SHOPPING:
                t("購物商城");
                break;
            case MenuItem.SETTING:
                t("設定");
                break;
            case MenuItem.EXIT:
                finish();
                break;
        }
        return new SampleScreenShotable();
    }


    private void switchFragment(Fragment f) {
        if (f != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, f).commit();
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_SELECT_DISTANCE:
                    LatLng from = data.getParcelableExtra("from");
                    LatLng to = data.getParcelableExtra("to");
                    mainFragment.clearMarker();
                    mainFragment.loadDirections(from, to);
                    break;
            }
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
