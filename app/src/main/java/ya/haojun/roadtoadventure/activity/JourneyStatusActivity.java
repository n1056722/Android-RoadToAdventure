package ya.haojun.roadtoadventure.activity;

import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import ya.haojun.roadtoadventure.R;
import ya.haojun.roadtoadventure.helper.TimeHelper;
import ya.haojun.roadtoadventure.model.JourneyModel;
import ya.haojun.roadtoadventure.sqlite.DAOJourney;

public class JourneyStatusActivity extends CommonActivity implements View.OnClickListener {

    // ui
    private EditText et_name, et_content;
    private TextView tv_time, tv_start_stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journey_status);
        // ui reference
        et_name = (EditText) findViewById(R.id.et_journey_status_name);
        et_content = (EditText) findViewById(R.id.et_journey_status_content);
        tv_time = (TextView) findViewById(R.id.tv_journey_status_time);
        tv_start_stop = (TextView) findViewById(R.id.tv_journey_status_start_stop);
        // listener
        tv_start_stop.setOnClickListener(this);
        // init
        init();
    }

    private void init() {
        if (isRunning()) {
            // set text
            DAOJourney daoJourney = new DAOJourney(this);
            JourneyModel jm = daoJourney.getLast();
            tv_start_stop.setText(getString(R.string.status_stop));
            et_name.setText(jm.getJourneyName());
            et_content.setText(jm.getJourneyContent());
            et_name.setEnabled(false);
            et_content.setEnabled(false);
            // refresh time
            startRefresh();
            Message msg = new Message();
            msg.obj = jm.getStartTime();
            handler.sendMessage(msg);
        }
    }

    private boolean isRunning() {
        DAOJourney daoJourney = new DAOJourney(this);
        return daoJourney.getCount() != 0 && daoJourney.getLast().getStopTime().isEmpty();
    }

    private static final int UPDATE_TIME = 1000;
    private boolean refreshing;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String startTime = (String) msg.obj;
            tv_time.setText(TimeHelper.gap(startTime, TimeHelper.now()));
            if (refreshing) {
                Message msg_new = new Message();
                msg_new.obj = startTime;
                handler.sendMessageDelayed(msg_new, UPDATE_TIME);
            }

        }
    };

    private void startRefresh() {
        refreshing = true;
    }

    private void stopRefresh() {
        refreshing = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_journey_status_start_stop:
                if (isRunning()) {
                    // stop refresh
                    stopRefresh();
                    // update model
                    DAOJourney daoJourney = new DAOJourney(this);
                    JourneyModel jm = daoJourney.getLast();
                    jm.setStopTime(TimeHelper.now());
                    if (daoJourney.update(jm)) {
                        openActivity(JourneyListActivity.class);
                        finish();
                    }
                } else {
                    String name = et_name.getText().toString();
                    String content = et_content.getText().toString();
                    if (name.isEmpty() || content.isEmpty()) return;
                    // create model
                    JourneyModel jm = new JourneyModel();
                    jm.setJourneyName(name);
                    jm.setJourneyContent(content);
                    jm.setStartTime(TimeHelper.now());
                    jm.setStopTime("");
                    // insert
                    if (new DAOJourney(this).insert(jm)) {
                        et_name.setEnabled(false);
                        et_content.setEnabled(false);
                        tv_start_stop.setText(getString(R.string.status_stop));
                        // refresh view
                        refreshing = true;
                        Message msg = new Message();
                        msg.obj = jm.getStartTime();
                        handler.sendMessage(msg);
                    }
                }

                break;
        }
    }
}
