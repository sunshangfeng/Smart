package com.ssf.smart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private PreferenceUtils preferenceUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        preferenceUtils = new PreferenceUtils(this);

        String url = preferenceUtils.get(Constant.PreferenceKey.URL, "");
        Log.e(MainActivity.class.getSimpleName(), url);
        if (TextUtils.isEmpty(url)) {
            replaceFragment(LoginFragment.newInstance());
        } else {
            replaceFragment(WebFragment.newInstance(url));
        }
    }

    public void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().
                replace(R.id.content, fragment).addToBackStack(null).commit();
    }

    public void clearBackStack() {
        int stack = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < stack; i++) {
            getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onBackPressed() {

        int stack = getSupportFragmentManager().getBackStackEntryCount();
        if (stack > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }

    }
}
