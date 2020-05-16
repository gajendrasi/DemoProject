package gaurav.example.interview_demo_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TeacherDetails_Activity extends AppCompatActivity {

    Toolbar teacherbar;
    TextView name,mobile,sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details_);

        teacherbar=(Toolbar) findViewById(R.id.teacherbar);
        setSupportActionBar(teacherbar);
        teacherbar.setTitle("Test");
        teacherbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back));

        name=(TextView) findViewById(R.id.name);
        mobile=(TextView) findViewById(R.id.mobile);
        sp=(TextView) findViewById(R.id.sp);

        Intent intent = getIntent();
        String teacherName = intent.getStringExtra("TeacherName");
        name.setText(teacherName);
        String teacherSp=intent.getStringExtra("TeacherSp");
        sp.setText("Specialization: "+teacherSp);
        String teacherMobile=intent.getStringExtra("TeacherMobile");
        mobile.setText(teacherMobile);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent home=new Intent(TeacherDetails_Activity.this,MainActivity.class);
        startActivity(home);
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
}
