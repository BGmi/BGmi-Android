package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import bgmi.app.bgmi_android.utils.BGmiProperties;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("bgmi_config", 0);
        String url = sp.getString("bgmi_url", "");
        BGmiProperties.getInstance().bgmiBackendURL = url;

        if (url.equals("https://") || url.equals("")) {
            createFragment(new ResetFragment());
        } else {
            createFragment(new BangumiListFragment());
        }
    }

    private void createFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, fragment).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String url = getSharedPreferences("bgmi_config", 0).getString("bgmi_url", "");
        if (url.equals("")) {
            createFragment(new ResetFragment());
            mDrawerLayout.closeDrawers();
            return true;
        }
        switch (item.getItemId()) {
            case R.id.navigation_drawer_bangumi:
                createFragment(new BangumiListFragment());
                break;
            case R.id.navigation_drawer_old_bangumi:
                createFragment(new OldBangumiListFragment());
                break;
            case R.id.navigation_drawer_calendar:
                createFragment(new CalendarFragment());
                break;
            case R.id.navigation_drawer_resource:
                break;
            case R.id.navigation_drawer_subscribe:
                break;
            case R.id.navigation_drawer_settings:
                createFragment(new ResetFragment());
                break;
            case R.id.navigation_drawer_about:
                createFragment(new AboutFragment());
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawers();
        return true;
    }

    private void initInstancesDrawer() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        NavigationView mNavigationView = findViewById(R.id.navigation_drawer);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initInstancesDrawer();
        actionBarDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return actionBarDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        //mDrawerLayout.closeDrawer(mDrawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
    }
}

