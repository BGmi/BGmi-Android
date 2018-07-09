package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;


import java.lang.reflect.Type;
import java.util.ArrayList;

import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;


public class BangumiListActivity extends AppCompatActivity {
    private static final String TAG = "BangumiListActivity";

    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;


    public void loadData() {
        String url = BGmiProperties.getInstance().bgmiBackendURL;
        Log.i(TAG, "loadData: " + url);

        if (url == null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return;
        }

        if (!url.endsWith("/")) {
            url += "/";
        }
        url += "api/index";

        RequestQueue queue = Volley.newRequestQueue(this);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        try {
                            JSONArray bangumiJSONArray = response.getJSONArray("data");
                            ArrayList<Bangumi> bangumiList = new ArrayList<>();
                            for (int i = 0; i<bangumiJSONArray.length(); i++) {
                                Bangumi bangumi = gson.fromJson(bangumiJSONArray.getJSONObject(i).toString(), Bangumi.class);
                                bangumiList.add(bangumi);
                                Log.i(TAG, "onResponse: " + bangumi.toString());
                            }
                            createFragment(bangumiList);

                        } catch (Exception ex) {
                            Toast.makeText(BangumiListActivity.this, "Error when parsing response data", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BangumiListActivity.this, "Error when performing HTTP request", Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.toString());
                    }
                }
        );
        queue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.app_bar_refresh:
                ArrayList<Bangumi> bangumi = new ArrayList<>();
                createFragment(bangumi);
                loadData();
                break;

            case R.id.app_bar_switch:
                SharedPreferences.Editor editor = getSharedPreferences("bgmi_config", 0).edit();
                editor.putString("bgmi_url", "");
                editor.putString("bgmi_data", "");
                editor.apply();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;

            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bangumi_list);
        initInstancesDrawer();

        SharedPreferences config = getSharedPreferences("bgmi_config", 0);

        String bgmi_json = config.getString("bgmi_data", "");
        if (!bgmi_json.equals("")) {
            Gson gson = new Gson();
            Type bangumiType = new TypeToken<ArrayList<Bangumi>>(){}.getType();
            ArrayList<Bangumi> bangumi = gson.fromJson(bgmi_json, bangumiType);
            createFragment(bangumi);
            return;
        }

        loadData();
    }

    public void createFragment(ArrayList<Bangumi> array) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = getSharedPreferences("bgmi_config", 0).edit();
        editor.putString("bgmi_data", gson.toJson(array));
        editor.apply();

        BangumiListFragment bangumiListFragment = new BangumiListFragment();

        bangumiListFragment.setBangumi(array);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, bangumiListFragment).commit();

    }


    private void initInstancesDrawer() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(BangumiListActivity.this, mDrawerLayout, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);

        /*
        TextView textView = mDrawerLayout.findViewById(R.id.textview_data_source);
        textView.setText(BGmiProperties.getInstance().bgmiBackendURL);
        */
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
}


