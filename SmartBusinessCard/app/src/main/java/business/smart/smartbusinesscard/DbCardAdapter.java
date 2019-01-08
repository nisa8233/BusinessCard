package business.smart.smartbusinesscard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbCardAdapter {

    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_POSITION = "position";
    public static final String KEY_LOCATI = "location";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_LINE = "line";
    public static final String KEY_FACEBOOK = "facebook";
    public static final String KEY_WEB = "web";

    public static final String KEY_ID = "_id";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    private static final String DB_NAME = "data";


    private static final String DB_TABLE = "card";
    private static final int DB_VERSION = 4;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private final Context context;


    private static final String createusertablestring = "CREATE TABLE user(username text primary key,password text);" ;

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            StringBuffer sb = new StringBuffer("create table " + DB_TABLE);
            sb.append(" (_id integer primary key autoincrement");
            sb.append(",");
            sb.append(KEY_NAME + " text not null");
            sb.append(",");
            sb.append(KEY_SURNAME + " text not null");
            sb.append(",");
            sb.append(KEY_COMPANY + " text not null");
            sb.append(",");
            sb.append(KEY_POSITION + " text not null");
            sb.append(",");
            sb.append(KEY_LOCATI + " text not null");
            sb.append(",");
            sb.append(KEY_PHONE + " text not null");
            sb.append(",");

            sb.append(KEY_MAIL + " text not null");
            sb.append(",");
            sb.append(KEY_LINE + " text not null");
            sb.append(",");
            sb.append(KEY_FACEBOOK + " text not null");
            sb.append(",");
            sb.append(KEY_WEB + " text not null ");
            sb.append(");");
            db.execSQL(sb.toString());





            db.execSQL(createusertablestring);
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS notes");
            onCreate(db);
        }
    }

    public DbCardAdapter(Context context) {
        this.context = context;
    }

    public DbCardAdapter open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long createNote(String name, String surname, String phone,String company, String position, String location,
                           String mail, String line, String facebook,
                           String web) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_SURNAME, surname);
        initialValues.put(KEY_COMPANY, company);
        initialValues.put(KEY_POSITION, position);
        initialValues.put(KEY_LOCATI, location);
        initialValues.put(KEY_PHONE, phone);
        initialValues.put(KEY_MAIL, mail);
        initialValues.put(KEY_LINE, line);
        initialValues.put(KEY_FACEBOOK, facebook);
        initialValues.put(KEY_WEB, web);

        return db.insert(DB_TABLE, null, initialValues);
    }

    public long createregister(String username, String password) {
        ContentValues initialValues = new ContentValues();



        initialValues.put(KEY_USERNAME, username);
        initialValues.put(KEY_PASSWORD, password);

        return db.insert("user", null, initialValues);
    }

    public boolean deleteCard(long id) {
        return db.delete(DB_TABLE, KEY_ID + "=" + id, null) > 0;
    }

    public Cursor fetchAllCards() {
        // TODO View this
        return db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_SURNAME, KEY_COMPANY, KEY_POSITION, KEY_LOCATI,
                        KEY_PHONE, KEY_MAIL, KEY_LINE, KEY_FACEBOOK, KEY_WEB },
                null, null, null, null, null);
    }

    public Cursor fetchAllUsers() {
        // TODO View this
        return db.query("user", new String[] { KEY_USERNAME, KEY_PASSWORD
                },
                null, null, null, null, null);
    }



    public Cursor fetchCard(long id) throws SQLException {
        Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ID,
                        KEY_NAME, KEY_SURNAME,  KEY_COMPANY, KEY_POSITION, KEY_LOCATI, KEY_PHONE, KEY_MAIL, KEY_LINE,
                        KEY_FACEBOOK, KEY_WEB }, KEY_ID + "=" + id, null, null, null,
                null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchUser(long id) throws SQLException {
        Cursor mCursor = db.query(true, "user", new String[] { KEY_ID,
                        KEY_USERNAME, KEY_PASSWORD }, KEY_ID + "=" + id, null, null, null,
                null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public boolean updateCard(long id, String name, String surname,String company, String position, String location,
                              String phone, String mail, String line, String facebook,
                              String web) {
        ContentValues args = new ContentValues();
        args.put(KEY_NAME, name);
        args.put(KEY_SURNAME, surname);
        args.put(KEY_COMPANY, company);
        args.put(KEY_POSITION, position);
        args.put(KEY_LOCATI, location);
        args.put(KEY_PHONE, phone);
        args.put(KEY_MAIL, mail);
        args.put(KEY_LINE, line);
        args.put(KEY_FACEBOOK, facebook);
        args.put(KEY_WEB, web);
        return db.update(DB_TABLE, args, KEY_ID + "=" + id, null) > 0;
    }
}

