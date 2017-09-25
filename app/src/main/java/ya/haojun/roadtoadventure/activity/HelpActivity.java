package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.HelpFragmentPagerAdapter;
import ya.haojun.roadtoadventure.fragment.HelpFragment;

public class HelpActivity extends CommonActivity {

    // ui
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        // ui reference
        tabLayout = (TabLayout) findViewById(R.id.tl_help);
        viewPager = (ViewPager) findViewById(R.id.vp_help);

        // init TabLayout , ViewPager
        HelpFragmentPagerAdapter adapter = new HelpFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(HelpFragment.getInstance(HelpFragment.HOSPITAL), "醫院");
        adapter.addFragment(HelpFragment.getInstance(HelpFragment.HOTEL), "旅社");
        adapter.addFragment(HelpFragment.getInstance(HelpFragment.BIKE_SHOP), "腳踏車店");
        adapter.addFragment(HelpFragment.getInstance(HelpFragment.CONVENIENT_SHOP), "便利商店");
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setupWithViewPager(viewPager);
    }
}
