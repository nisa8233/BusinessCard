package business.smart.smartbusinesscard;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewCardActivity extends Activity {

    private TextView nameAndSurnameTextView;
    private TextView companyTextView;
    private TextView positionTextView;
    private TextView locationTextView;
    private TextView phoneTextView;
    private TextView mailTextView;
    private ImageView lineImageView;
    private ImageView facebookImageView;
    private ImageView webImageView;
    private Long rowId;
    private DbCardAdapter dbAdapter;

    private static boolean isDeleted;

    Bundle bundleAnimation;

    private static final int ACTIVITY_EDIT = 1;
    public static final int RESULT_DELETED = 137;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_card_activity);



        bundleAnimation = ActivityOptions.makeCustomAnimation(
                getApplicationContext(), R.anim.enter_1, R.anim.enter_2)
                .toBundle();

        if (isDeleted) {
            finish();
            return;
        }

        setTitle(getResources().getString(R.string.view_card_activity_title));

        getActionBar().setDisplayHomeAsUpEnabled(true);
        nameAndSurnameTextView = (TextView) findViewById(R.id.card_view_activity_name_and_surname);
        companyTextView = (TextView) findViewById(R.id.card_view_activity_company);
        positionTextView = (TextView) findViewById(R.id.card_view_activity_position);
        locationTextView = (TextView) findViewById(R.id.card_view_activity_location);

        phoneTextView = (TextView) findViewById(R.id.card_view_activity_phone);
        mailTextView = (TextView) findViewById(R.id.card_view_activity_mail);
        lineImageView = (ImageView) findViewById(R.id.card_view_activity_icon_line);
        facebookImageView = (ImageView) findViewById(R.id.card_view_activity_icon_facebook);
        webImageView = (ImageView) findViewById(R.id.card_view_activity_icon_web);

        dbAdapter = new DbCardAdapter(this);
        dbAdapter.open();
        rowId = (savedInstanceState == null) ? null : (Long) savedInstanceState
                .getSerializable(DbCardAdapter.KEY_ID);
        if (rowId == null) {
            Bundle extras = getIntent().getExtras();
            rowId = extras != null ? extras.getLong(DbCardAdapter.KEY_ID)
                    : null;
        }
        prepareForm();
    }

    private void prepareForm() {
        Cursor cardCursor = dbAdapter.fetchCard(rowId);
        startManagingCursor(cardCursor);
        nameAndSurnameTextView.setText(cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_NAME))
                + " "
                + cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_SURNAME)));
        companyTextView.setText(cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_COMPANY)));
        positionTextView.setText(cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_POSITION)));
        locationTextView.setText(cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_LOCATI)));

        phoneTextView.setText(cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_PHONE)));
        mailTextView.setText(cardCursor.getString(cardCursor
                .getColumnIndexOrThrow(DbCardAdapter.KEY_MAIL)));
        prepareIcon(cardCursor, lineImageView, DbCardAdapter.KEY_LINE);
        prepareIcon(cardCursor, facebookImageView, DbCardAdapter.KEY_FACEBOOK);
        prepareIcon(cardCursor, webImageView, DbCardAdapter.KEY_WEB);
    }

    private void prepareIcon(Cursor card, ImageView icon, String key) {
        // TODO add a handler to launch different
        String content = card.getString(card.getColumnIndexOrThrow(key));
        if (content != null && !content.isEmpty()) {
            icon.setVisibility(View.VISIBLE);
            icon.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(ViewCardActivity.this,
                            "TODO: add functionality", Toast.LENGTH_SHORT)
                            .show();
                }
            });
        } else {
            icon.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareForm();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DbCardAdapter.KEY_ID, rowId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_card_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitActivity();
                break;
            case R.id.card_view_menu_action_edit:
                Intent i = new Intent(this, EditCardActivity.class);
                i.putExtra(DbCardAdapter.KEY_ID, rowId);
                startActivityForResult(i, ACTIVITY_EDIT, bundleAnimation);
                break;
            case R.id.card_view_menu_action_print:
                break;
            case R.id.card_view_menu_action_share:
                startActivity(new Intent(ViewCardActivity.this,
                        ShareCardActivity.class), bundleAnimation);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_DELETED) {
            finish();
            return;
        }
        prepareForm();
    }

    @Override
    public void onBackPressed() {
        exitActivity();
    }

    private void exitActivity() {
        finish();
        overridePendingTransition(R.anim.leave_1, R.anim.leave_2);
    }

}

