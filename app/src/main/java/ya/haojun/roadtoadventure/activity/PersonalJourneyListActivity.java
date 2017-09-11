package ya.haojun.roadtoadventure.activity;

import android.content.Intent;
import android.os.Bundle;
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
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new PersonalJourneyListRVAdapter(this, list));
        getPersonalJourneyList();
    }

    public void onItemClick(PersonalJourney item){
        Bundle b = new Bundle();
        b.putInt("personalJourneyId", item.getPersonalJourneyId());
        openActivity(PersonalJourneyActivity.class, b);
    }

    private void getPersonalJourneyList() {
        PersonalJourney params = new PersonalJourney();
        params.setUserId(User.getInstance().getUserId());

        Call<PersonalJourney> call = RoadToAdventureService.service.getPersonalJourneyList(params);
        showLoadingDialog();
        call.enqueue(new Callback<PersonalJourney>() {
            @Override
            public void onResponse(Call<PersonalJourney> call, Response<PersonalJourney> response) {
                dismissLoadingDialog();
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
                dismissLoadingDialog();
                t(t.toString());
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_personal_journey_list_add_record:
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
                getPersonalJourneyList();
                break;
        }
    }
}
