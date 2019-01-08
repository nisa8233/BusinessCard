package business.smart.smartbusinesscard;

import android.app.Activity;
import android.os.Bundle;

public class MeetingModeActivity extends Activity {

    // TODO: maybe I can achiev this by only doing wifi broadcast, or bluetooth
    // broadcast, without the need of touching cellphones with NFC, or using QR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_mode_activity);
        setTitle(getResources().getString(
                R.string.meeting_mode_activity_title_pairing));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.leave_1, R.anim.leave_2);
    }
}
