package gaurav.example.interview_demo_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Profile_Settings_Activity extends AppCompatActivity {

    Toolbar profileBar;
    CircleImageView editProfileimage;
    private int SELECT_FILE = 1;
    String encode, getImage;
    ImageView editname, editphone, editemail;
    TextView adminname, adminphone, adminemail;
    Button update_txt;
    LinearLayout camera_layout;
    private final int PERMISSION_ALL = 1;
    //private ImagePicker imagePicker;
    Bitmap bitmap;
    MyDataBaseHelper myDb;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__settings_);

        myDb=new MyDataBaseHelper(Profile_Settings_Activity.this);

        profileBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(profileBar);
        setTitle("My Profile");
        profileBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));

        editProfileimage=(CircleImageView) findViewById(R.id.edit_profile);
        camera_layout=(LinearLayout) findViewById(R.id.camera_layout);

        init();

        editname = (ImageView) findViewById(R.id.editname_image);
        editphone = (ImageView) findViewById(R.id.editphone_image);
        editemail = (ImageView) findViewById(R.id.editemail_image);

        adminname = (TextView) findViewById(R.id.adminname_txt);
        adminphone = (TextView) findViewById(R.id.adminphone_txt);
        adminemail = (TextView) findViewById(R.id.adminemail_txt);

        getRegisDatatoprofile();

        editname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setprofiledata("Update Name", "Enter Name");
            }
        });

        editphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setprofiledata("Update Mobile Number", "Enter Mobile");
            }
        });

        editemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setprofiledata("Update Email", "Enter Email");
            }
        });

        update_txt=(Button) findViewById(R.id.update_txt);
        update_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileData();
            }
        });
    }

    private void updateProfileData() {
        String sendbduserimage="";
        String sendbdusername=new Session_Management_Service(Profile_Settings_Activity.this).getUsername();
        try {
            sendbduserimage = imagetToString(bitmap);
        }
        catch (Exception exp){exp.printStackTrace();}

        String senddbname=adminname.getText().toString();
        String senddbmobile=adminphone.getText().toString();
        String senddbemail=adminemail.getText().toString();


        if(sendbduserimage.equals("") || sendbduserimage.equals(null)){
            Toast.makeText(this, "Please select a picture", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                boolean status = myDb.updateprofileData(sendbdusername, senddbname, senddbmobile, senddbemail, sendbduserimage);
                if (status == true) {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ex) {ex.printStackTrace();}
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeintent=new Intent(Profile_Settings_Activity.this,MainActivity.class);
        startActivity(homeintent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void init()
    {
        camera_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                CropImage.startPickImageActivity(Profile_Settings_Activity.this);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode== Activity.RESULT_OK)
        {
            Uri imageuri=CropImage.getPickImageResultUri(this,data);
            if(CropImage.isReadExternalStoragePermissionsRequired(this,imageuri)){
                uri=imageuri;
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},
                        0);
            }
            else
            {
                startCrop(imageuri);
            }
        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK){
                Bitmap bm = null;
                Uri uri = result.getUri();
                if (uri != null) {
                    try {
                        bm = MediaStore.Images.Media.getBitmap(Profile_Settings_Activity.this.getApplicationContext().getContentResolver(), result.getUri());
                        InputStream inputStream = Profile_Settings_Activity.this.getContentResolver().openInputStream(uri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        encode = imagetToString(bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                editProfileimage.setImageBitmap(bm);

            }
            else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }

    }

   private void startCrop(Uri imageuri) {
        CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    private String imagetToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageByte = byteArrayOutputStream.toByteArray();
        getImage = Base64.encodeToString(imageByte, Base64.DEFAULT);
        return getImage;
    }

    private void setprofiledata(final String status,final String hint)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(status);
        builder.setCancelable(false);
        builder.setIcon(getResources().getDrawable(R.drawable.welcome));

        LayoutInflater inflater= Profile_Settings_Activity.this.getLayoutInflater();
        View view=inflater.inflate(R.layout.updatedata_layout,null);


        final EditText input = view.findViewById(R.id.input);
        if(hint.equals("Enter Mobile"))
        {
            input.setInputType(InputType.TYPE_CLASS_NUMBER);
            input.setHint(hint);
            builder.setView(view);
        }
        else if (hint.equals("Enter Name")){
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint(hint);
            builder.setView(view);
        }else if (hint.equals("Enter Email")){
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint(hint);
            builder.setView(view);
        }


        builder.setPositiveButton("SUBMIT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(status.equals("Update Name")) {
                    String adminame = input.getText().toString();
                    if(adminame.equals("") || adminame.equals(null))
                    {
                        Toast.makeText(Profile_Settings_Activity.this, "This field can't be empty.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        adminname.setText(adminame);
                    }
                }
                else if (status.equals( "Update Mobile Number" ))
                {

                    String adminphonenumber=input.getText().toString();
                    if(adminphonenumber.equals("") || adminphonenumber.equals(null) || !String.valueOf(adminphonenumber.length()).equals("10")) {
                        Toast.makeText(Profile_Settings_Activity.this, "Enter valid mobile number.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {

                        adminphone.setText(adminphonenumber);
                    }
                }
                else if (status.equals("Update Email"))
                {

                    String adminEmail=input.getText().toString();
                    if(adminEmail.equals("") || adminEmail.equals(null)) {
                        Toast.makeText(Profile_Settings_Activity.this, "Enter valid Email.", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        adminemail.setText(adminEmail);
                    }
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void getRegisDatatoprofile()
    {
        Cursor cursor=myDb.getRegisData();
        if(cursor.getCount() == 0)
        {
            return;
        }
        else
        {
            while (cursor.moveToNext())
            {
                String dbimage=cursor.getString(6);
                String dbname=cursor.getString(1);
                String dbmobile=cursor.getString(2);
                String dbemail=cursor.getString(3);

                adminname.setText(dbname);
                adminphone.setText(dbmobile);
                adminemail.setText(dbemail);
                if(dbimage.equals("") || dbimage.equals(null))
                {
                    editProfileimage.setImageResource(R.drawable.user_icon);
                }
                else {
                    editProfileimage.setImageBitmap(stringToBitmap(dbimage));
                }
            }
        }
    }

    private Bitmap stringToBitmap(String encodedString){
        try{
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
}
