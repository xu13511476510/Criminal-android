package com.example.lenovo.criminal;

import android.support.v4.app.Fragment;

/**
 * Created by Lenovo on 2018/4/7.
 */

public class CrimeListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new CrimeListFragment();
    }
}
