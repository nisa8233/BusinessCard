package swe.omm.omm;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


public class LoginActivity extends Activity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.login_activity);



        Button btlogin = (Button) findViewById(R.id.loginbutton);

        btlogin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {


                EditText us_et = (EditText) findViewById(R.id.usernameid);
                EditText pt_et = (EditText) findViewById(R.id.passwordid);


                String user = us_et.getText().toString();
                String pwd = pt_et.getText().toString();

                Intent itn = new Intent(LoginActivity.this, SaveActivity.class);
                itn.putExtra("uu", user);
                itn.putExtra("pp", pwd);




                startActivity(itn);
                //   startActivity(new Intent(MainActivity.this,
                //           MeetingModeActivity.class), bundleAnimation);
            }
        });

    }

    public void save (View v) {


    }

}












