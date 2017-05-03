package ya.haojun.roadtoadventure.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import ya.haojun.roadtoadventure.R;

public class SearchFriendActivity extends CommonActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friend);

        // ui reference
        findViewById(R.id.tv_search_friend_search).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_search_friend_search:
                String input = ((EditText)findViewById(R.id.et_search_friend_input)).getText().toString();
                if(input.isEmpty()){
                    t("請輸入");
                    return;
                }
                break;
        }
    }
}
