package gaurav.example.interview_demo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Forget_Password extends AppCompatActivity {

    MyDataBaseHelper myDb;
    EditText usernameEd,useremailEd,userpasswordEd,userconfirmpasswordEd;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget__password);

        myDb=new MyDataBaseHelper(Forget_Password.this);

        usernameEd=(EditText) findViewById(R.id.username);
        useremailEd=(EditText) findViewById(R.id.emailaddress);
        userpasswordEd=(EditText) findViewById(R.id.new_password);
        userconfirmpasswordEd=(EditText) findViewById(R.id.confirm_password);

        submit=(Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotData();
            }
        });

    }

    private void forgotData() {
        if(usernameEd.getText().toString().equals("") || usernameEd.getText().toString().equals(null))
        {
            Toast.makeText(this, "Please enter username", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(useremailEd.getText().toString().equals("") || useremailEd.getText().toString().equals(null))
        {
            Toast.makeText(this, "Please enter email address", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(userpasswordEd.getText().toString().equals("") || userpasswordEd.getText().toString().equals(null))
        {
            Toast.makeText(this, "Please enter new password", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(userconfirmpasswordEd.getText().toString().equals("") || userconfirmpasswordEd.getText().toString().equals(null))
        {
            Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            return;
        }
        else
        {
            boolean status=myDb.forgetUserPassword(usernameEd.getText().toString(),userpasswordEd.getText().toString());
            if(status==true)
            {
                Toast.makeText(Forget_Password.this,"Your password successfully changed",Toast.LENGTH_SHORT).show();
                Intent loginintent=new Intent(Forget_Password.this, Login_Activity.class);
                startActivity(loginintent);
                finish();
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent loginintent=new Intent(Forget_Password.this,Login_Activity.class);
        startActivity(loginintent);
        finish();
    }
}
