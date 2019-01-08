package swe.omm.omm;

import android.app.ActionBar;
import android.app.ActivityOptions;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toolbar;


public class MainActivity extends ListActivity   {
	// Fancy show as a real business card
	// print it with cloud printing form android

	// al tener la app abierta, al tocarlo con otro dispositivo te pregunta que
	// tarjeta queres compartir
	// si tenias una tarjeta seleccionada, se envia automaticamente

	// si estas en un evento podes usar el celular como stand de tu nombre,
	// quizas mostrnaod un video de internet y cada tanto mostrando tus datos
	// personlaes, nombre, titulo... etc

	// TODO: add a directory(contacts), and show the cards of the people I have
	// received, but also when you receive a new card it would be cool to have
	// their information automatically added to your contacts list, you may ask
	// the user what to do.

	private static final int ACTIVITY_CREATE = 0;
	private static final int ACTIVITY_REGISTER = 0;

	private static final int DELETE_ID = Menu.FIRST;


	private DbCardAdapter dbAdapter;
	Bundle bundleAnimation;






	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);





		dbAdapter = new DbCardAdapter(this);
		dbAdapter.open();



		fillData();
		//filluser();



		registerForContextMenu(getListView());

		bundleAnimation = ActivityOptions.makeCustomAnimation(
				getApplicationContext(), R.anim.enter_1, R.anim.enter_2)
				.toBundle();



		Button meetingMode = (Button) findViewById(R.id.main_activity_meeting_mode);


		meetingMode.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {


				startActivity(new Intent(MainActivity.this,
						MeetingModeActivity.class), bundleAnimation);
			}
		});
	}



	private void filluser(){

        Cursor userCursor = dbAdapter.fetchAllUsers();
        startManagingCursor(userCursor);
        String[] from = new String[] { DbCardAdapter.KEY_NAME };
        int[] to = new int[] { R.id.text1 };
        SimpleCursorAdapter users = new SimpleCursorAdapter(this,
                R.layout.main_activity_row, userCursor, from, to);
        setListAdapter(users);
    }



	@SuppressWarnings("deprecation")
	private void fillData() {
		Cursor notesCursor = dbAdapter.fetchAllCards();
		startManagingCursor(notesCursor);
		String[] from = new String[] { DbCardAdapter.KEY_NAME };
		int[] to = new int[] { R.id.text1 };
		SimpleCursorAdapter notes = new SimpleCursorAdapter(this,
				R.layout.main_activity_row, notesCursor, from, to);
		setListAdapter(notes);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.add(0, DELETE_ID, 0, "Delete");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
					.getMenuInfo();
			dbAdapter.deleteCard(info.id);
			fillData();
			return true;
		}
		return super.onContextItemSelected(item);
	}

	private void createNewCard() {
		Intent i = new Intent(this, EditCardActivity.class);
		startActivityForResult(i, ACTIVITY_CREATE, bundleAnimation);
	}

	private void createNewRegister() {
		Intent i = new Intent(this, RegisterActivity.class);
		startActivityForResult(i, ACTIVITY_REGISTER, bundleAnimation);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Intent i = new Intent(this, ViewCardActivity.class);
		i.putExtra(DbCardAdapter.KEY_ID, id);
		startActivity(i, bundleAnimation);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		fillData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.main_menu_action_new:
			createNewCard();
			break;

			case R.id.main_menu_action_register:
			//	createNewCard();
				createNewRegister();
				break;

		}
		return super.onOptionsItemSelected(item);
	}






	}

