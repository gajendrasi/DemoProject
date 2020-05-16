package gaurav.example.interview_demo_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="MYDATABASE";
    public static final String TABLE_NAME_REGISTRATION="REGISTRATION";

    public static final String User_COL_1="USER_ID";
    public static final String User_COL_2="USER_NAME";
    public static final String User_COL_3="USER_MOBILE";
    public static final String User_COL_4="USER_EMAIL";
    public static final String User_COL_5="USER_CODE";
    public static final String User_COL_6="USER_PASSWORD";
    public static final String User_COL_7="USER_IMAGE";

    public MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME_REGISTRATION+ "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,USER_NAME TEXT,USER_MOBILE TEXT,USER_EMAIl TEXT,USER_CODE TEXT,USER_PASSWORD TEXT,USER_IMAGE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_REGISTRATION);
        onCreate(db);
    }

    public boolean registrationData(String username,String usermobile,String useremail,String usercode,String userpassword,String userimage)
    {
        SQLiteDatabase registrationdb = this.getWritableDatabase();
        ContentValues registrationcontentValues = new ContentValues();
        registrationcontentValues.put(User_COL_2, username);
        registrationcontentValues.put(User_COL_3, usermobile);
        registrationcontentValues.put(User_COL_4, useremail);
        registrationcontentValues.put(User_COL_5, usercode);
        registrationcontentValues.put(User_COL_6, userpassword);
        registrationcontentValues.put(User_COL_7, userimage);
        long result = registrationdb.insert(TABLE_NAME_REGISTRATION, null, registrationcontentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getRegisData()
    {
        SQLiteDatabase regisdb = this.getWritableDatabase();
        Cursor regisres = regisdb.rawQuery("select * from " + TABLE_NAME_REGISTRATION, null);
        return regisres;
    }

    public boolean forgetUserPassword(String username,String password)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues forgotContent=new ContentValues();
        forgotContent.put(User_COL_6,password);
        db.update(TABLE_NAME_REGISTRATION,forgotContent,"USER_CODE = ?",new String[] {username});
        return true;
    }

    public boolean updateprofileData(String username,String name,String mobile,String email,String userimage)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues updateContent=new ContentValues();
        updateContent.put(User_COL_2,name);
        updateContent.put(User_COL_3,mobile);
        updateContent.put(User_COL_4,email);
        updateContent.put(User_COL_7,userimage);
        db.update(TABLE_NAME_REGISTRATION,updateContent,"USER_CODE = ?",new String[] {username});
        return true;
    }
}
