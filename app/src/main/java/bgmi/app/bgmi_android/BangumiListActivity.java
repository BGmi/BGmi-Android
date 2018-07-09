package bgmi.app.bgmi_android;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;


import bgmi.app.bgmi_android.adapters.BangumiAdapter;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;


public class BangumiListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bangumi_list);

        SharedPreferences config = getSharedPreferences("bgmi_config", MODE_WORLD_READABLE);

        String bgmi_json = config.getString("bgmi_data", "");
        if (!bgmi_json.equals("")) {
            Gson gson = new Gson();
            createFragment(gson.fromJson(bgmi_json, Bangumi[].class));
            return;
        }

        String url = BGmiProperties.getInstance().bgmiFrontEndURL;
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
                            Bangumi[] bangumiList = new Bangumi[bangumiJSONArray.length()];
                            for (int i = 0; i<bangumiJSONArray.length(); i++) {
                                Bangumi bangumi = gson.fromJson(bangumiJSONArray.getJSONObject(i).toString(), Bangumi.class);
                                bangumiList[i] = bangumi;
                                System.out.println(bangumi.toString());
                            }
                            createFragment(bangumiList);

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            // TODO: Handler json error
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println(error.toString());
                        // TODO: Handle error
                    }
                }
        );
        queue.add(request);

    }

    public void createFragment(Bangumi[] array) {
        Gson gson = new Gson();
        SharedPreferences.Editor editor = getSharedPreferences("bgmi_config", MODE_WORLD_WRITEABLE).edit();
        editor.putString("bgmi_data", gson.toJson(array));
        editor.apply();

        BangumiListFragment bangumiListFragment = new BangumiListFragment();

        bangumiListFragment.setBangumi(array);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment, bangumiListFragment).commit();

    }
}


