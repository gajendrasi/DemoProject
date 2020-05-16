package gaurav.example.interview_demo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ImageView add,search,search_close,share,refresh,mic;
    RelativeLayout searchLayout,toolbarLayout;
    Toolbar toolbar;
    EditText searchBar;
    ListView list_recycler;
    TeacherDetailInformationAdapter adapter;
    ArrayList<String> subjectlistdata=new ArrayList<>();
    MyDataBaseHelper myDb;
    ChipNavigationBar bottomNav;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb=new MyDataBaseHelper(MainActivity.this);

        setUptoolbar();

        searchLayout=findViewById(R.id.seacr_layout);
        toolbarLayout=findViewById(R.id.toolbar_layout);
        searchBar=(EditText) findViewById(R.id.searched_bar);

        list_recycler=(ListView) findViewById(R.id.list_recycler);
        final ArrayList<TeacherDetailInformationData> teacherData=getAllData();
        adapter=new TeacherDetailInformationAdapter(MainActivity.this,R.layout.teacher_list_items,teacherData);
        list_recycler.setAdapter(adapter);
        list_recycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name=teacherData.get(position).teacherName;
                String mobile=teacherData.get(position).teacherMobile;
                String sp=teacherData.get(position).teacherSp;

                Intent intent=new Intent(MainActivity.this,TeacherDetails_Activity.class);

                intent.putExtra("TeacherName",name);
                intent.putExtra("TeacherSp",sp);
                intent.putExtra("TeacherMobile",mobile);

                startActivity(intent);
                finish();
            }
        });

        setDataNavHead();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.home: {
                        drawerLayout.closeDrawers();
                        break;
                    }
                    case R.id.myprofile: {
                        Intent i1 = new Intent(MainActivity.this, Profile_Settings_Activity.class);
                        startActivity(i1);
                        finish();
                        break;
                    }

                    case R.id.logout: {
                        Session_Management_Service session_management_service=new Session_Management_Service(getApplicationContext());
                        session_management_service.removeUserInfo();
                        Intent i1 = new Intent(MainActivity.this, Login_Activity.class);
                        startActivity(i1);
                        finish();
                        break;
                    }

                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        bottomNav=(ChipNavigationBar) findViewById(R.id.bottomnav);
        bottomNav.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int id) {
                switch (id)
                {
                    case R.id.home:
                    {
                        finish();
                        startActivity(getIntent());
                        break;
                    }
                    case R.id.search:
                    {
                        searchLayout.setVisibility( View.VISIBLE );
                        toolbarLayout.setVisibility( View.GONE );
                        break;
                    }
                    case R.id.me:
                    {
                        Intent profileintent=new Intent(MainActivity.this,Profile_Settings_Activity.class);
                        startActivity(profileintent);
                        finish();
                        break;
                    }
                }
            }
        });

        search_close=findViewById( R.id.search_close );
        search_close.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBar.getText().clear();
                searchLayout.setVisibility(View.GONE );
                toolbarLayout.setVisibility(View.VISIBLE);
            }
        });

        searchBar=(EditText) findViewById(R.id.searched_bar);
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapter.getFilter().filter(s);
                }
                catch (Exception excep){excep.printStackTrace();}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mic=(ImageView) findViewById(R.id.ic_mic);
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speechtoText();
            }
        });


    }

    private ArrayList<TeacherDetailInformationData> getAllData() {
        TeacherDetailInformationData data=new TeacherDetailInformationData();
        ArrayList<TeacherDetailInformationData> listdata=new ArrayList<>();
        data.setTeacherName("Vivek");
        data.setTeacherMobile("8976452345");
        data.setTeacherSp("Mathematics");
        TeacherDetailInformationData data1=new TeacherDetailInformationData();
        data1.setTeacherName("Gaurav");
        data1.setTeacherMobile("8767543421");
        data1.setTeacherSp("Physics");
        TeacherDetailInformationData data2=new TeacherDetailInformationData();
        data2.setTeacherName("Rajesh");
        data2.setTeacherMobile("9767543421");
        data2.setTeacherSp("Hindi");
        TeacherDetailInformationData data3=new TeacherDetailInformationData();
        data3.setTeacherName("Manoj");
        data3.setTeacherMobile("9656564532");
        data3.setTeacherSp("History");
        TeacherDetailInformationData data4=new TeacherDetailInformationData();
        data4.setTeacherName("Shubham");
        data4.setTeacherMobile("9090675434");
        data4.setTeacherSp("Sanskrit");

        listdata.add(data);
        listdata.add(data1);
        listdata.add(data2);
        listdata.add(data3);
        listdata.add(data4);

        return listdata;
    }

    private void speechtoText() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    searchBar.setText(result.get(0));
                }
                break;
            }
        }
    }

    private void setUptoolbar() {
        toolbar=findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar.setTitle("Test");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


    }

    @Override
    public boolean onKeyDown ( int keyCode, KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.alert)
                    .setTitle("Alert!")
                    .setMessage("Do you really want to exit from application?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            MainActivity.this.finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDataNavHead() {
        View headerView = navigationView.getHeaderView(0);
        final ImageView profile_image = (ImageView) headerView.findViewById(R.id.navedit_profile);
        //profile_image.setImageResource(R.mipmap.app_icon);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.name);
        final TextView navPhone = (TextView) headerView.findViewById(R.id.number);

        Cursor cursor=myDb.getRegisData();
        if(cursor.getCount() == 0)
        {
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                try {
                    String dbimage = cursor.getString(6);
                    String dbname = cursor.getString(1);
                    String dbmobile = cursor.getString(2);

                    navUsername.setText(dbname);
                    navPhone.setText(dbmobile);
                    if(dbimage.equals("") || dbimage.equals(null))
                    {
                        profile_image.setImageResource(R.drawable.user_icon);
                    }
                    else {
                        profile_image.setImageBitmap(stringToBitmap(dbimage));
                    }
                }
                catch (Exception exception){exception.printStackTrace();}
            }
        }
    }


    private Bitmap stringToBitmap(String encodedString){
        try{
            byte [] encodeByte= Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

}
