package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.PersonalJourneyListRVAdapter;
import ya.haojun.roadtoadventure.model.PersonalJourney;

public class PersonalJourneyListActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private RecyclerView rv;
    // data
    private ArrayList<PersonalJourney> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_journey_list);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_personal_journey_list);
        findViewById(R.id.iv_personal_journey_list_add_record).setOnClickListener(this);
        // init RecyclerView
        list = new ArrayList<>();
        String s1 = "", s2 = "", s3 = "";
        for (int i = 0; i < 100; i++) {
            s1 += "金山";
            s2 += "三坑";
            s3 += "后豐";
        }
//        list.add(new PersonalJourney("金山自由行", s1, "0", "http://2.blog.xuite.net/2/8/0/e/244134372/blog_4539270/txt/331642785/0.jpg", "2017-05-05 00:00:00"));
//        list.add(new PersonalJourney("三坑鐵馬行", s2, "1", "http://i.imgur.com/ar9eLff.jpg", "2017-05-05 00:00:00"));
//        list.add(new PersonalJourney("后豐鐵馬行", s3, "2", "http://www.tonyhuang39.com/tony0821/20110114b_04.JPG", "2017-05-05 00:00:00"));

        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PersonalJourneyListRVAdapter(this, list));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_personal_journey_list_add_record:
                openActivity(AddPersonalJourneyActivity.class);
                break;
        }
    }
}
