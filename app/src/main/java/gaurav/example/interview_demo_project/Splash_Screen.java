package gaurav.example.interview_demo_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class Splash_Screen extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash__screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(new Session_Management_Service(Splash_Screen.this).getUsername().equals("") || new Session_Management_Service(Splash_Screen.this).getUserpassword().equals(null) &&
                        new Session_Management_Service(Splash_Screen.this).getUserpassword().equals("") ||  new Session_Management_Service(Splash_Screen.this).getUserpassword().equals(null)) {
                    Intent loginintent = new Intent(Splash_Screen.this, Login_Activity.class);
                    startActivity(loginintent);
                    finish();
                }
                else
                {

                    Intent loginintent = new Intent(Splash_Screen.this, MainActivity.class);
                    startActivity(loginintent);
                    finish();
                }
            }


        }, SPLASH_DISPLAY_LENGTH);
    }
}
