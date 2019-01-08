package business.smart.smartbusinesscard;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class ShareCardActivity extends Activity {
    // TODO: NFC does two ways share, but unfortunately reading a QR does only
    // one way, can I do it better? Maybe I can use Wifi to share the card back,
    // or even bluetooth. Or also a WebServices, but its not worth it because
    // its not local and not as fast as the other alternatives

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_card_activity);
        setTitle(getResources().getString(R.string.share_card_activity_title));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO add scan button(with cammera icon)
        getMenuInflater().inflate(R.menu.share_card_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.leave_1, R.anim.leave_2);
    }
}

