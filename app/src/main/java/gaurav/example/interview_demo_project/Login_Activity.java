package gaurav.example.interview_demo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText userName,userPassword;
    Button loginBtn;
    TextView forgot_txt,SignUp;
    CheckBox showpassword;
    private ProgressDialog progressDialog;
    MyDataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        forgot_txt=(TextView) findViewById(R.id.userforgotpassword);
        userName=(EditText) findViewById(R.id.username);
        userPassword=(EditText) findViewById(R.id.password);
        loginBtn=(Button) findViewById(R.id.login_btn);
        showpassword=(CheckBox) findViewById(R.id.showpassword);

        loginBtn.setOnClickListener(this);

        progressDialog=new ProgressDialog(this);

        forgot_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgotintent=new Intent(Login_Activity.this,Forget_Password.class);
                startActivity(forgotintent);
                finish();
            }
        });

        SignUp=(TextView) findViewById(R.id.buttonsignup);
        SignUp.setOnClickListener(this);

        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else
                {
                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });
    }

    private void adminLogin() {
        if(new CheckNetConnection().isConnected(Login_Activity.this)) {

            final String username_str = userName.getText().toString();
            final String userpassword_str = userPassword.getText().toString();

            progressDialog.setTitle("Login User");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();

            boolean status=getRegisData(username_str,userpassword_str);

            if(status==true)
            {
                progressDialog.dismiss();

                Session_Management_Service sessionMgt=new Session_Management_Service(Login_Activity.this);
                sessionMgt.setUsername(username_str);
                sessionMgt.setUserpassword(userpassword_str);

                Toast.makeText(Login_Activity.this, "Login Success!!", Toast.LENGTH_SHORT).show();

                Intent adminhomeintent=new Intent(Login_Activity.this,MainActivity.class);
                startActivity(adminhomeintent);
                finish();
            }
            else
            {
                progressDialog.hide();
                Toast.makeText(Login_Activity.this, "Login Failed!!", Toast.LENGTH_SHORT).show();
            }


        }

    }

    private boolean getRegisData(String username_str, String userpassword_str) {
        myDb=new MyDataBaseHelper(this);
        Cursor res=myDb.getRegisData();
        if(res.getCount() == 0)
        {
            Toast.makeText(this, "You are not registered", Toast.LENGTH_SHORT).show();
        }
        else
        {
            while (res.moveToNext()) {
                String dbusername = res.getString(4);
                String dbpassword = res.getString(5);

                if (dbusername.trim().equals(username_str.trim()) && dbpassword.trim().equals(userpassword_str.trim())) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if(v==loginBtn)
        {
            if(TextUtils.isEmpty(userName.getText().toString().trim()))
            {
                new AlertDialog.Builder(Login_Activity.this)
                        .setIcon(R.drawable.alert)
                        .setTitle("Alert!")
                        .setMessage("Please enter valid username.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        })
                        .show();
                return;
            }
            if(TextUtils.isEmpty(userPassword.getText().toString().trim())){
                new AlertDialog.Builder(Login_Activity.this)
                        .setIcon(R.drawable.alert)
                        .setTitle("Alert!")
                        .setMessage("Please enter valid password.")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }

                        })
                        .show();
                return;
            }
            else {

                adminLogin();
            }
        }

        if(v==SignUp) {
            Intent regisintent = new Intent(Login_Activity.this, Registration.class);
            startActivity(regisintent);
            finish();
        }


    }
}
