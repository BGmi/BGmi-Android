package bgmi.app.bgmi_android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import java.util.concurrent.TimeUnit;

import bgmi.app.bgmi_android.adapters.BangumiAdapter;
import bgmi.app.bgmi_android.models.Bangumi;
import bgmi.app.bgmi_android.utils.BGmiProperties;


public class BangumiListFragment extends Fragment {
    private static final String TAG = "BangumiListFragment";
    private ArrayList<Bangumi> bangumiList;

    public void loadData() {
        String url = getActivity().getSharedPreferences("bgmi_config", 0).getString("bgmi_url", "");
        Log.i(TAG, "loadData: " + url);

        if (url.equals("")) {
            /*
            Intent intent = new Intent(this, ResetActivity.class);
            startActivity(intent);
            */
            return;
        }

        if (!url.endsWith("/")) {
            url += "/";
        }
        url += "api/index";

        RequestQueue queue = Volley.newRequestQueue(getContext());
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        try {
                            JSONArray bangumiJSONArray = response.getJSONArray("data");
                            bangumiList = new ArrayList<>();
                            for (int i = 0; i<bangumiJSONArray.length(); i++) {
                                Bangumi bangumi = gson.fromJson(bangumiJSONArray.getJSONObject(i).toString(), Bangumi.class);
                                bangumiList.add(bangumi);
                                Log.i(TAG, "onResponse: " + bangumi.toString());
                            }

                            SharedPreferences.Editor editor = getActivity().getSharedPreferences("bgmi_config", 0).edit();
                            editor.putString("bgmi_data", gson.toJson(bangumiList));
                            editor.apply();
                            callback();

                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), "Error when parsing response data", Toast.LENGTH_LONG).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Error when performing HTTP request", Toast.LENGTH_LONG).show();
                        Log.e(TAG, error.toString());
                    }
                }
        );
        queue.add(request);
    }
    public BangumiListFragment() {
    }

    public static BangumiListFragment newInstance() {
        return new BangumiListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        SharedPreferences sp = getActivity().getSharedPreferences("bgmi_config", 0);
        String bgmi_data = sp.getString("bgmi_data", "");

        if (bgmi_data.equals("")) {
            loadData();
        } else {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<Bangumi>>() {}.getType();
            bangumiList = gson.fromJson(bgmi_data, type);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bangumi_list, container, false);
        if (bangumiList != null) {
            callback(view);
        }
        return view;
    }

    private void callback(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(bangumiList.size());


        BangumiAdapter bangumiAdapter = new BangumiAdapter(this.bangumiList);
        bangumiAdapter.setHasStableIds(true);
        recyclerView.setAdapter(bangumiAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void callback() {
        View view = getView();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(bangumiList.size());


        BangumiAdapter bangumiAdapter = new BangumiAdapter(this.bangumiList);
        bangumiAdapter.setHasStableIds(true);
        recyclerView.setAdapter(bangumiAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.refresh, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        loadData();
        return true;
    }

}
