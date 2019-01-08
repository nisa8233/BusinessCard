package business.smart.smartbusinesscard;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class EditCardActivity extends Activity {

    private EditText inputName;
    private EditText inputSurname;
    private EditText inputCompany;
    private EditText inputPosition;
    private EditText inputLocation;
    private EditText inputPhone;
    private EditText inputMail;
    private EditText inputLine;
    private EditText inputFacebook;
    private EditText inputWeb;
    private Long rowId;
    private DbCardAdapter dbAdapter;
    private boolean saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_card_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        saveChanges = true;
        inputName = (EditText) findViewById(R.id.card_edit_activity_name);
        inputSurname = (EditText) findViewById(R.id.card_edit_activity_surname);
        inputCompany = (EditText) findViewById(R.id.card_edit_activity_company);
        inputPosition = (EditText) findViewById(R.id.card_edit_activity_position);
        inputLocation = (EditText) findViewById(R.id.card_edit_activity_location);
        inputPhone = (EditText) findViewById(R.id.card_edit_activity_phone);
        inputMail = (EditText) findViewById(R.id.card_edit_activity_mail);
        inputLine = (EditText) findViewById(R.id.card_edit_activity_line);
        inputFacebook = (EditText) findViewById(R.id.card_edit_activity_facebook);
        inputWeb = (EditText) findViewById(R.id.card_edit_activity_web);
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
        if (rowId != null) {
            setTitle(getResources()
                    .getString(R.string.edit_card_activity_title));
            Cursor note = dbAdapter.fetchCard(rowId);
            startManagingCursor(note);
            inputName.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_NAME)));
            inputSurname.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_SURNAME)));
            inputCompany.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_COMPANY)));
            inputPosition.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_POSITION)));
            inputLocation.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_LOCATI)));
            inputPhone.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_PHONE)));
            inputMail.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_MAIL)));
            inputLine.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_LINE)));
            inputFacebook.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_FACEBOOK)));
            inputWeb.setText(note.getString(note
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_WEB)));
        } else {
            setTitle(getResources().getString(
                    R.string.create_card_activity_title));
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
        processForm();
        outState.putSerializable(DbCardAdapter.KEY_ID, rowId);
    }

    @Override
    protected void onPause() {
        super.onPause();
        processForm();
    }

    private void processForm() {
        if (saveChanges && formIsValid(false)) {
            if (rowId == null) {
                long id = dbAdapter.createNote(inputName.getText().toString(),
                        inputSurname.getText().toString(),
                        inputCompany.getText().toString(),
                        inputPosition.getText().toString(), inputLocation.getText().toString(),
                        inputPhone.getText().toString(), inputMail.getText().toString(),
                        inputLine.getText().toString(), inputFacebook.getText().toString(),
                        inputWeb.getText().toString());
                if (id > 0) {
                    rowId = id;
                }
            } else {
                dbAdapter.updateCard(rowId, inputName.getText().toString(),
                        inputSurname.getText().toString(),
                        inputCompany.getText().toString(),
                        inputPosition.getText().toString(), inputLocation.getText().toString(),inputPhone.getText()
                                .toString(), inputMail.getText().toString(),
                        inputLine.getText().toString(), inputFacebook
                                .getText().toString(), inputWeb.getText().toString());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // TODO: maybe there is a fancier way to do this
        if (rowId == null) {
            menuInflater.inflate(R.menu.create_card_menu, menu);
        } else {
            menuInflater.inflate(R.menu.edit_card_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitActivity();
                break;
            case R.id.card_create_menu_action_save:
            case R.id.card_edit_menu_action_save:
                if (formIsValid(true)) {
                    setResult(RESULT_OK); // TODO is really necessary?
                    exitActivity();
                }
                break;
            case R.id.card_create_menu_action_discard:
            case R.id.card_edit_menu_action_delete:
                saveChanges = false;
                if (rowId != null) {
                    dbAdapter.deleteCard(rowId);
                }
                setResult(ViewCardActivity.RESULT_DELETED);
                exitActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean formIsValid(boolean showCauses) {
        if (inputName.getText().length() == 0) {
            // TODO: use internationalization
            if (showCauses) {
                Toast.makeText(this, "Name is  compulsory", Toast.LENGTH_SHORT)
                        .show();
            }
            return false;
        }
        return true;
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

