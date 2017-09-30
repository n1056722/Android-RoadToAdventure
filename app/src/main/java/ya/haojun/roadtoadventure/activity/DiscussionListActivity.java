package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.adapter.DiscussionListRVAdapter;
import ya.haojun.roadtoadventure.model.PersonalJourney;
import ya.haojun.roadtoadventure.retrofit.RoadToAdventureService;

public class DiscussionListActivity extends CommonActivity {

    // request
    public static final int REQUEST_DISCUSSION = 0;
    // ui
    private RecyclerView rv;
    // data
    private ArrayList<PersonalJourney> list_personal_journey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_list);

        // ui reference
        rv = (RecyclerView) findViewById(R.id.rv_discussion_list);

        // init
        list_personal_journey = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new DiscussionListRVAdapter(this, list_personal_journey));

        getPublicPersonalJourney();
    }

    private void getPublicPersonalJourney() {
        PersonalJourney params = new PersonalJourney();

        Call<PersonalJourney> call = RoadToAdventureService.service.getPublicPersonalJourneyList(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
                if (isResponseOK(response)) {
                    PersonalJourney result = response.body();
                    if (result.isSuccess()) {
                        list_personal_journey.clear();
                        list_personal_journey.addAll(result.getPersonalJourneys());
                        rv.getAdapter().notifyDataSetChanged();
                    } else {
                        t(R.string.empty);
                    }
                }
            }

            @Override
            public void onFailure(Call<PersonalJourney> call, Throwable t) {
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }

    public void onItemClick(PersonalJourney item) {
        Bundle b = new Bundle();
        b.putInt("personalJourneyId", item.getPersonalJourneyId());
        openActivityForResult(DiscussionActivity.class, REQUEST_DISCUSSION, b);
    }
}
