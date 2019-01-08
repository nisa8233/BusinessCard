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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class RegisterActivity extends Activity {

    private EditText inputUserName;
    private EditText inputPassword;
    private Long rowId;
    private DbCardAdapter dbAdapter;
    private boolean saveChanges;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.register_activity);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        saveChanges = true;
        inputUserName = (EditText) findViewById(R.id.user_edit_activity_username);
        inputPassword = (EditText) findViewById(R.id.user_edit_activity_password);

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




/**
 setContentView(R.layout.register_activity);

 //   Button meetingMode = (Button) findViewById(R.id.main_activity_meeting_mode);

 Button btconf = (Button) findViewById(R.id.confirmbutton);
 Button btcanc = (Button) findViewById(R.id.cancelbutton);

 btconf.setOnClickListener(new OnClickListener() {

@Override
public void onClick(View v) {


EditText name_et = (EditText) findViewById(R.id.nameid);
EditText sure_et = (EditText) findViewById(R.id.surenameid);
EditText comp_et = (EditText) findViewById(R.id.companyid);
EditText posi_et = (EditText) findViewById(R.id.positionid);
EditText loca_et = (EditText) findViewById(R.id.locationid);
EditText phon_et = (EditText) findViewById(R.id.phnumid);
EditText face_et = (EditText) findViewById(R.id.faceid);
EditText line_et = (EditText) findViewById(R.id.lineid);
EditText webs_et = (EditText) findViewById(R.id.websiteid);
EditText email_et = (EditText) findViewById(R.id.emailid);
EditText us_et = (EditText) findViewById(R.id.usernameid);
EditText pt_et = (EditText) findViewById(R.id.passwordid);

String name = name_et.getText().toString();
String sure = sure_et.getText().toString();
String comp = comp_et.getText().toString();
String posi = posi_et.getText().toString();
String loca = loca_et.getText().toString();
String phon = phon_et.getText().toString();
String face = face_et.getText().toString();
String line = line_et.getText().toString();
String webs = webs_et.getText().toString();
String email = email_et.getText().toString();
String user = us_et.getText().toString();
String pwd = pt_et.getText().toString();

Intent itn = new Intent(RegisterActivity.this, SaveActivity.class);
itn.putExtra("na", name);
itn.putExtra("su", sure);
itn.putExtra("co", comp);
itn.putExtra("po", posi);
itn.putExtra("lo", loca);
itn.putExtra("ph", phon);
itn.putExtra("fa", face);
itn.putExtra("li", line);
itn.putExtra("we", webs);
itn.putExtra("em", email);
itn.putExtra("nn", user);
itn.putExtra("pp", pwd);



startActivity(itn);
//   startActivity(new Intent(MainActivity.this,
//           MeetingModeActivity.class), bundleAnimation);
}
});

 btcanc.setOnClickListener(new OnClickListener() {

@Override
public void onClick(View v) {


EditText name_et = (EditText) findViewById(R.id.nameid);
EditText sure_et = (EditText) findViewById(R.id.surenameid);
EditText comp_et = (EditText) findViewById(R.id.companyid);
EditText posi_et = (EditText) findViewById(R.id.positionid);
EditText loca_et = (EditText) findViewById(R.id.locationid);
EditText phon_et = (EditText) findViewById(R.id.phnumid);
EditText face_et = (EditText) findViewById(R.id.faceid);
EditText line_et = (EditText) findViewById(R.id.lineid);
EditText webs_et = (EditText) findViewById(R.id.websiteid);
EditText email_et = (EditText) findViewById(R.id.emailid);
EditText us_et = (EditText) findViewById(R.id.usernameid);
EditText pt_et = (EditText) findViewById(R.id.passwordid);

String name = name_et.getText().toString();
String sure = sure_et.getText().toString();
String comp = comp_et.getText().toString();
String posi = posi_et.getText().toString();
String loca = loca_et.getText().toString();
String phon = phon_et.getText().toString();
String face = face_et.getText().toString();
String line = line_et.getText().toString();
String webs = webs_et.getText().toString();
String email = email_et.getText().toString();
String user = us_et.getText().toString();
String pwd = pt_et.getText().toString();

Intent itn = new Intent(RegisterActivity.this, LoginActivity.class);
itn.putExtra("na", name);
itn.putExtra("su", sure);
itn.putExtra("co", comp);
itn.putExtra("po", posi);
itn.putExtra("lo", loca);
itn.putExtra("ph", phon);
itn.putExtra("fa", face);
itn.putExtra("li", line);
itn.putExtra("we", webs);
itn.putExtra("em", email);
itn.putExtra("nn", user);
itn.putExtra("pp", pwd);



startActivity(itn);
//   startActivity(new Intent(MainActivity.this,
//           MeetingModeActivity.class), bundleAnimation);
}
});


 */
    }


    private void prepareForm() {
        if (rowId != null) {
            setTitle(getResources()
                    .getString(R.string.edit_user_activity_title));
            Cursor user = dbAdapter.fetchUser(rowId);
            startManagingCursor(user);
            inputUserName.setText(user.getString(user
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_USERNAME)));
            inputPassword.setText(user.getString(user
                    .getColumnIndexOrThrow(DbCardAdapter.KEY_PASSWORD)));





        } else {
            setTitle(getResources().getString(
                    R.string.create_user_activity_title));
        }
    }

/**********************************************************************/
/***********************************************************************/
    /***********************************************************************/

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
                long id = dbAdapter.createregister(inputUserName.getText().toString(),
                        inputPassword.getText().toString()
                        /**
                         inputPhone.getText().toString(),
                         inputMail.getText().toString(),
                         inputLinkedin.getText().toString(),
                         inputFacebook.getText().toString(),
                         inputTwitter.getText().toString(),
                         inputBlogger.getText().toString(),
                         inputFlickr.getText().toString(),
                         inputSkype.getText().toString(),
                         inputWordpress.getText().toString(),
                         inputWeb.getText().toString()
                         */

                );
                if (id > 0) {
                    rowId = id;
                }
            } else {
                /**
                 dbAdapter.updateCard(rowId, inputUserName.getText().toString(),
                 inputPassword.getText().toString()
                 );

                 */

            }
        }
    }








/******************************************************************************/
/*******************************************************************************/
    /*******************************************************************************/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        // TODO: maybe there is a fancier way to do this
        if (rowId == null) {
            menuInflater.inflate(R.menu.create_user_menu, menu);
        } else {
            menuInflater.inflate(R.menu.edit_user_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                exitActivity();
                break;
            case R.id.user_create_menu_action_save:
            case R.id.user_edit_menu_action_save:
                if (formIsValid(true)) {
                    setResult(RESULT_OK); // TODO is really necessary?
                    exitActivity();
                }
                break;
            case R.id.user_create_menu_action_discard:
            case R.id.user_edit_menu_action_delete:
                saveChanges = false;
                if (rowId != null) {
                    //  dbAdapter.deleteCard(rowId);
                }
                setResult(ViewCardActivity.RESULT_DELETED);
                exitActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean formIsValid(boolean showCauses) {
        if (inputUserName.getText().length() == 0) {
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

