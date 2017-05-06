package ya.haojun.roadtoadventure.activity;

import android.os.Bundle;
import android.view.View;

import ya.haojun.roadtoadventure.R;

public class AddPersonalJourneyActivity extends CommonActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_personal_journey);

        findViewById(R.id.tv_add_personal_journey_direction).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_add_personal_journey_direction:
                openActivity(SelectDistanceMapActivity.class);
                break;
        }
    }
}
