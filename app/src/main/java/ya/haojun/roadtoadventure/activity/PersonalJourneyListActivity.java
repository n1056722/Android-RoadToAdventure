package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.PersonalJourneyListRVAdapter;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.model.User;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class PersonalJourneyListActivity extends CommonActivity implements View.OnClickListener {

    // request
    public static final int REQUEST_ADD_PERSONAL_JOURNEY = 0;
    public static final int REQUEST_PERSONAL_JOURNEY = 1;
    // ui
    private SwipeRefreshLayout srl;
    private RecyclerView rv;
    private FloatingActionButton fab_add_journey;
    // data
    private ArrayList<PersonalJourney> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_journey_list);

        // ui reference
        srl = (SwipeRefreshLayout) findViewById(R.id.srl_personal_journey_list);
        rv = (RecyclerView) findViewById(R.id.rv_personal_journey_list);
        fab_add_journey = (FloatingActionButton) findViewById(R.id.fab_personal_journey_list_add_journey);
        fab_add_journey.setOnClickListener(this);

        // init
        list = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PersonalJourneyListRVAdapter(this, list));
        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    fab_add_journey.hide();
                } else if (dy < 0) {
                    fab_add_journey.show();
                }
            }
        });
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srl.setRefreshing(true);
                getPersonalJourneyList();
            }
        });
        srl.setColorSchemeResources(
                android.R.color.holo_red_light,
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light);

        getPersonalJourneyList();
    }

    public void onItemClick(PersonalJourney item) {
        Bundle b = new Bundle();
        b.putInt("personalJourneyId", item.getPersonalJourneyId());
        openActivityForResult(PersonalJourneyActivity.class, REQUEST_PERSONAL_JOURNEY, b);
    }

    private void getPersonalJourneyList() {
        PersonalJourney params = new PersonalJourney();
        params.setUserId(User.getInstance().getUserId());

        Call<PersonalJourney> call = RoadToAdventureService.service.getPersonalJourneyList(params);
        srl.setRefreshing(true);
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                srl.setRefreshing(false);
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        list.clear();
                        list.addAll(result.getPersonalJourneys());
                        rv.getAdapter().notifyDataSetChanged();
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonalJourney> call, Throwable t) {
                srl.setRefreshing(false);
                t(t.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_personal_journey_list_add_journey:
                openActivityForResult(AddPersonalJourneyActivity.class, REQUEST_ADD_PERSONAL_JOURNEY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case REQUEST_ADD_PERSONAL_JOURNEY:
            case REQUEST_PERSONAL_JOURNEY:
                getPersonalJourneyList();
                break;
        }
    }
}
