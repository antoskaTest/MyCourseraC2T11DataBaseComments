package com.courseraandroid.myfirstappcoursera.albums;


import androidx.fragment.app.Fragment;

import com.courseraandroid.myfirstappcoursera.SingleFragmentActivity;

public class AlbumsActivity extends SingleFragmentActivity {
    @Override
    protected Fragment getFragment() {
        return AlbumsFragment.newInstance();
    }
}
