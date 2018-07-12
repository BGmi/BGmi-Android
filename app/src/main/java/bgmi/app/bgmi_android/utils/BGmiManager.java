package bgmi.app.bgmi_android.utils;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.security.auth.callback.Callback;

import bgmi.app.bgmi_android.models.Bangumi;

public class BGmiManager {
    private static final String TAG = "BGmiManager";
    private static BGmiManager mInstance = null;

    private BGmiManager(){}

    public String getUrl() {
        return BGmiProperties.getInstance().bgmiBackendURL;
    }

    public static synchronized BGmiManager getInstance() {
        if(null == mInstance){
            mInstance = new BGmiManager();
        }
        return mInstance;
    }

    public void load(Context context, final CallBack clazz, String path) {
        String url = getUrl() + path;
        Log.i(TAG, "load: " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
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
                            }

                            clazz.callback(bangumiList, "");

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            clazz.callback(null, "Error when parsing response data");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        clazz.callback(null, "Error when performing HTTP request");
                        Log.e(TAG, error.toString());
                    }
                }
        );
        queue.add(request);
    }

    public void calendar(Context context, final CallBack<HashMap<String, ArrayList<String>>> clazz) {
        String url = getUrl() + BGmiProperties.getInstance().pageCalURL;
        Log.i(TAG, "calendar: " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("data");
                            HashMap<String, ArrayList<String>> calendarMap = new HashMap<>();
                            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                                String key = it.next();
                                ArrayList<String> nameList = new ArrayList<String>();

                                JSONArray jsonArray = jsonObject.getJSONArray(key);
                                for (Integer i = 0; i<jsonArray.length(); i++) {
                                    nameList.add(jsonArray.getJSONObject(i).getString("name"));
                                }
                                calendarMap.put(key, nameList);
                            }
                            clazz.callback(calendarMap, "");

                        } catch (Exception ex) {
                            clazz.callback(null, "Error when parsing response data");
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        clazz.callback(null, "Error when performing HTTP request");
                        Log.e(TAG, error.toString());
                    }
                }
        );
        queue.add(request);
    }

    public void subscribe(Context context, final CallBack<String> clazz, String name, String path) {
        String url = getUrl() + path;
        Log.i(TAG, "subscribe: " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        HashMap<String, String> params = new HashMap<>();
        params.put("name", name);
        JSONObject parameters = new JSONObject(params);

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        clazz.callback("subscribed", "");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                        clazz.callback(null, "Error when performing HTTP request, maybe ADMIN_TOKEN not correct");
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("bgmi-token", BGmiProperties.getInstance().adminToken);
                return header;
            }
        };
        queue.add(request);
    }
}
