package com.courseraandroid.myfirstappcoursera;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.courseraandroid.myfirstappcoursera.model.User;

public class ProfileActivity extends AppCompatActivity {
    public static final String USER_KEY = "USER_KEY";

    private TextView mLogin;
    private TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_profile);

        mLogin = findViewById(R.id.tvEmail);
        mName = findViewById(R.id.tvName);


        User user = (User)getIntent().getExtras().get(USER_KEY);
        mLogin.setText(user.getmLogin());
        mName.setText(user.getName());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.actionLogout :
                startActivity(new Intent(this, AuthActivity.class));
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}