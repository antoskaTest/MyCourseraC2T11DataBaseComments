package com.courseraandroid.myfirstappcoursera;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;

public abstract class SingleFragmentActivity extends AppCompatActivity {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_single_fragment);

        if(savedInstanceState == null){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, getFragment())
                    .addToBackStack(getFragment().getClass().getSimpleName())
                    .commit();
        }

    }

    protected abstract Fragment getFragment();

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() != 1){
            fragmentManager.popBackStack();
        }
    }
}
