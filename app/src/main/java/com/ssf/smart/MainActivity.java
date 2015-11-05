package com.ssf.smart;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private PreferenceUtils preferenceUtils;

    private Login login;

    @Bind(R.id.email)
    TextView email;

    private String url;

    private String storesid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        getSupportActionBar().setElevation(0);

        preferenceUtils = new PreferenceUtils(this);

        url = preferenceUtils.get(Constant.PreferenceKey.URL, "");


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


    public boolean onNavigationItemSelected(MenuItem item) {
        login = preferenceUtils.getObject(Constant.PreferenceKey.LOGIN, Login.class);
        storesid = preferenceUtils.get(Constant.PreferenceKey.STORES_ID, "");
        int id = item.getItemId();

        clearBackStack();

        if (id == R.id.nav_appstores) {
            replaceFragment(AppStoresFragment.newInstance());
        } else if (id == R.id.nav_app) {
            replaceFragment(AppFragment.newInstance(storesid));
        } else if (id == R.id.nav_back) {
            preferenceUtils.clear();
            replaceFragment(LoginFragment.newInstance());
        }

        return true;
    }

    void Toast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }
}
