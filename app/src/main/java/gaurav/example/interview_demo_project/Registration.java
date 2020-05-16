package gaurav.example.interview_demo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registration extends AppCompatActivity {

    EditText name,mobile,email,username,password;
    Button submit;
    MyDataBaseHelper myDb;
    TextView logintxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        myDb=new MyDataBaseHelper(Registration.this);
        logintxt=(TextView) findViewById(R.id.singin_text);

        name=(EditText) findViewById(R.id.name);
        mobile=(EditText) findViewById(R.id.user_mobile);
        email=(EditText) findViewById(R.id.user_email);
        username=(EditText) findViewById(R.id.user_name);
        password=(EditText) findViewById(R.id.password);

        submit=(Button) findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.getText().toString().equals("") || name.getText().toString().equals(null))
                {
                    Toast.makeText(Registration.this, "Please enter your name", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(mobile.getText().toString().equals("") || mobile.getText().toString().equals(null))
                {
                    Toast.makeText(Registration.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(email.getText().toString().equals("") || mobile.getText().toString().equals(null))
                {
                    Toast.makeText(Registration.this, "Please enter email address", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(username.getText().toString().equals("") || username.getText().toString().equals(null))
                {
                    Toast.makeText(Registration.this, "Please enter username", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(password.getText().toString().equals("") || password.getText().toString().equals(null))
                {
                    Toast.makeText(Registration.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    insertData();
                }
            }
        });

        logintxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginintent=new Intent(Registration.this, Login_Activity.class);
                startActivity(loginintent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent loginintent=new Intent(Registration.this,Login_Activity.class);
        startActivity(loginintent);
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

    private void insertData()
    {
        String userName=name.getText().toString();
        String userMobile=mobile.getText().toString();
        String userEmail=email.getText().toString();
        String userCode=username.getText().toString();
        String userPassword=password.getText().toString();

        boolean result= myDb.registrationData(userName,userMobile,userEmail,userCode,userPassword,"");
        if(result == true)
        {
            Toast.makeText(Registration.this, "You are succesfully registered", Toast.LENGTH_SHORT).show();
            name.getText().clear();
            mobile.getText().clear();
            email.getText().clear();
            username.getText().clear();
            password.getText().clear();

            Intent loginintent=new Intent(Registration.this,Login_Activity.class);
            startActivity(loginintent);
            finish();

        }
        else
        {
            Toast.makeText(Registration.this, "Registration failed", Toast.LENGTH_SHORT).show();
        }
    }
}
